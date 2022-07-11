package com.guan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guan.entiity.ValidateInfo;
import com.guan.mapper.ValidateInfoMapper;
import com.guan.service.ValidInfoService;
import org.springframework.stereotype.Service;

@Service
public class ValidInfoServiceImpl extends ServiceImpl<ValidateInfoMapper, ValidateInfo> implements ValidInfoService {
}
