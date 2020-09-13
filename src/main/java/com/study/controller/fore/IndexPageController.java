package com.study.controller.fore;

import com.github.pagehelper.PageInfo;
import com.study.pojo.announcement.Announcement;
import com.study.pojo.article.Article;
import com.study.pojo.banner.Banner;
import com.study.pojo.category.Category;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.keywords.Keywords;
import com.study.pojo.link.Link;
import com.study.pojo.user.User;
import com.study.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/index")
@Controller
public class IndexPageController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private KeywordsService keywordsService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private ReviewService reviewService;

    @RequestMapping("/page")
    public String findAll(QueryVo vo, Model model, Long categoryId) {
        // 获取所有分类数据
        List<Category> categories = categoryService.findParent();
        // 添加子数据
        categoryService.fillChildren(categories);
        // 添加分类附加数据
        categoryService.addAttachDate(categories);
        // 获取当前分类
        Category category = categoryService.findById(categoryId);
        if (category == null) {
            category = categories.get(0);
        }
        PageInfo<Article> articlePageInfo = articleService.findByCategoryId(vo, categoryId);
        List<Article> articles = articlePageInfo.getList();
        // 处理文章数据
        articleService.convertContent2Text(articles);
        // 获取banner
        List<Banner> banners =  bannerService.findAll();
        // 获取第一条公告
        Announcement announcement = announcementService.findFirst();
        // 获取关键字
        List<Keywords> keywords = keywordsService.findAll();
        // 连接
        List<Link> links = linkService.findAll();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        // 查询未审核的评论
        Long reviewCount = null;
        if (user != null) {
            reviewCount = reviewService.findCountReview(user.getId(), false);
        }

        model.addAttribute("categories", categories);
        model.addAttribute("curCategory", category);
        model.addAttribute("articles", articles);
        model.addAttribute("banners", banners);
        model.addAttribute("announcement", announcement);
        model.addAttribute("keywords", keywords);
        model.addAttribute("links", links);
        model.addAttribute("articlePageInfo", articlePageInfo);
        model.addAttribute("search", vo.getKeywords());
        model.addAttribute("reviewCount", reviewCount);
        return "fore/index";
    }
}
