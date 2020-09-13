package com.study.controller.fore;

import com.github.pagehelper.PageInfo;
import com.study.pojo.article.Article;
import com.study.pojo.category.Category;
import com.study.pojo.entity.AjaxResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.user.User;
import com.study.service.ArticleService;
import com.study.service.CategoryService;
import com.study.service.ReviewService;
import com.study.service.UserService;
import com.study.util.VerifyCodeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping("/home")
@Controller
public class HomePageController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @RequestMapping("/page")
    public String page(QueryVo vo, Model model, Long userId, Long categoryId) {
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
        // 获取当前用户下当前分类的的所有文章
        PageInfo<Article> articlePageInfo = articleService.findByUidAndCid(vo, userId, categoryId);
        List<Article> articles = articlePageInfo.getList();
        articleService.convertContent2Text(articles);
        // 获取文章用户对象
        User user = userService.findById(userId);
        // 填充非数据库字段
        userService.fillPublishCount(user);
        userService.fillFansCount(user);
        userService.fillFollowCount(user);
        userService.fillReviewCount(user);
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
        model.addAttribute("articles", articles);
        model.addAttribute("user", user);
        model.addAttribute("isStart", isStart);
        model.addAttribute("articlePageInfo", articlePageInfo);
        model.addAttribute("reviewCount", reviewCount);
        return "fore/home";
    }

    @RequestMapping("findFans")
    @ResponseBody
    public List<User> findFans(Long userId) {
        // 获取当前用户的粉丝列表
        return userService.findFansByUid(userId);
    }

    @RequestMapping("findStart")
    @ResponseBody
    public List<User> findStart(Long userId) {
        // 获取当前用户的粉丝列表
        return userService.findStartByUid(userId);
    }


    @RequestMapping("findStatus")
    @ResponseBody
    public AjaxResult findStatus(Long userId) {
        // 获取当前用户是否为登录状态
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        AjaxResult ajaxResult = new AjaxResult();
        if (user != null) {
            //
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("已登录");
        } else {
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("未登录");
        }
        return ajaxResult;
    }

    @RequestMapping("/createCode")
    // 生成验证码
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入会话session
        request.getSession().setAttribute("code", verifyCode.toLowerCase());

        //生成图片
        int width = 100;//宽
        int height = 40;//高
        VerifyCodeUtils.outputImage(width, height, response.getOutputStream(), verifyCode);
    }

    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult login(User user, String code,  @SessionAttribute("code") String sessionCode) {
        AjaxResult ajaxResult = new AjaxResult();
        if (sessionCode.equalsIgnoreCase(code)) {
            // 进行shiro验证
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            try {
                subject.login(token);
                ajaxResult.setMessage("登录成功");
                ajaxResult.setIsSuccess(true);
            } catch (UnknownAccountException e) {
                e.printStackTrace();
                ajaxResult.setMessage("账号不存在");
                ajaxResult.setIsSuccess(false);
                ajaxResult.setErrorClassification(2);
            } catch (IncorrectCredentialsException e) {
                e.printStackTrace();
                ajaxResult.setMessage("密码错误");
                ajaxResult.setIsSuccess(false);
                ajaxResult.setErrorClassification(3);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setMessage("未知异常");
                ajaxResult.setIsSuccess(false);
                ajaxResult.setErrorClassification(4);
            }
        } else {
            // 验证失败
            ajaxResult.setMessage("验证码错误");
            ajaxResult.setIsSuccess(false);
            ajaxResult.setErrorClassification(1);
        }
        return ajaxResult;
    }
}
