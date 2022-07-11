package com.guan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guan.entiity.HR;
import com.guan.entiity.IPInfo;
import com.guan.entiity.ValidateInfo;

public interface IPInfoService extends IService<IPInfo> {

    Boolean saveOrUpdate(String ipAddr, String username);

    HR getIPInfo(String ipAddr);

}
