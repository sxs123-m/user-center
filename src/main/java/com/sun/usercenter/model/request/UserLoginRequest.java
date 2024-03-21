package com.sun.usercenter.model.request;

import lombok.Data;

/**
 * Description: 用户登录请求体
 *
 * @Author sun
 * @Create 2024/3/21 13:39
 * @Version 1.0
 */
@Data
public class UserLoginRequest {
    private String userAccount;
    private String userPassword;
}
