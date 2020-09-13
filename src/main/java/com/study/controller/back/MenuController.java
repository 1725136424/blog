package com.study.controller.back;

import com.study.pojo.entity.AjaxResult;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.menu.Menu;
import com.study.service.MenuService;
import com.study.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/page")
    @ResponseBody
    public PageResult<Menu> page(QueryVo queryVo) {
        PageResult<Menu> pageResult = menuService.findPage(queryVo);
        List<Menu> menus = pageResult.getRows();
        // 填充父菜单
        menuService.fillChildren(menus);
        // 填充权限
        menuService.fillPermission(menus);
        // 填充权限中的角色
        menuService.fillRoles(menus);
        return pageResult;
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Menu> list() {
        return menuService.findAll();
    }

    @RequestMapping("/findParent")
    @ResponseBody
    public List<Menu> findParent() {
        List<Menu> menus = menuService.findParent();
        // 填充子数据
        menuService.fillChildren(menus);
        // 填充菜单权限数据
        menuService.fillPermission(menus);
        // 根据用户权限返回不同的菜单数据
        menuService.checkUserPermission(menus);
        return menus;
    }

    @RequestMapping("/save")
    @ResponseBody
    public AjaxResult save(Menu menu) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            // 保存员工
            menuService.save(menu);
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
    public AjaxResult edit(Menu menu) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            // 编辑角色
            menuService.edit(menu);
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("编辑成功");
        } catch (Exception e) {
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("编辑失败");
        }
        return ajaxResult;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public AjaxResult delete(Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            // 删除菜单
            menuService.delete(id);
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
