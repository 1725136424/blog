package com.study.controller.back;

import com.study.pojo.entity.AjaxResult;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.permission.Permission;
import com.study.pojo.role.Role;
import com.study.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/page")
    @ResponseBody
    public PageResult<Role> page(QueryVo vo) {
        return roleService.findPage(vo);
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Role> list() {
        return roleService.list();
    }

    @RequestMapping("/save")
    @ResponseBody
    public AjaxResult save(Role role) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            // 保存用户
            roleService.save(role);
            // 保存关联关系
            roleService.saveRolePermissionRel(role, role.getPermissions());
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
    public AjaxResult edit(Role role) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            // 保存用户
            roleService.edit(role);
            // 删除关联关系
            roleService.deleteRolePermissionRel(role);
            // 保存关联关系
            roleService.saveRolePermissionRel(role, role.getPermissions());
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
    public AjaxResult edit(Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            // 删除关联关系
            roleService.deleteRolePermissionRel(id);
            // 删除角色
            roleService.delete(id);
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
