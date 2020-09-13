package com.study.controller.back;

import com.study.pojo.entity.AjaxResult;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.role.Role;
import com.study.pojo.user.User;
import com.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/page")
    @ResponseBody
    public PageResult<User> page(QueryVo vo) {
        PageResult<User> pageResult = userService.findPage(vo);
        // 填充角色数据
        userService.fillRoles(pageResult.getRows());
        return pageResult;
    }

    // 授权
    @RequestMapping("/auth")
    @ResponseBody
    public AjaxResult auth(User user) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            // 删除
            userService.deleteUserRoleRel(user);
            // 授权角色
            userService.saveUserRoleRel(user, user.getRoles());
            ajaxResult.setIsSuccess(true);
            ajaxResult.setMessage("授权成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setIsSuccess(false);
            ajaxResult.setMessage("授权失败");
        }
        return ajaxResult;
    }
}
