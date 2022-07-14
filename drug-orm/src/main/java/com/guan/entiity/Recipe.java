package com.guan.entiity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Map;

@Data
@TableName(value = "t_recipe", autoResultMap = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @TableId(value = "recipe_id", type = IdType.AUTO)
    private Long recipeID;

    @TableField(value = "doc_name")
    private String docName;

    @TableField(value = "create_time")
    private Timestamp createTime;

    @TableField(value = "detail", typeHandler = JacksonTypeHandler.class)
    private Map<String, Integer> detail;
}
