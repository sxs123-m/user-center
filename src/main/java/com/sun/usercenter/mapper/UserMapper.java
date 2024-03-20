package com.sun.usercenter.mapper;

import com.sun.usercenter.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 8615941515990
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-03-20 20:37:26
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




