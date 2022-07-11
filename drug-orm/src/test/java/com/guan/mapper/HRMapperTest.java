package com.guan.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HRMapperTest {

    @Autowired
    HRMapper hrMapper;

    @Test
    void test() {
        hrMapper.selectList(null).forEach(System.out::println);
    }

}