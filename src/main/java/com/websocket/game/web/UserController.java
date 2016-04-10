package com.websocket.game.web;

import com.websocket.game.DAO.UserRepository;
import com.websocket.game.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by piotr on 10.04.16.
 */
@RestController
@RequestMapping(value="/user")
public class UserController
{

    @Autowired
    public UserRepository userRepository;
    /*
     *  This method provides the name of current user
     */
    @RequestMapping(method = RequestMethod.GET)
    public Principal user(Principal user)
    {
        return user;
    }

    @RequestMapping(value="/register",method = RequestMethod.POST)
    public void register(@ModelAttribute("user") User user)
    {
        userRepository.save(user);
    }


}
