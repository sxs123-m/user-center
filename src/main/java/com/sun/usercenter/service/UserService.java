package com.sun.usercenter.service;

import com.sun.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 8615941515990
* @description 针对表【user】的数据库操作Service
* @createDate 2024-03-20 20:37:26
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册接口
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 二次密码
     * @return 注册成功返回用户id，注册失败返回-1
     */
    public Long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录接口
     * @param userAccount 账号
     * @param userPassword 密码
     * @return 成功返回脱敏后的User对象，失败返回null
     */
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request);
}
