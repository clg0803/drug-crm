package com.guan.entiity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_drug")
@ToString
public class Drug {

    @TableId(value = "drug_id")
    private Integer drugID;

    private String drugName;

    private String description;

    private String unit;

    @TableField(value = "class")
    private Integer drugClass;

    private Integer inventory;

}
