package com.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.dao.article.ArticleDao;
import com.study.pojo.article.Article;
import com.study.pojo.article.ArticleExample;
import com.study.pojo.category.Category;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.user.User;
import com.study.service.ArticleService;
import com.study.service.CategoryService;
import com.study.util.HtmlUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageResult<Article> findPage(QueryVo queryVo, String searchCategory) {
        // 获取当前用户下的所有文章
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        PageHelper.startPage(queryVo.getPage(), queryVo.getRows());
        ArticleExample articleExample = new ArticleExample();
        articleExample.setOrderByClause("edit_date desc");
        ArticleExample.Criteria criteria = articleExample.createCriteria();
        criteria.andUserIdEqualTo(user.getId());
        if (queryVo.getKeywords() != null) {
            criteria.andTitleLike("%" + queryVo.getKeywords() + "%");
        }
        if (!"".equals(searchCategory) && searchCategory != null) {
            // 查询当前分类以及其子分类下的数据
            Category category = categoryService.findByName(searchCategory);
            Long id = category.getId();
            List<Category> children = categoryService.findByParentId(id);
            children.add(category);
            ArrayList<Long> ids = new ArrayList<>();
            for (Category child : children) {
                ids.add(child.getId());
            }
            criteria.andCategoryIdIn(ids);
        }
        List<Article> articles = articleDao.selectByExample(articleExample);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        return new PageResult<>(pageInfo.getTotal(), articles);
    }

    @Override
    public void fillCategories(List<Article> articles) {
        for (Article article : articles) {
            fillCategories(article);
        }
    }

    @Override
    public void fillCategories(Article article) {
        Long categoryId = article.getCategoryId();
        Category category = categoryService.findById(categoryId);
        article.setCategory(category);
    }

    @Override
    public void save(Article article) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        // 设置文章数据
        String contentStr = HtmlUtils.html2Str(article.getContent());
        article.setSubTitle(contentStr.substring(0, 30));
        article.setPublishDate(new Date());
        article.setEditDate(new Date());
        article.setUserId(user.getId());
        // 保存
        articleDao.insertSelective(article);
    }


    @Override
    public void edit(Article article) {
        String content = HtmlUtils.html2Str(article.getContent());
        article.setSubTitle(content.substring(0, 30));
        article.setEditDate(new Date());
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    public void delete(Long id) {
        articleDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Article> findAll() {
        ArticleExample articleExample = new ArticleExample();
        articleExample.setOrderByClause("edit_date desc");
        return articleDao.selectByExample(articleExample);
    }

    @Override
    public void convertContent2Text(List<Article> articles) {
        for (Article article : articles) {
            convertContent2Text(article);
        }
    }

    @Override
    public void convertContent2Text(Article article) {
        String content = article.getContent();
        article.setContent(HtmlUtils.html2Str(content).replaceAll(("\\u00A0+|\\s*"), ""));
    }

    @Override
    public PageInfo<Article> findByCategoryId(QueryVo vo, Long categoryId) {
        boolean isSearch = false;
        if (vo.getKeywords() != null && !"".equals(vo.getKeywords())) {
            isSearch = true;
        }
        List<Article> articles = null;
        // 获取其数据和其子类数据
        if (categoryId == -1) {
            PageHelper.startPage(vo.getPage(), vo.getRows());
            if (!isSearch) {
                articles = findAll();
            } else {
                articles = findBySearch(vo.getKeywords());
            }

        } else {
            // 查询当前分类以及其子分类数据
            List<Long> cids = categoryService.findSelfAndChildren(categoryId);
            ArticleExample articleExample = new ArticleExample();
            articleExample.setOrderByClause("edit_date desc");
            ArticleExample.Criteria criteria = articleExample.createCriteria();
            criteria.andCategoryIdIn(cids);
            ArticleExample.Criteria criteria1 = articleExample.createCriteria();
            criteria1.andTitleLike("%" + vo.getKeywords() + "%");
            PageHelper.startPage(vo.getPage(), vo.getRows());
            articles = articleDao.selectByExample(articleExample);
        }
        return new PageInfo<>(articles);
    }

    @Override
    public List<Article> findBySearch(String keywords) {
        ArticleExample articleExample = new ArticleExample();
        articleExample.setOrderByClause("edit_date desc");
        ArticleExample.Criteria criteria = articleExample.createCriteria();
        criteria.andTitleLike("%" + keywords+ "%");
        return articleDao.selectByExample(articleExample);
    }

    @Override
    public Article findById(Long articleId) {
        return articleDao.selectByPrimaryKey(articleId);
    }

    @Override
    public Long findPublishCountByUid(Long id) {
        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria criteria = articleExample.createCriteria();
        criteria.andUserIdEqualTo(id);
        return articleDao.countByExample(articleExample);
    }

    @Override
    public List<Article> findByUid(Long userId) {
        ArticleExample articleExample = new ArticleExample();
        articleExample.setOrderByClause("edit_date desc");
        ArticleExample.Criteria criteria = articleExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return articleDao.selectByExample(articleExample);
    }

    @Override
    public PageInfo<Article> findByUidAndCid(QueryVo vo, Long userId, Long categoryId) {
        List<Article> articles = null;
        if (categoryId == -1) {
            PageHelper.startPage(vo.getPage(), vo.getRows());
            articles = findByUid(userId);
        } else {
            List<Long> cids = categoryService.findSelfAndChildren(categoryId);
            ArticleExample articleExample = new ArticleExample();
            articleExample.setOrderByClause("edit_date desc");
            ArticleExample.Criteria criteria = articleExample.createCriteria();
            criteria.andUserIdEqualTo(userId);
            criteria.andCategoryIdIn(cids);
            PageHelper.startPage(vo.getPage(), vo.getRows());
            articles = articleDao.selectByExample(articleExample);
        }
        return new PageInfo<>(articles);
    }
}
