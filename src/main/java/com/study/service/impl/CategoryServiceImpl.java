package com.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.dao.category.CategoryDao;
import com.study.pojo.category.Category;
import com.study.pojo.category.CategoryExample;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.permission.Permission;
import com.study.pojo.permission.PermissionExample;
import com.study.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageResult<Category> findPage(QueryVo vo) {
        PageHelper.startPage(vo.getPage(), vo.getRows());
        CategoryExample categoryExample =  new CategoryExample();
        categoryExample.setOrderByClause("sort desc");
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        if (vo.getKeywords() != null) {
            criteria.andNameLike("%" + vo.getKeywords() + "%");
        } else {
            criteria.andParentIdIsNull();
        }
        List<Category> categories = categoryDao.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categories);
        return new PageResult<>(pageInfo.getTotal(), categories);
    }

    @Override
    public void fillChildren(List<Category> rows) {
        for (Category row : rows) {
            fillChildren(row);
        }
    }

    @Override
    public void fillChildren(Category category) {
        Long id = category.getId();
        List<Category> categories = findByParentId(id);
        if (categories != null && categories.size() > 0) {
            category.setChildren(categories);
            fillChildren(categories);
        }
    }

    public List<Category> findByParentId(Long id) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort desc");
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andParentIdEqualTo(id);
        return categoryDao.selectByExample(categoryExample);
    }

    @Override
    public void save(Category category) {
        categoryDao.insertSelective(category);
    }

    @Override
    public void edit(Category category) {
        categoryDao.updateByPrimaryKeySelective(category);
    }

    @Override
    public void delete(Long id) {
        categoryDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Category> findParent() {
        CategoryExample categoryExample =  new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andParentIdIsNull();
        return categoryDao.selectByExample(categoryExample);
    }

    @Override
    public Category findById(Long categoryId) {
        return categoryDao.selectByPrimaryKey(categoryId);
    }

    @Override
    public Category findByName(String searchCategory) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andNameEqualTo(searchCategory);
        List<Category> categories = categoryDao.selectByExample(categoryExample);
        if (categories != null && categories.size() > 0) {
            return categories.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Category> findAll() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort desc");
        return categoryDao.selectByExample(categoryExample);
    }

    @Override
    public void addAttachDate(List<Category> categories) {
        Category category = new Category();
        category.setId((long) -1);
        category.setName("所有文章");
        categories.add(0, category);
    }

    @Override
    public List<Long> findSelfAndChildren(Long categoryId) {
        // 获取当前分类
        Category category = findById(categoryId);
        // 获取分类下的所有子id
        List<Category> categories = findByParentId(categoryId);
        categories.add(category);
        ArrayList<Long> cids = new ArrayList<>();
        for (Category category1 : categories) {
            cids.add(category1.getId());
        }
        return cids;
    }
}
