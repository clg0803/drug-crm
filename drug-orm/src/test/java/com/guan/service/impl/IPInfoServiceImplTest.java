package com.guan.service.impl;

import com.guan.service.IPInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IPInfoServiceImplTest {

    @Autowired
    private IPInfoService service;

    @Test
    void saveOrUpdate() {
        service.saveOrUpdate("0:0:0:0:0:0:0:1", "admin");
    }

    @Test
    void getIPInfo() {
        System.out.println(service.getIPInfo("0:0:0:0:0:0:0:1"));
    }
}