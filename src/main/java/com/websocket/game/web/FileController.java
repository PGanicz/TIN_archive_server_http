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
        User u = userReposiotry.findUserByUsername(p.getName());
        if(u!= null) {
            response.setHeader("Content-Disposition", "attachment; filename="+fileName);
            return new FileSystemResource(getFileFor(u.username,fileName,device,stamp));
        }else
       return null;
    }
    @RequestMapping(value = "/remove/{file_name}/{device}/{timestamp}", method = RequestMethod.GET)
    @ResponseBody
    public void removeFile(@PathVariable("file_name") String fileName,
                           @PathVariable("device") String device,
                           @PathVariable("timestamp") String stamp, Principal p) {
            User u = userReposiotry.findUserByUsername(p.getName());
            if(u!= null) {

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
    };

}
