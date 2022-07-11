package com.guan.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailSenderTest {

    @Autowired
    private EmailService emailService;

    @Test
    void sendMail() {
        emailService.sendCode("coder_guanchenlu@163.com", 122333);
    }
}