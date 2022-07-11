package com.guan.service.impl;

import com.guan.entiity.Drug;
import com.guan.service.DrugService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DrugServiceImplTest {

    @Autowired
    private DrugServiceImpl drugService;


    @Test
    void findAllData() {
        System.out.println(drugService.findAllData(3));
    }

    @Test
    void findByPageAndLimit() {
        drugService.findDataByPageAndLimit(1, 10, 3);
    }

    @Test
    void test() {
//        System.out.println(drugService.insertEntity(
//                new Drug(1001, "藿香", "芳香化浊 和中止呕 发表解暑", "克", 2, 100)));
        drugService.deleteById(1001);
    }

}