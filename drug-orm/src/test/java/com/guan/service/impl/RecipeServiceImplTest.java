package com.guan.service.impl;

import com.guan.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class RecipeServiceImplTest {


    @Autowired
    private RecipeService recipeService;

    @Test
    void test() {
        HashMap<String, Integer> items = new HashMap<>();
        items.put("黄芩", 100);
        items.put("人参", 100);
        System.out.println(recipeService.createRecipe(items, "gcl"));
    }

}