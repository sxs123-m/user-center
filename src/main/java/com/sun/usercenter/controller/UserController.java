package com.sun.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.usercenter.model.domain.User;
import com.sun.usercenter.model.request.UserLoginRequest;
import com.sun.usercenter.model.request.UserRegisterRequest;
import com.sun.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sun.usercenter.contant.UserConstant.ADMIN_ROLE;
import static com.sun.usercenter.contant.UserConstant.USER_LOGIN_SAVE;

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

    /**
     * 处理用户注册
     *
     * @param userRegisterRequest
     * @return 成功返回注册用户id, 失败返回-1
     */
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

        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    /**
     * 处理用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return 成功返回用户登录状态信息，失败返回null
     */
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

    /**
     * 根据用户名进行模糊查询
     *
     * @param username
     * @return 成功返回信息，失败返回null
     */
    @GetMapping("/search")
    public List<User> searchUsers(String username, HttpServletRequest request) {
        // 仅管理员可查询
        if (!isAdmin(request)) {
            return new ArrayList<>();
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            userQueryWrapper.like("username", username);
        }
        List<User> userList = userService.list(userQueryWrapper);

        // 将查询到的用户数据进行脱敏

        // 这里的逻辑就是把查询到User对象列表使用user来遍历然后对每个user对象进行脱敏最后组合成一个新的list
        return userList.stream().map(user -> {
           return userService.getCleanUser(user);
        }).collect(Collectors.toList());
    }

    /**
     * 根据id进行逻辑删除（只要配置了MyBatisPlus的逻辑删除即可进行自动逻辑删除）
     *
     * @param id
     * @return 成功返回true，失败返回false
     */
    @PostMapping("/delete")
    public boolean deleteUser(long id, HttpServletRequest request) {
        // 仅管理员可删除
        if (!isAdmin(request)) {
            return false;
        }

        if (id <= 0) {
            return false;
        }
        return userService.removeById(id); // 这里会自动逻辑删除
    }

    /**
     * 判断是否是管理员
     *
     * @param request
     * @return 布尔
     */
    public boolean isAdmin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER_LOGIN_SAVE);
        return user != null && user.getRole() == ADMIN_ROLE;
    }

}































































