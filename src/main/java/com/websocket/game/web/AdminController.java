package com.websocket.game.web;

import com.websocket.game.DAO.ActivateUserRepository;
import com.websocket.game.DAO.UserRepository;
import com.websocket.game.domain.ActivateUser;
import com.websocket.game.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Random;

@RestController
public class AdminController {
    private final int ACTIVATE_STRING_LEN = 128;
    private final Random random = new Random();
    private final String SERVER_URI = "localhost:7799";
    private ActivateUserRepository activateUserRepository;
    private MailSender mailSender;
    private UserRepository userRepository;

    @Autowired
    public AdminController(ActivateUserRepository activateUserRepository, MailSender mailSender, UserRepository userRepository) {
        this.activateUserRepository = activateUserRepository;
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public void isAdmin() {

    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin/unactive",method = RequestMethod.GET)
    public Iterable<User> getUnactive() {
        System.out.println(userRepository.findByActiveFalse());
        return userRepository.findByActiveFalse();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin/activate",method = RequestMethod.POST)
    void activateUser(@RequestBody User user) {
        System.out.println("Email sent");
        System.out.println(user);
        final User foundByMail = userRepository.findUserByEmail(user.email);

        System.out.println(foundByMail);
        final String activateString = generateActivateString(ACTIVATE_STRING_LEN);
        final ActivateUser activateUser = new ActivateUser(foundByMail, activateString);

        System.out.println(activateUser);
        sendActivateEmail(user.email,activateString);
        activateUserRepository.save(activateUser);
    }
    private String generateActivateString(final int length) {
        final byte[] bytes = new byte[length];
        random.nextBytes(bytes);

        return Base64.getEncoder().encodeToString(bytes).replace('+', 'X').replace('/', 'Y');
    }

    private void sendActivateEmail(final String email, final String activateString) {
        final String from = "tin432.32.11.23@gmail.com";
        final String msg = "Kliknij w link w celu aktywacji konta: http://localhost:7799/user/activate?activateString=" +
                activateString;
        final String subject = "Archive Server - aktywacja konta";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setFrom(from);
        message.setText(msg);

        mailSender.send(message);
    }
}
