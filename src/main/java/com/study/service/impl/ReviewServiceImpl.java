package com.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.dao.review.ReviewDao;
import com.study.pojo.article.Article;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.review.Review;
import com.study.pojo.review.ReviewExample;
import com.study.pojo.user.User;
import com.study.service.ArticleService;
import com.study.service.ReviewService;
import com.study.service.UserService;
import com.study.util.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Override
    public PageInfo<Review> findReviewByArticleId(Long articleId) {
        PageHelper.startPage(1, 5);
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.setOrderByClause("remark_date desc");
        ReviewExample.Criteria criteria = reviewExample.createCriteria();
        criteria.andArticleIdEqualTo(articleId);
        criteria.andStatusEqualTo(true);
        criteria.andParentIdIsNull();
        List<Review> reviews = reviewDao.selectByExample(reviewExample);
        return new PageInfo<>(reviews);
    }

    @Override
    public void fillUser(List<Review> reviews) {
        if (reviews != null && reviews.size() > 0) {
            for (Review review : reviews) {
                fillUser(review);
            }
        }
    }

    @Override
    public void fillUser(Review review) {
        if (review != null) {
            Long userId = review.getUserId();
            User user = userService.findById(userId);
            review.setUser(user);
        }
    }

    @Override
    public List<Review> findRemainReview(Long articleId) {
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.setOrderByClause("remark_date desc");
        ReviewExample.Criteria criteria = reviewExample.createCriteria();
        criteria.andArticleIdEqualTo(articleId);
        criteria.andStatusEqualTo(true);
        criteria.andParentIdIsNull();
        List<Review> reviews = reviewDao.selectByExample(reviewExample);
        return reviews.subList(5, reviews.size());
    }

    @Override
    public void save(Review review) {
        reviewDao.insertSelective(review);
    }

    @Override
    public Long findCountReview(Long id, Boolean flag) {
        // 查询当前用户发表的所有文章
        List<Article> articles = articleService.findByUid(id);
        List<Long> aids = new ArrayList<>();
        for (Article article : articles) {
            aids.add(article.getId());
        }
        if (aids.size() > 0) {
            ReviewExample reviewExample = new ReviewExample();
            ReviewExample.Criteria criteria = reviewExample.createCriteria();
            criteria.andStatusEqualTo(flag);
            criteria.andArticleIdIn(aids);
            criteria.andParentIdIsNull();
            return reviewDao.countByExample(reviewExample);
        } else {
            return null;
        }
    }

    @Override
    public PageResult<Review> findUnAuthenticationReview(QueryVo vo, Long id) {
        PageHelper.startPage(vo.getPage(), vo.getRows());
        // 查询当前用户发表的所有文章
        List<Article> articles = articleService.findByUid(id);
        List<Long> aids = new ArrayList<>();
        for (Article article : articles) {
            aids.add(article.getId());
        }
        ReviewExample reviewExample = new ReviewExample();
        ReviewExample.Criteria criteria = reviewExample.createCriteria();
        if (vo.getKeywords() != null) {
            criteria.andContentLike("%" + vo.getKeywords() + "%");
        }
        criteria.andStatusEqualTo(false);
        criteria.andArticleIdIn(aids);
        criteria.andParentIdIsNull();
        List<Review> reviews = reviewDao.selectByExample(reviewExample);
        PageInfo<Review> reviewPageInfo = new PageInfo<>(reviews);
        return new PageResult<Review>(reviewPageInfo.getTotal(), reviews);
    }

    @Override
    public void fillArticle(List<Review> rows) {
        for (Review row : rows) {
            fillArticle(row);
        }
    }

    @Override
    public void fillArticle(Review row) {
        Long articleId = row.getArticleId();
        Article article = articleService.findById(articleId);
        row.setArticle(article);
    }

    @Override
    public void editStatus(Long id, boolean flag) {
        Review review = reviewDao.selectByPrimaryKey(id);
        review.setStatus(flag);
        reviewDao.updateByPrimaryKeySelective(review);
    }

    @Override
    public void delete(Long id) {
        reviewDao.deleteByPrimaryKey(id);
    }

    @Override
    public void fillChildren(List<Review> reviews) {
        for (Review review : reviews) {
            fillChildren(review);
        }
    }

    @Override
    public void fillChildren(Review review) {
        List<Review> reviews = null;
        if (review != null) {
            Long id = review.getId();
            // 一级子评论
            reviews = findByParentId(id);
            review.setChildren(reviews);
            fillChildren(reviews);
        }
    }

    @Override
    public void fillParent(List<Review> reviews) {
        if (reviews != null && reviews.size() > 0) {
            for (Review review : reviews) {
                fillParent(review);
            }
        }
    }

    @Override
    public void fillParent(Review review) {
        // 填充子评论的父信息
        Long parentId = review.getParentId();
        if (parentId != null) {
            Review parent = findById(parentId);
            review.setParent(parent);
            if (review.getChildren() != null) {
                fillParent(review.getChildren());
            }
        }
    }

    @Override
    public List<Review> findByParentId(Long id) {
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.setOrderByClause("remark_date desc");
        ReviewExample.Criteria criteria = reviewExample.createCriteria();
        criteria.andParentIdEqualTo(id);
        return reviewDao.selectByExample(reviewExample);
    }

    @Override
    public Review findById(Long id) {
        return reviewDao.selectByPrimaryKey(id);
    }

    @Override
    public void operateDate(List<Review> reviews) {
        if (reviews != null && reviews.size() > 0) {
            for (Review review : reviews) {
                Field.treeList.add(review);
                if (review.getChildren() != null && review.getChildren().size() > 0) {
                    List<Review> children = review.getChildren();
                    Field.treeList.addAll(review.getChildren());
                    for (Review child : children) {
                        operateDate(child.getChildren());
                    }
                }
            }
            // 设置数据
            reviews.clear();
            reviews.addAll(Field.treeList);
            Field.treeList.clear();
        }
    }
}
