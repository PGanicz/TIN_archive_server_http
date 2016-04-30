package com.websocket.game.web;

import com.websocket.game.DAO.ActivateUserRepository;
import com.websocket.game.DAO.UserRepository;
import com.websocket.game.domain.ActivateUser;
import com.websocket.game.domain.User;
import com.websocket.game.exceptions.ActivateStringNotFoundException;
import com.websocket.game.exceptions.BadPasswordException;
import com.websocket.game.exceptions.UserAlreadyExistsException;
import com.websocket.game.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final int ACTIVATE_STRING_LEN = 128;
    private final Random random = new Random();
    private final String SERVER_URI = "localhost:7799";

    private ActivateUserRepository activateUserRepository;
    private MailSender mailSender;
    private UserRepository userRepository;

    @Autowired
    public UserController(ActivateUserRepository activateUserRepository, MailSender mailSender, UserRepository userRepository) {
        this.activateUserRepository = activateUserRepository;
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public void register(@RequestBody User user) throws UserAlreadyExistsException {
        final User foundByMail = userRepository.findUserByEmail(user.email);
        final User foundByName = userRepository.findUserByUsername(user.username);

        if (foundByMail != null)
            throw new UserAlreadyExistsException("User with such email: " + foundByMail.email + " already exists!");
        else if (foundByName != null)
            throw new UserAlreadyExistsException("User with such username: " + foundByName.username + " already exists!");

        final String activateString = generateActivateString(ACTIVATE_STRING_LEN);
        final ActivateUser activateUser = new ActivateUser(user, activateString);

        sendActivateEmail(user, activateString);

        userRepository.save(user);
        activateUserRepository.save(activateUser);
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public User getUserSettings(final Principal user) throws UserNotFoundException {
        final User found = userRepository.findUserByUsername(user.getName());

        if (found == null)
            throw new UserNotFoundException("User " + user.getName() + " not found!");

        return found;
    }

    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    @ResponseBody
    public void changeUserSettings(final Principal user,
                                   @RequestParam(name = "email", required = false) String email,
                                   @RequestParam(name = "password", required = true) String password,
                                   @RequestParam(name = "new_password", required = false) String newPassword) throws UserNotFoundException, BadPasswordException {
        final User found = userRepository.findUserByUsername(user.getName());

        if (newPassword == null && email == null)
            return;
        if (found == null)
            throw new UserNotFoundException("User " + user.getName() + " not found!");
        if (!password.equals(found.password))
            throw new BadPasswordException("Podano niepoprawne haslo!");

        if (email != null)
            found.email = email;
        if (newPassword != null)
            found.password = newPassword;

        userRepository.save(found);
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public void activateUser(@RequestParam(name = "activateString") String activateString) throws ActivateStringNotFoundException {
        final ActivateUser activateUser = activateUserRepository.findActivateUserByActivateString(activateString);

        System.out.println(activateString);

        if (activateUser == null)
            throw new ActivateStringNotFoundException("Couldn't find activate string");

        final User user = activateUser.user;
        final List<ActivateUser> toDeleteList = activateUserRepository.findActivateUsersByUser(user);

        activateUserRepository.delete(toDeleteList);
        user.active = true;
        userRepository.save(user);
    }

    private String generateActivateString(final int length) {
        final byte[] bytes = new byte[length];
        random.nextBytes(bytes);

        return Base64.getEncoder().encodeToString(bytes).replace('+', 'X').replace('/', 'Y');
    }

    private void sendActivateEmail(final User user, final String activateString) {
        final String from = "gwentteam@gmail.com";
        final String msg = "Kliknij w link w celu aktywacji konta: http://localhost:7799/user/activate?activateString=" +
                activateString;
        final String subject = "Gwint - aktywacja konta";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.email);
        message.setSubject(subject);
        message.setFrom(from);
        message.setText(msg);

        mailSender.send(message);
    }
}
