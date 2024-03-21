package com.sun.usercenter.model.request;


import lombok.Data;

/**
 * Description: 用户注册请求体
 *
 * @Author sun
 * @Create 2024/3/21 13:26
 * @Version 1.0
 */
@Data
public class UserRegisterRequest {
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
