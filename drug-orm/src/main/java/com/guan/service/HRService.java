package com.guan.service;

import com.guan.entiity.HRError;

public interface HRService {

    /**
     *
     * @param username
     * @param password
     * @return
     *          1. 登录成功  -> "non error"
     *          2. 登录失败  -> "login failed"
     */
    HRError checkForLogin(String username, String password, Integer ident);

    /**
     * 不跳转界面 - 查重 -> 发送验证码 -> 写数据
     * @param username
     * @param emailAddress
     * @param ident
     * @return `HRError` 对象 其 `type`
     *          1. 重复用户名    -> "duplicate username"
     *          2. 发送验证码失败 -> "email-sending failed"
     *          3. 校验成功      -> "non error"
     */
    HRError sandCodeAndSaveInfo(String username, String emailAddress, Integer ident);

    /**
     * 不跳转界面 - 查用户名是否被定义 -> 根据用户名查校验码比较
     * @param username
     * @param password
     * @param validationCode
     * @param ident
     * @return 将信息封装在 HRError 对象中的 `type` 属性里面 :
     *          1. 用户名未定义    -> "username undefined"
     *          2. 输入的验证码有误 -> "vCode mismatched"
     *          3. 将 [username, password, ident] 存在 `t_hr` 表中
     *          4. 校验成功 -> "non error"
     */
    HRError doValidateAndStoreInfo(String username, String password, Integer validationCode, Integer ident);

}
