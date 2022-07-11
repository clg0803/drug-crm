package com.guan.service.impl;

import com.guan.service.HRService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HRServiceImplTest {

    @Autowired
    HRServiceImpl hrService;


    @Test
    void checkForLogin() {
        System.out.println(hrService.checkForLogin("admin", "123456", 3));
    }

    @Test
    void test() {
        System.out.println(hrService.doValidateAndStoreInfo("gcl", "sdsdsds", 842491, 1));
    }
}