package com.sun.usercenter.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.usercenter.model.domain.User;
import com.sun.usercenter.service.UserService;
import com.sun.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 8615941515990
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-03-20 20:37:26
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    /*
    盐值：用于密码混淆
     */
    private static final  String SALT = "sun";
    /*
    注入针对接口的代理对象，可以直接调用接口的方法
     */
    @Resource
    private UserMapper userMapper;
    /*
    用户登录态的key
     */
    private static final String USER_LOGIN_SAVE = "userLoginState";


    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1 校验
        // 验证非空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1L;
        }
        // 密码不小于8位
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1L;
        }
        // 账户不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        long count = this.count(userQueryWrapper);
        if (count > 0) {
            return -1L;
        }
        // 账户4到16位，不能包含特殊字符
        if (!userAccount.matches("^[a-zA-Z0-9]{4,16}$")) {
            return -1L;
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1L;
        }
        // 2 密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3 数据库插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean save = this.save(user);

        if (!save) {
            return -1L;
        }

        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1 检验用户名和密码是否合法，如果不合法就没必要去数据库查询
        // 验证非空
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        // 账户4到16位，不能包含特殊字符
        if (!userAccount.matches("^[a-zA-Z0-9]{4,16}$")) {
            return null;
        }
        // 密码不小于8位
        if (userPassword.length() < 8) {
            return null;
        }

        // 2 密码加密进行查询
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 编写查询条件
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userPassword", encryptPassword);
        userQueryWrapper.eq("userAccount", userAccount);
        // 执行查询
        User user = userMapper.selectOne(userQueryWrapper);
        // 判断用户是否存在
        if (user == null) {
            // 输出日志
            log.info("user login faild, userAccount can not match password");
            return null;
        }

        // 3 如果登录成功，记录用户的登录态

        // 首先进行脱敏
        User cleanUser = new User();
        cleanUser.setId(user.getId());
        cleanUser.setUsername(user.getUsername());
        cleanUser.setUserAccount(user.getUserAccount());
        cleanUser.setAvatarUrl(user.getAvatarUrl());
        cleanUser.setGender(user.getGender());
        cleanUser.setPhone(user.getPhone());
        cleanUser.setEmail(user.getEmail());
        cleanUser.setUserStatus(user.getUserStatus());
        cleanUser.setCreateTime(user.getCreateTime());

        HttpSession session = request.getSession();
        // 然后将脱敏后的用户信息放入session中
        session.setAttribute(USER_LOGIN_SAVE, cleanUser);

        return cleanUser;
    }
}


































