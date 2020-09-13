package com.study.controller.fore;

import com.github.pagehelper.PageInfo;
import com.study.pojo.article.Article;
import com.study.pojo.category.Category;
import com.study.pojo.entity.AjaxResult;
import com.study.pojo.review.Review;
import com.study.pojo.user.User;
import com.study.service.ArticleService;
import com.study.service.CategoryService;
import com.study.service.ReviewService;
import com.study.service.UserService;
import com.study.util.Field;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@RequestMapping("/articleDesc")
@Controller
public class ArticlePageController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;


    @RequestMapping("page")
    public String page(Model model, Long articleId) {
        // 分类数据
        // 获取所有分类数据
        List<Category> categories = categoryService.findParent();
        // 添加子数据
        categoryService.fillChildren(categories);
        // 添加分类附加数据
        categoryService.addAttachDate(categories);
        // 获取当前文章
        Article article = articleService.findById(articleId);
        // 获取当前分类
        Category category = categoryService.findById(article.getCategoryId());
        // 获取文章用户对象
        User user = userService.findById(article.getUserId());
        // 填充非数据库字段
        userService.fillPublishCount(user);
        userService.fillFansCount(user);
        userService.fillReviewCount(user);
        // 获取当前文章的评论信息
        PageInfo<Review> reviewPageInfo = reviewService.findReviewByArticleId(articleId);
        List<Review> reviews = reviewPageInfo.getList();
        // 获取当前评论信息下的用户信息
        reviewService.fillUser(reviews);
        // 填充当前评论信息下的所有(一级二级...)子评论信息
        reviewService.fillChildren(reviews);
        for (Review review : reviews) {
            // 填充子评论下的父信息
            reviewService.fillParent(review.getChildren());
            // 操作数据 把所有数据放入一级库中
            reviewService.operateDate(review.getChildren());
            // 填充子评论下的用户信息
            reviewService.fillUser(review.getChildren());
            // 填充子评论下父信息下的用户数据
            List<Review> children = review.getChildren();
            for (Review child : children) {
                reviewService.fillUser(child.getParent());
            }
        }
        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        User curUser = (User) subject.getPrincipal();
        Boolean isStart = false;
        if (curUser != null) {
            // 获取当前的关注列表
            isStart = userService.findStartIByFansId(user.getId(), curUser.getId());
        }
        Long reviewCount = null;
        if (curUser != null) {
            reviewCount = reviewService.findCountReview(curUser.getId(), false);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("curCategory", category);
        model.addAttribute("article", article);
        model.addAttribute("user", user);
        model.addAttribute("isStart", isStart);
        model.addAttribute("reviews", reviews);
        model.addAttribute("total", reviewPageInfo.getTotal());
        model.addAttribute("reviewCount", reviewCount);
        return "fore/article";
    }

    // 关注
    @RequestMapping("followUser")
    @ResponseBody
    public AjaxResult followUser(Long uid) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            userService.saveStartFansRel(uid, user.getId());
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("操作失败");
        }

        return ajaxResult;
    }

    // 取消关注
    @RequestMapping("cancelFollow")
    @ResponseBody
    public AjaxResult cancelFollow(Long uid) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            userService.deleteStartFansRel(uid, user.getId());
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("操作失败");
        }

        return ajaxResult;
    }

    // 获取剩余评论
    @RequestMapping("/remainReview")
    @ResponseBody
    public List<Review> remainReview(Long articleId) {
        try {
            List<Review> reviews = reviewService.findRemainReview(articleId);
            // 获取当前评论信息下的用户信息
            reviewService.fillUser(reviews);
            // 填充当前评论信息下的所有(一级二级...)子评论信息
            reviewService.fillChildren(reviews);
            for (Review review : reviews) {
                // 填充子评论下的父信息
                reviewService.fillParent(review.getChildren());
                // 操作数据 把所有数据放入一级库中
                reviewService.operateDate(review.getChildren());
                // 填充子评论下的用户信息
                reviewService.fillUser(review.getChildren());
                // 填充子评论下父信息下的用户数据
                List<Review> children = review.getChildren();
                for (Review child : children) {
                    reviewService.fillUser(child.getParent());
                }
            }
            return reviews;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @RequestMapping("/commitReview")
    @ResponseBody
    public AjaxResult commitReview(Review review) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            review.setStatus(false);
            review.setRemarkDate(new Date());
            review.setUserId(user.getId());
            reviewService.save(review);
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("保存失败");
        }

        return ajaxResult;
    }

    @RequestMapping("/answerReview")
    @ResponseBody
    public AjaxResult answerReview(Review review) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            review.setStatus(true);
            review.setRemarkDate(new Date());
            review.setUserId(user.getId());
            reviewService.save(review);
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("保存失败");
        }

        return ajaxResult;
    }
}
