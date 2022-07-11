package com.guan.entiity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("t_verify_code")
@NoArgsConstructor
@AllArgsConstructor
public class ValidateInfo {
    @TableId(value = "code_id", type = IdType.AUTO)
    private Integer validationCodeID;

    private String username;

    private Integer validCode;

}
