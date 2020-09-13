package com.study.controller.fore;

import com.study.pojo.category.Category;
import com.study.pojo.entity.AjaxResult;
import com.study.pojo.user.User;
import com.study.service.CategoryService;
import com.study.service.ReviewService;
import com.study.service.UserService;
import com.study.util.Field;
import com.study.util.MailUtils;
import com.study.util.ServletUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/loginAndRegister")
@SessionAttributes("editUser")
public class LoginAndRegisterPageController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private ReviewService reviewService;

    @RequestMapping("/page")
    public String page(Model model, Integer method) {
        // 获取所有分类数据
        List<Category> categories = categoryService.findParent();
        // 添加子数据
        categoryService.fillChildren(categories);
        // 添加分类附加数据
        categoryService.addAttachDate(categories);
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Long reviewCount = null;
        if (user != null) {
             reviewCount = reviewService.findCountReview(user.getId(), false);
        }
        // 查询未审核的评论
        model.addAttribute("categories", categories);
        model.addAttribute("method", method);
        model.addAttribute("reviewCount", reviewCount);
        return "fore/register";
    }

    @RequestMapping("/checkUsername")
    @ResponseBody
    public Map<String, Boolean> checkUsername(String username) {
        User user = userService.findByUsername(username);
        HashMap<String, Boolean> resMap = new HashMap<>();
        if (user != null) {
            resMap.put("valid", false);
        } else {
            resMap.put("valid", true);
        }
        return resMap;
    }

    @RequestMapping("/register")
    @ResponseBody
    public AjaxResult register(MultipartFile image,
                               User user,
                               String code,
                               @SessionAttribute("code") String resCode,
                               HttpServletRequest request) throws IOException {
        AjaxResult ajaxResult = new AjaxResult();
        if (image.getSize() > 0) {
            if (code.equalsIgnoreCase(resCode)) {
                // 上传图片
                String imagePath = ServletUtils.uploadImage(image, request, Field.userRelative);
                user.setPicture(imagePath);
                user.setRegisterDate(new Date());
                // 密码加密
                Md5Hash md5Hash = new Md5Hash(user.getPassword(), Field.salt, Field.hashIterations);
                user.setPassword(md5Hash.toString());
                // 保存用户
                userService.save(user);
                // 添加普通用户角色
                userService.assignmentNormalRole(user);
                ajaxResult.setMessage("注册成功");
                ajaxResult.setIsSuccess(true);
            } else {
                ajaxResult.setMessage("验证码错误");
                ajaxResult.setIsSuccess(false);
                ajaxResult.setErrorClassification(1);
            }
        } else {
            ajaxResult.setMessage("请上传图片");
            ajaxResult.setIsSuccess(false);
            ajaxResult.setErrorClassification(4);
        }
        return ajaxResult;
    }

    // 发送邮件控制器
    @RequestMapping("/sendEmail")
    @ResponseBody
    public AjaxResult sendEmail(String username,
                                String code,
                                @SessionAttribute("code") String resCode,
                                HttpServletRequest request) throws MessagingException {
        AjaxResult ajaxResult = new AjaxResult();
        // 验证码判断
        if (code.equalsIgnoreCase(resCode)) {
            // 查找当前用户
            User user = userService.findByUsername(username);
            if (user != null) {
                // 唯一key
                String secretKey = UUID.randomUUID().toString();
                // 生成过期时间
                Date outDate = new Date(System.currentTimeMillis() + 15 * 30 * 1000);
                // 忽略秒
                long time = outDate.getTime() / 1000 * 1000;
                outDate = new Date(time);
                // 设置用户唯一性
                user.setValidateCode(secretKey);
                user.setOutDate(outDate);
                userService.update(user);
                // 生成唯一key
                String key = username + "$" + time + "$" + secretKey;
                // 数字签名
                String digitalSignature = new Md5Hash(key, Field.salt, Field.hashIterations).toString();
                String absolutePath = ServletUtils.getAbsolutePath(request);
                String checkUrl = absolutePath + "/loginAndRegister/checkLegal?id=" + digitalSignature + "&username=" + user.getUsername();
                String title = "豪大大重置密码";
                String text = "请勿回复本邮件.点击下面的链接,重设密码<br/><a href=" + checkUrl + " target='_BLANK'>点击我重新设置密码</a>" +
                        "<br/>tips:本邮件超过15分钟,链接将会失效，需要重新申请'找回密码'";
                Thread t = new Thread(() -> {
                    try {
                        mailUtils.sendMail(user.getUsername(), title, text);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                });
                t.start();
                ajaxResult.setIsSuccess(true);
                ajaxResult.setMessage("发送成功");
            } else {
                ajaxResult.setIsSuccess(false);
                ajaxResult.setMessage("账号不存在");
                ajaxResult.setErrorClassification(2);
            }
        } else {
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("验证码错误");
            ajaxResult.setErrorClassification(1);
        }
        return ajaxResult;
    }

    @RequestMapping("/checkLegal")
    public String checkLegal(String id, String username, Model model) {
        if ("".equals(id) || "".equalsIgnoreCase(username)) {
            model.addAttribute("message", "链接不完整");
            return "fore/checkIllegal";
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("message", "无法找到该用户");
            return "fore/checkIllegal";
        }

        long outDate = user.getOutDate().getTime();
        if (outDate <= System.currentTimeMillis()) {
            model.addAttribute("message", "链接已经过期");
            return "fore/checkIllegal";
        }

        // 验证唯一性
        String validateCode = user.getValidateCode();
        String digitalSignature = user.getUsername() + "$" + outDate + "$" + validateCode;
        String key = new Md5Hash(digitalSignature, Field.salt, Field.hashIterations).toString();
        if (!key.equals(id)) {
            model.addAttribute("message", "链接不正确,是否已经过期了?重新申请吧");
            return "fore/checkIllegal";
        }

        // 验证成功 抱歉当前用户至session中
        model.addAttribute("editUser", user);
        return "fore/editPassword";
    }

    @RequestMapping("/editPassword")
    @ResponseBody
    public AjaxResult editPassword(String password,
                                   @SessionAttribute("editUser") User user,
                                   SimpleSessionStatus status) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            String encodingPWD = new Md5Hash(password, Field.salt, Field.hashIterations).toString();
            // 保证修改密码链接的一次性
            user.setOutDate(new Date());
            user.setValidateCode(UUID.randomUUID().toString());
            user.setPassword(encodingPWD);
            userService.update(user);
            ajaxResult.setMessage("修改成功");
            ajaxResult.setIsSuccess(true);
            // 销毁当前用户
            status.isComplete();
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMessage("修改失败");
            ajaxResult.setIsSuccess(false);
        }
        return ajaxResult;
    }

}
