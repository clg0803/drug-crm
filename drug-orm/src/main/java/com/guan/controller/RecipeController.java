package com.guan.controller;

import com.guan.entiity.HR;
import com.guan.entiity.Recipe;
import com.guan.service.IPInfoService;
import com.guan.service.RecipeService;
import com.guan.util.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/recipe/")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private IPInfoService ipInfoService;

    @GetMapping("/all")
    public List<Recipe> getAllRecipe() {
        return recipeService.list();
    }

    @PostMapping("/add")
    public Boolean addRecipe(@RequestBody Map<String, Integer> recipeItems, HttpServletRequest request) {
        String ipAddr = IPUtil.getIpAddr(request);
        // get username by IP
        HR hr = ipInfoService.getIPInfo(ipAddr);
        String username = hr.getUsername();
        System.out.println("Request IP : " + ipAddr);
        System.out.println(username);

        System.out.println(recipeItems);

        return recipeService.createRecipe(recipeItems, username);
    }
}
