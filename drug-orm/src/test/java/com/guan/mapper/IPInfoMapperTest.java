package com.guan.mapper;

import com.guan.entiity.IPInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IPInfoMapperTest {

    @Autowired
    private IPInfoMapper ipInfoMapper;

    @Test
    void test() {
        ipInfoMapper.selectList(null).forEach(System.out::println);
    }
}