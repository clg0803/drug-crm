package com.guan.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guan.entiity.Drug;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DrugMapperTest {

    @Autowired
    DrugMapper drugMapper;

    @Test
    void test() {
        Drug drug = drugMapper.selectById(7005);
        drug.setInventory(1000);
        drugMapper.updateById(drug);
//        System.out.println(drugMapper.selectFOREIGN_KEY_CHECKS());
//        drugMapper.freeFOREIGN_KEY_CHECKS();
//        System.out.println(drugMapper.selectFOREIGN_KEY_CHECKS());
//        drugMapper.regainFOREIGN_KEY_CHECKS();
//        System.out.println(drugMapper.selectFOREIGN_KEY_CHECKS());

    }

}