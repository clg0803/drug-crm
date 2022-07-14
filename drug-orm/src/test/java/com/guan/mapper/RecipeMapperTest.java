package com.guan.mapper;

import com.guan.entiity.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecipeMapperTest {

    @Autowired
    RecipeMapper recipeMapper;


    @Test
    void test() {
        HashMap<String, Integer> mp = new HashMap<String, Integer>() {
            {
                put("人参", 3);
            }

            {
                put("陈皮", 3);
            }
            {
                put("炉甘石洗剂", 3);
            }
        };

        Recipe recipe = new Recipe();
        recipe.setCreateTime(new Timestamp(System.currentTimeMillis()));
        recipe.setDocName("zhongyi");
        recipe.setDetail(mp);
        int insert = recipeMapper.insert(recipe);
    }

}