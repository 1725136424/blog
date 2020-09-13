package com.study.controller.back;

import com.study.pojo.banner.Banner;
import com.study.pojo.entity.AjaxResult;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.keywords.Keywords;
import com.study.service.BannerService;
import com.study.service.KeywordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/keywords")
public class KeywordsController {

    @Autowired
    private KeywordsService keywordsService;

    @RequestMapping("/page")
    @ResponseBody
    public PageResult<Keywords> page(QueryVo queryVo) {
        return keywordsService.findPage(queryVo);
    }

    @RequestMapping("/save")
    @ResponseBody
    public AjaxResult save(Keywords keywords) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            keywordsService.save(keywords);
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
    public AjaxResult edit(Keywords keywords) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            keywordsService.edit(keywords);
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
            keywordsService.delete(id);
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("删除成功");
        } catch (Exception e) {
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("删除失败");
        }
        return ajaxResult;
    }
}
