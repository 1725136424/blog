package com.study.controller.back;

import com.study.pojo.article.Article;
import com.study.pojo.entity.AjaxResult;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.service.ArticleService;
import com.study.util.Field;
import com.study.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/page")
    @ResponseBody
    public PageResult<Article> page(QueryVo queryVo, String searchCategory) {
        // 获取所有文章集合
        PageResult<Article> pageResult = articleService.findPage(queryVo, searchCategory);
        // 填充文章下的分类数据
        articleService.fillCategories(pageResult.getRows());
        return pageResult;
    }

    @RequestMapping("/save")
    @ResponseBody
    public AjaxResult save(MultipartFile image,
                           Article article, HttpServletRequest request) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            String httpImage = ServletUtils.uploadImage(image, request, Field.articleCoverRelative);
            article.setPicture(httpImage);
            articleService.save(article);
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("保存失败");
        }
        return ajaxResult;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public AjaxResult edit(MultipartFile image,
                           Article article, HttpServletRequest request) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            if (image.getSize() > 0) {
                String httpImage = ServletUtils.uploadImage(image, request, Field.articleCoverRelative);
                article.setPicture(httpImage);
            }
            articleService.edit(article);
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("修改失败");
        }
        return ajaxResult;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public AjaxResult delete(Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            articleService.delete(id);
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("删除成功");
        } catch (Exception e) {
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("删除失败");
        }
        return ajaxResult;
    }
}
