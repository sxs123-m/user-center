package com.sun.usercenter;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.sun.usercenter.mapper.UserMapper;
import com.sun.usercenter.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 孙显圣
 * @version 1.0
 */
@SpringBootTest
// 如果使用的是import org.junit.Test; 这个测试类则需要加上这个注解，否则依赖不会自动注入
//@RunWith(SpringRunner.class)
public class SampleTest {

    // 依赖注入Mapper接口的代理对象，使用代理对象直接调用接口方法，框架会自动找到方法的实现
    @Resource
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }

}