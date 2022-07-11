package com.guan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.guan.entiity.HR;
import com.guan.entiity.HRError;
import com.guan.entiity.ValidateInfo;
import com.guan.mapper.HRMapper;
import com.guan.mapper.ValidateInfoMapper;
import com.guan.service.HRService;
import com.guan.service.ValidInfoService;
import com.guan.util.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HRServiceImpl implements HRService {

    @Autowired
    private HRMapper hrMapper;

    @Autowired
    private ValidateInfoMapper validateInfoMapper;

   @Autowired
   private ValidInfoService validInfoService;

    @Autowired
    private EmailService emailService;



    /**
     * @param username
     * @param password
     * @return 1. 登录成功 -> "non error"
     * 2. 登录失败 -> "login failed"
     */
    @Override
    public HRError checkForLogin(String username, String password, Integer ident) {
        HRError hrError = new HRError();
        QueryWrapper<HR> hrQueryWrapper = new QueryWrapper<HR>();
        hrQueryWrapper.eq("username", username)
                .eq("password", password)
                .eq("ident", ident);
        if (hrMapper.exists(hrQueryWrapper)) {
            System.out.println(" find user INFO ^_^ ");
            hrError.setType("non error");
        } else {
            System.out.println(" user not exists ಠ_ಠ ");
            hrError.setType("login failed");
        }
        return hrError;
    }

    @Override
    public HRError sandCodeAndSaveInfo(String username, String emailAddress, Integer ident) {
        System.out.println("Entering method : <sandCodeAndSaveInfo> ... ");
        HRError hrError = new HRError();
        Integer validCode = (int) ((Math.random() * 9 + 1) * 100000);
        System.out.println("validCode generated : " + validCode);
        if (dupUsername(username)) {
            hrError.setType("duplicate username");
        } else if (!sendCode(emailAddress, validCode)) {
            hrError.setType("email-sending failed");
        } else {
            // saveOrUpdate [username, validCode] to `t_verify_code`
            saveOrUpdate(username, validCode);
            hrError.setType("non error");
        }
        return hrError;
    }


    @Override
    public HRError doValidateAndStoreInfo(String username, String password, Integer validationCode, Integer ident) {
        System.out.println("Entering method : <doValidateAndStoreInfo> ... ");
        HRError hrError = new HRError();
        if (!existInValidationTable(username)) {
            hrError.setType("username undefined");
        } else if (!validationCodeMatch(username, validationCode)) {
            hrError.setType("vCode mismatched");
        } else {
            storeInHrTable(username, password, ident);
            hrError.setType("non error");
        }
        return hrError;
    }

    /**
     * 检查 `t_hr` 表中是否存在相同的 `username`
     */
    private Boolean dupUsername(String username) {
        QueryWrapper<HR> queryWrapper = new QueryWrapper<>();
        // 由于是 unique 的 所以直接判断数量是否唯一
        queryWrapper.eq("username", username);
        if (hrMapper.exists(queryWrapper)) {
            return true;
        }
        return false;
    }
    private Boolean sendCode(String emailAddress, Integer validCode) {
        emailService.sendCode(emailAddress, validCode);
        return true;
    }

    /**
     * saveOrUpdate [username, validCode] to `t_verify_code`
     */
    private Boolean saveOrUpdate(String username, Integer validCode) {
        ValidateInfo info = new ValidateInfo();
        info.setUsername(username);
        info.setValidCode(validCode);

        UpdateWrapper<ValidateInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", username);
        updateWrapper.set("valid_code", validCode);

        return validInfoService.saveOrUpdate(info, updateWrapper);
    }

    /**
     * @param username
     * @return `username` 是否在 `t_verify_code` 中存在
     */
    private Boolean existInValidationTable(String username) {
        QueryWrapper<ValidateInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return validateInfoMapper.exists(queryWrapper);
    }

    private Boolean validationCodeMatch(String username, Integer code) {
        QueryWrapper<ValidateInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("valid_code", code);
        return validateInfoMapper.exists(queryWrapper);
    }

    private Integer storeInHrTable(String username, String password, Integer ident) {
        HR hr = new HR();
        hr.setUsername(username);
        hr.setPassword(password);
           hr.setIdent(ident);
        return hrMapper.insert(hr);
    }
}
