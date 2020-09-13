package com.study.pojo.category;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * category
 * @author 
 */
@Data
public class Category implements Serializable {
    private Long id;

    private String name;

    private Integer sort;

    private Long parentId;

    private List<Category> children;

    private Category parent;

    private static final long serialVersionUID = 1L;
}