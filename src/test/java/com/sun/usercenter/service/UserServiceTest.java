package com.sun.usercenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


/**
 * Description:
 *
 * @Author sun
 * @Create 2024/3/20 20:45
 * @Version 1.0
 */
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("test");
        user.setUserAccount("122");
        user.setAvatarUrl("34353344334342");
        user.setUserPassword("234234234324");
        user.setPhone("23423432");
        user.setEmail("234rrer334");
        boolean save = userService.save(user);
        if (save == true) {
            System.out.println("添加成功！");
        }
        // 添加断言
        Assertions.assertTrue(save);
    }

    @Test
    void userRegister() {

        // 验证账号为空
        String userAccount = "";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        Long res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1L, res);

        // 验证密码小于8位
        userAccount = "12345";
        userPassword = "12";
        checkPassword = "12";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1L, res);

        // 验证账户不在4到16位
        userAccount = "123";
        userPassword = "123456789";
        checkPassword = "123456789";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1L, res);

        // 验证账户有特殊字符
        userAccount = "12345*";
        userPassword = "123456789";
        checkPassword = "123456789";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1L, res);

        // 验证密码和校验密码不同
        userAccount = "12345";
        userPassword = "1234567890";
        checkPassword = "123456789";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1L, res);

        // 验证账号重复
        userAccount = "1234567";
        userPassword = "123456789";
        checkPassword = "123456789";
        userService.userRegister(userAccount, userPassword, checkPassword);
        userAccount = "1234567";
        userPassword = "123456789";
        checkPassword = "123456789";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1L, res);

        // 验证密码加密
        userAccount = "12345678";
        userPassword = "123456789";
        checkPassword = "123456789";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", res);
        List<User> list = userService.list(userQueryWrapper);
        System.out.println(list);

    }

}
















