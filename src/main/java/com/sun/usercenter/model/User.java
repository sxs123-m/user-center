package com.sun.usercenter.model;

import lombok.Data;

/**
 * @author 孙显圣
 * @version 1.0
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
