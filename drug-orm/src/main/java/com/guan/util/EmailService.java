package com.guan.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendCode(String emailAddress, Integer validCode) {
        //简单邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // config your own e-mail address
        simpleMailMessage.setFrom("********");
        simpleMailMessage.setTo(emailAddress);
        simpleMailMessage.setSubject("欢迎注册");
        simpleMailMessage.setText("Your valid code is : " + validCode);
        mailSender.send(simpleMailMessage);
    }
}
