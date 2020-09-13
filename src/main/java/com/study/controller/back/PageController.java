package com.study.controller.back;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/login")
    public String login() {
        return "redirect:/index/page?categoryId=-1";
    }

    @RequestMapping("/back")
    public String back() {
        return "back/index";
    }

    @RequestMapping("/user")
    @RequiresPermissions("user:index")
    public String user() {
        return "back/user";
    }

    @RequestMapping("/permission")
    @RequiresPermissions("permission:index")
    public String permission() {
        return "back/permission";
    }

    @RequestMapping("/role")
    @RequiresPermissions("role:index")
    public String role() {
        return "back/role";
    }

    @RequestMapping("/menu")
    @RequiresPermissions("menu:index")
    public String menu() {
        return "back/menu";
    }

    @RequestMapping("/article")
    @RequiresPermissions("article:index")
    public String article() {
        return "back/article";
    }

    @RequestMapping("/category")
    @RequiresPermissions("category:index")
    public String category() {
        return "back/category";
    }

    @RequestMapping("/review")
    @RequiresPermissions("review:index")
    public String review() {
        return "back/review";
    }

    @RequestMapping("/banner")
    @RequiresPermissions("banner:index")
    public String banner() {
        return "back/banner";
    }

    @RequestMapping("/announcement")
    @RequiresPermissions("announcement:index")
    public String announcement() {
        return "back/announcement";
    }

    @RequestMapping("/keywords")
    @RequiresPermissions("keywords:index")
    public String keywords() {
        return "back/keywords";
    }

    @RequestMapping("/link")
    @RequiresPermissions("link:index")
    public String link() {
        return "back/link";
    }

}
