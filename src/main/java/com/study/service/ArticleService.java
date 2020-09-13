package com.study.service;

import com.github.pagehelper.PageInfo;
import com.study.pojo.article.Article;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.permission.Permission;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ArticleService {

    PageResult<Article> findPage(QueryVo queryVo, String searchCategory);

    void fillCategories(List<Article> articles);

    void fillCategories(Article article);

    void save(Article article);

    void edit(Article article);

    void delete(Long id);

    List<Article> findAll();

    void convertContent2Text(List<Article> articles);

    void convertContent2Text(Article article);

    PageInfo<Article> findByCategoryId(QueryVo vo, Long categoryId);

    Article findById(Long articleId);


    Long findPublishCountByUid(Long id);

    List<Article> findByUid(Long userId);

    PageInfo<Article>  findByUidAndCid(QueryVo vo, Long userId, Long categoryId);

    List<Article> findBySearch(String keywords);
}
