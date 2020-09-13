package com.study.controller.back;

import com.study.pojo.category.Category;
import com.study.pojo.entity.AjaxResult;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.permission.Permission;
import com.study.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 分页获取父分类以及其子数据
    @RequestMapping("/page")
    @ResponseBody
    public PageResult<Category> page(QueryVo vo) {
        // 查询顶级分权限数据
        PageResult<Category> pageResult = categoryService.findPage(vo);
        // 填充子菜单
        categoryService.fillChildren(pageResult.getRows());
        return pageResult;
    }

    // 获取父及其子数据
    @RequestMapping("/findParent")
    @ResponseBody
    public List<Category> findParent() {
        // 查询顶级分权限数据
        List<Category> categories = categoryService.findParent();
        // 填充子菜单
        categoryService.fillChildren(categories);
        return categories;
    }

    @RequestMapping("/save")
    @ResponseBody
    public AjaxResult save(Category category) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            categoryService.save(category);
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
    public AjaxResult edit(Category category) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            categoryService.edit(category);
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
            categoryService.delete(id);
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("删除失败");
        }
        return ajaxResult;
    }
}
