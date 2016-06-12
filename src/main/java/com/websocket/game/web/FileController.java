package com.websocket.game.web;

import com.websocket.game.DAO.FileRepository;
import com.websocket.game.DAO.FileVersionReposiotry;
import com.websocket.game.DAO.UserRepository;
import com.websocket.game.domain.File;
import com.websocket.game.domain.FileVersion;
import com.websocket.game.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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


    //w kazdej metodzie trzeba sprawdzic czy user to moze.
    @RequestMapping(value = "/download/{file_name}/{device}/{timestamp}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getFile(@PathVariable("file_name") String fileName,
                                      @PathVariable("device") String device,
                                      @PathVariable("timestamp") String stamp,HttpServletResponse response,Principal p) {


        System.out.println("File: "+fileName);

        response.setHeader("Content-Disposition", "attachment; filename="+fileName);

        return new FileSystemResource(getFileFor(fileName,device));
    }
    @RequestMapping(value = "/remove/{file_name}/{device}/{timestamp}", method = RequestMethod.GET)
    @ResponseBody
    public void removeFile(@PathVariable("file_name") String fileName,
                           @PathVariable("device") String device,
                           @PathVariable("timestamp") String stamp) {


    }
    private String getFileFor(String fileName,String device) {
        return root+fileName+"/"+device;
    }
    @RequestMapping(value = "/stamps/{file_name}/{device}", method = RequestMethod.GET)
    public Date [] getStamps(@PathVariable(value="file_name")String file_name,@PathVariable(value="device") String device, Principal p)
    {
        User u = userReposiotry.findUserByUsername(p.getName());
        Date[] result = null;
        if(u!=null)
        {
            Set<File> files = fileRepository.findByUserId(u.id);
            File[] arr =  (File[]) files.toArray();
            File found = null;
            for(int i = 0 ; i < arr.length; i++)
            {
                if(arr[i].getFilename() == file_name && arr[i].getDeviceName() == device)
                    found = arr[i];
            }
            if(found != null)
            {
                Set<FileVersion> set = fileVersionReposiotry.findByFileId(found.getId());
                FileVersion[] x = (FileVersion[]) set.toArray();
                result = new Date[x.length];
                for(int i = 0 ;i < x.length;i++)
                {
                    result[i] = x[i].getTimestamp();
                }
            }
        }
        return result;
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
    };

}
