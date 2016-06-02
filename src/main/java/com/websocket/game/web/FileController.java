package com.websocket.game.web;

import com.websocket.game.DAO.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by piotr on 30.05.16.
 */
@RestController
@RequestMapping(value = "/files")
public class FileController {
    @Autowired
    public FileRepository fileRepository;

    //w kazdej metodzie trzeba sprawdzic czy user to moze.
    @RequestMapping(value = "/download/{file_name}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getFile(@PathVariable("file_name") String fileName,HttpServletResponse response) {
        System.out.println("File: "+fileName);
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        return new FileSystemResource(getFileFor(fileName));
    }
    @RequestMapping(value = "/remove/{file_name}", method = RequestMethod.GET)
    @ResponseBody
    public void getFile(@PathVariable("file_name") String fileName) {
        System.out.println("File: "+fileName);

    }
    private String getFileFor(String fileName) {
        return "/home/piotr/Obrazy/Tapety/1920/"+fileName; //TODO
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public String[] getAllFiles()
    {
        //get names from database
      return new String[]{"one","two","jakies"};
    };
}
