package com.sun.usercenter.controller;

import com.sun.usercenter.model.domain.User;
import com.sun.usercenter.model.request.UserLoginRequest;
import com.sun.usercenter.model.request.UserRegisterRequest;
import com.sun.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 *
 * @Author sun
 * @Create 2024/3/21 13:04
 * @Version 1.0
 */
@RestController // 作为一个Controller注入容器，并将返回结果转换为json
@RequestMapping("/user") // restful风格的请求
public class UserController {
    @Resource
    private UserService userService; // 注入针对Service接口的bean对象，可以调用接口的方法

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        // 首先判断数据是否成功封装
        if (userRegisterRequest == null) {
            return null;
        }

        // 对封装的数据进行校验，如果有一个是空直接返回null
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }

        // 调用Service层的注册方法，如果成功注册，则返回注册成功的用户id, 否则返回-1
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public User userRegister(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 首先判断数据是否成功封装
        if (userLoginRequest == null) {
            return null;
        }

        // 对封装的数据进行校验，如果有一个是空直接返回null
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        return userService.doLogin(userAccount, userPassword, request);
    }

}































































