package com.sun.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun.usercenter.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 孙显圣
 * @version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
