package com.study.pojo.role;

import java.io.Serializable;
import java.util.List;

import com.study.pojo.permission.Permission;
import lombok.Data;

/**
 * role
 * @author 
 */
@Data
public class Role implements Serializable {
    private Long id;

    private String number;

    private String name;

    private List<Permission> permissions;

    private static final long serialVersionUID = 1L;
}