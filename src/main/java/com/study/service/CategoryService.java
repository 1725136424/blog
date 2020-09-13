package com.study.service;

import com.study.pojo.category.Category;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.permission.Permission;

import java.util.List;

public interface CategoryService {

    PageResult<Category> findPage(QueryVo vo);

    void fillChildren(List<Category> rows);

    void fillChildren(Category row);

    void save(Category category);

    void edit(Category category);

    void delete(Long id);

    List<Category> findParent();

    Category findById(Long categoryId);

    Category findByName(String searchCategory);

    List<Category> findByParentId(Long id);

    List<Category> findAll();

    void addAttachDate(List<Category> categories);

    List<Long> findSelfAndChildren(Long categoryId);
}
