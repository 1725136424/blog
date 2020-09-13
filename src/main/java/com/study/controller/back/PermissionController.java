package com.study.controller.back;

import com.study.pojo.entity.AjaxResult;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.permission.Permission;
import com.study.pojo.role.Role;
import com.study.service.PermissionService;
import com.study.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/page")
    @ResponseBody
    public PageResult<Permission> page(QueryVo vo) {
        // 查询顶级分权限数据
        PageResult<Permission> pageResult = permissionService.findPage(vo);
        // 填充子菜单
        permissionService.fillChildren(pageResult.getRows());
        return pageResult;
    }

    @RequestMapping("/findChildren")
    @ResponseBody()
    public List<Permission> findChildren(Long parentId) {
        return permissionService.findByParentId(parentId);
    }

    @RequestMapping("/findParent")
    @ResponseBody()
    public List<Permission> findParent(Long parentId) {
        List<Permission> permissions = permissionService.findParent();
        // 填充子数据
        permissionService.fillChildren(permissions);
        return permissions;
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Permission> list() {
        return permissionService.list();
    }

    @RequestMapping("/findByRid")
    @ResponseBody
    public List<Permission> findByRid(Long rid) {
        // 获取角色
        Role role = roleService.findById(rid);
        // 获取权限id
        List<Long> pids = permissionService.findPidByRid(role.getId());
        // 查询所有的权限
        return permissionService.findByIds(pids);
    }

    @RequestMapping("/save")
    @ResponseBody
    public AjaxResult save(Permission permission) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            permissionService.save(permission);
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
    public AjaxResult edit(Permission permission) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            permissionService.edit(permission);
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
            permissionService.delete(id);
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
