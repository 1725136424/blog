package com.study.controller.back;

import com.study.pojo.article.Article;
import com.study.pojo.banner.Banner;
import com.study.pojo.entity.AjaxResult;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.service.BannerService;
import com.study.util.Field;
import com.study.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping("/page")
    @ResponseBody
    public PageResult<Banner> page(QueryVo queryVo) {
        return bannerService.findPage(queryVo);
    }

    @RequestMapping("/save")
    @ResponseBody
    public AjaxResult save( MultipartFile image,
                            Banner banner,
                           HttpServletRequest request) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            String imagePath = ServletUtils.uploadImage(image, request, Field.bannerRelative);
            banner.setPicture(imagePath);
            bannerService.save(banner);
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
                           Banner banner, HttpServletRequest request) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            if (image.getSize() > 0) {
                String httpImage = ServletUtils.uploadImage(image, request, Field.bannerRelative);
                banner.setPicture(httpImage);
            }
            bannerService.edit(banner);
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
            bannerService.delete(id);
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("删除成功");
        } catch (Exception e) {
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("删除失败");
        }
        return ajaxResult;
    }
}
