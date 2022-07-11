package com.guan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guan.entiity.HR;
import com.guan.entiity.IPInfo;
import com.guan.entiity.ValidateInfo;
import com.guan.mapper.HRMapper;
import com.guan.mapper.IPInfoMapper;
import com.guan.mapper.ValidateInfoMapper;
import com.guan.service.IPInfoService;
import com.guan.service.ValidInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IPInfoServiceImpl extends ServiceImpl<IPInfoMapper, IPInfo> implements IPInfoService {

    @Autowired
    private IPInfoMapper ipInfoMapper;

    @Autowired
    private HRMapper hrMapper;

    @Override
    public Boolean saveOrUpdate(String ipAddr, String username) {
        // get `hr_id` from `t_hr`
        QueryWrapper<HR> hrQueryWrapper = new QueryWrapper<>();
        hrQueryWrapper.eq("username", username);
        hrQueryWrapper.select("hr_id");
        Integer hrID = hrMapper.selectOne(hrQueryWrapper).getHrID();

        IPInfo info = new IPInfo();
        info.setIpAddr(ipAddr);
        info.setHrID(hrID);
        UpdateWrapper<IPInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ip_addr", ipAddr);
        updateWrapper.set("hr_id", hrID);

        return this.saveOrUpdate(info, updateWrapper);
    }

    @Override
    public HR getIPInfo(String ipAddr) {
        QueryWrapper<HR> hrQueryWrapper = new QueryWrapper<>();
        hrQueryWrapper.select("username", "ident");
        hrQueryWrapper.last("INNER join t_ip_info ON t_ip_info.ip_addr = '"
                + ipAddr
                + "' and t_ip_info.hr_id = t_hr.hr_id");
        return hrMapper.selectOne(hrQueryWrapper);
    }
}
