package com.websocket.game.web;

import com.websocket.game.DAO.FileRepository;
import com.websocket.game.DAO.FileVersionReposiotry;
import com.websocket.game.DAO.UserRepository;
import com.websocket.game.domain.File;
import com.websocket.game.domain.FileVersion;
import com.websocket.game.domain.User;
import com.websocket.game.exceptions.FileNotFoundException;
import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by piotr on 30.05.16.
 */

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping(value = "/files")
public class FileController {
    private String root = "/home/piotr/tin/";
    @Autowired
    public FileRepository fileRepository;
    @Autowired
    public FileVersionReposiotry fileVersionReposiotry;
    @Autowired
    public UserRepository userReposiotry;

    @RequestMapping(value = "/download/{file_name}/{device}/{timestamp}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getFile(@PathVariable("file_name") String fileName,
                                      @PathVariable("device") String device,
                                      @PathVariable("timestamp") String stamp,HttpServletResponse response,
                                      Principal p) throws FileNotFoundException {
        User u = userReposiotry.findUserByUsername(p.getName());
        if(u!= null) {
            response.setHeader("Content-Disposition", "attachment; filename="+fileName);
            return new FileSystemResource(getFileFor(u.username,fileName,device,stamp));
        }else
            throw new FileNotFoundException();
    }
    @RequestMapping(value = "/remove/{file_name}/{device}/{timestamp}", method = RequestMethod.GET)
    @ResponseBody
    public void removeFile(@PathVariable("file_name") String fileName,
                           @PathVariable("device") String device,
                           @PathVariable("timestamp") String stamp, Principal p) throws FileNotFoundException {
            User u = userReposiotry.findUserByUsername(p.getName());
            if(u!= null) {

                String path = getFileFor(u.username,fileName,device,stamp);
                java.io.File file = new java.io.File(path);
                if(!file.delete()) throw new FileNotFoundException();
                try {
                    Set<FileWithStamps> allFiles = getAllFiles(p);
                    if(allFiles==null) System.out.println("null");
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = format.parse(stamp);
                    for(FileWithStamps fileWithStamps: allFiles)
                    {
                        if(fileWithStamps.file.getFilename().compareTo(fileName) == 0&&
                                fileWithStamps.file.getUserId() == u.id &&
                                fileWithStamps.file.getDeviceName().compareTo(device) == 0){
                            for(FileVersion fv : fileWithStamps.fileVersion)
                            {
                                if(fv.getTimestamp().compareTo(date)==0)
                                {
                                    fileVersionReposiotry.delete(fv);
                                    if(fileWithStamps.fileVersion.size()==1)
                                    {
                                        fileRepository.delete(fileWithStamps.file);
                                    }
                                    break;
                                }
                            }
                        }

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
    }
    private String getFileFor(String username, String fileName,String device,String timestamp) {
        return root+"/"+username+"/"+device+"/"+fileName+"/"+timestamp;
    }


    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public Set<FileWithStamps> getAllFiles(Principal p)
    {
        User u = userReposiotry.findUserByUsername(p.getName());

        if(u!=null)
        {
            Set<File> x = fileRepository.findByUserId(u.id);
            Set<FileWithStamps> result = new HashSet<FileWithStamps>();
            for (File z :x) {
                FileWithStamps nowy = new FileWithStamps();
                nowy.file = z;
                nowy.fileVersion =  fileVersionReposiotry.findByFileId(z.getId());
                result.add(nowy);
            }
            return result;
        }
      return null;
    };

    static class FileWithStamps {
        public File file;
        public Set<FileVersion> fileVersion;

        @Override
        public String toString() {
            return "FileWithStamps{" +
                    "file=" + file +
                    ", fileVersion=" + fileVersion +
                    '}';
        }
    };

}
