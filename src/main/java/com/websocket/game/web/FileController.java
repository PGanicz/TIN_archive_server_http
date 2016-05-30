package com.websocket.game.web;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;

/**
 * Created by piotr on 30.05.16.
 */
@RestController
@RequestMapping(value = "/files")
public class FileController {
    @RequestMapping(value = "/{file_name}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getFile(@PathVariable("file_name") String fileName) {
        return new FileSystemResource(getFileFor(fileName));
    }

    private String getFileFor(String fileName) {
        return "path"; //TODO
    }
}
