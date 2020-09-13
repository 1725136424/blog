package com.study.service;

import com.github.pagehelper.PageInfo;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.review.Review;

import java.util.List;


public interface ReviewService {

    PageInfo<Review> findReviewByArticleId(Long articleId);

    void fillUser(List<Review> reviews);

    void fillUser(Review review);

    List<Review> findRemainReview(Long articleId);

    void save(Review review);

    Long findCountReview(Long id, Boolean flag);

    PageResult<Review> findUnAuthenticationReview(QueryVo vo, Long id);

    void fillArticle(List<Review> rows);

    void fillArticle(Review row);

    void editStatus(Long id, boolean b);

    void delete(Long id);

    void fillChildren(List<Review> reviews);

    void fillChildren(Review review);

    List<Review> findByParentId(Long id);

    Review findById(Long id);

    void fillParent(List<Review> reviews);

    void fillParent(Review review);

    void operateDate(List<Review> reviews);


}
