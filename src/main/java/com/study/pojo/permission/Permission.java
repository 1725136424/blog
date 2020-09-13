package com.study.pojo.permission;

import java.io.Serializable;
import java.util.List;

import com.study.pojo.role.Role;
import lombok.Data;

/**
 * permission
 * @author 
 */
@Data
public class Permission implements Serializable {
    private Long id;

    private String name;

    private String resource;

    private List<Role> roles;

    private Long parentId;

    private List<Permission> children;

    private static final long serialVersionUID = 1L;
}