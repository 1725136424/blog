package com.study.pojo.menu;

import java.io.Serializable;
import java.util.List;

import com.study.pojo.permission.Permission;
import lombok.Data;

/**
 * menu
 * @author 
 */
@Data
public class Menu implements Serializable {
    private Long id;

    private String text;

    private String url;

    private Long parentId;

    private Long permissionId;

    private Menu parent;

    private List<Menu> children;

    private Permission permission;

    private static final long serialVersionUID = 1L;
}