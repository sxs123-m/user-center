package com.sun.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.usercenter.model.domain.User;
import com.sun.usercenter.service.UserService;
import com.sun.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 8615941515990
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-03-20 20:37:26
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




