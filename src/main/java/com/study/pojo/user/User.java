package com.study.pojo.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.pojo.role.Role;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * user
 * @author
 */
@Data
public class User implements Serializable {
    private Long id;

    private String name;

    private String username;

    private String password;

    private String picture;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registerDate;

    private String validateCode;

    private Date outDate;

    private List<Role> roles;

    // 非数据库字段
    // 发文数量
    private Long publishCount;

    // 粉丝数量
    private Long fansCount;

    // 关注数量
    private Long followCount;

    // 评论数量
    private Long reviewCount;

    private static final long serialVersionUID = 1L;
}