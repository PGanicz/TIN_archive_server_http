package com.websocket.game.web;

import com.websocket.game.DAO.UserRepository;
import com.websocket.game.domain.User;
import com.websocket.game.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    public UserRepository userRepository;

    /*
     *  This method provides the name of current user
     */
    @RequestMapping(method = RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public void register(@RequestBody User user) throws UserAlreadyExistsException {
        User found = userRepository.findUserByEmail(user.email);
        if (found != null)
            throw new UserAlreadyExistsException("User with such email: " + found.email + " already exists!");
        userRepository.save(user);
    }
}
