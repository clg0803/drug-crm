package com.guan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guan.entiity.Drug;
import com.guan.entiity.Recipe;
import com.guan.mapper.DrugMapper;
import com.guan.mapper.RecipeMapper;
import com.guan.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.Map;

@Service
public class RecipeServiceImpl extends ServiceImpl<RecipeMapper, Recipe> implements RecipeService {

    @Autowired
    DrugMapper drugMapper;

    @Autowired
    RecipeMapper recipeMapper;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Boolean createRecipe(Map<String, Integer> recipeItems, String username) {
        QueryWrapper<Drug> drugQueryWrapper = new QueryWrapper<>();
        try {
            // 扣减库存
            recipeItems.forEach((drugName, num) -> {
                // 清空前置条件
                drugQueryWrapper.clear();
                drugQueryWrapper.eq("drug_name", drugName);
                Drug drug2update = drugMapper.selectOne(drugQueryWrapper);
                drug2update.setInventory(drug2update.getInventory() - num);
                drugMapper.updateById(drug2update);
            });
            // 生成处方
            Recipe recipe = new Recipe();
            recipe.setCreateTime(new Timestamp(System.currentTimeMillis()));
            recipe.setDocName(username);
            recipe.setDetail(recipeItems);
            recipeMapper.insert(recipe);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
