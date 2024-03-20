package com.sun.usercenter.service;

import com.sun.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


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
        //添加断言
        Assertions.assertTrue(save);
    }
}