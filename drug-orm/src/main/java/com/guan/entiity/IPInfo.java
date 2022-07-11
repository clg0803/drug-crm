package com.guan.entiity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("t_ip_info")
@AllArgsConstructor
@NoArgsConstructor
public class IPInfo {

    @TableId(value = "info_id")
    private Integer infoID;

    @TableField(value = "ip_addr")
    private String ipAddr;

    @TableField(value = "hr_id")
    private Integer hrID;
}
