package com.websocket.game.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfiguration {

    @Value("${mail.protocol}")
    private String protocol;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.port}")
    private int port;
    @Value("${mail.smtp.auth}")
    private boolean auth;
    @Value("${mail.smtp.starttls.enable}")
    private boolean starttls;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();

        mailProperties.put("mail.smtp.auth", auth);
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.smtp.starttls.required", "true");
        mailProperties.put("mail.smtp.EnableSSL.enable", "true");
        mailSender.setJavaMailProperties(mailProperties);

        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.smtp.EnableSSL.enable", "true");

        mailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailProperties.setProperty("mail.smtp.socketFactory.fallback", "false");
        mailProperties.setProperty("mail.smtp.port", "" + port);
        mailProperties.setProperty("mail.smtp.socketFactory.port", "" + port);

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        return mailSender;
    }
}