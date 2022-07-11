package com.guan.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DrugMapperTest {

    @Autowired
    DrugMapper drugMapper;

    @Test
    void test() {

        System.out.println(drugMapper.selectFOREIGN_KEY_CHECKS());
        drugMapper.freeFOREIGN_KEY_CHECKS();
        System.out.println(drugMapper.selectFOREIGN_KEY_CHECKS());
        drugMapper.regainFOREIGN_KEY_CHECKS();
        System.out.println(drugMapper.selectFOREIGN_KEY_CHECKS());

    }

}