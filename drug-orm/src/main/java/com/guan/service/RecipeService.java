package com.guan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guan.entiity.Recipe;

import java.util.Map;

public interface RecipeService extends IService<Recipe> {

    /**
     *  1.扣减库存 2.增加处方 包含在一个事务中
     * @param recipeItems [药品名称, 该药品的数量]
     * @return 操作是否成功
     */
    Boolean createRecipe(Map<String, Integer> recipeItems, String username);

}
