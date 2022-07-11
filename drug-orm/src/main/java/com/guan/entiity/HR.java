package com.guan.entiity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_hr")
public class HR {

    @TableId(value = "hr_id", type = IdType.AUTO)
    private Integer hrID;

    private String username;

    private String password;

    private Integer ident;

}
