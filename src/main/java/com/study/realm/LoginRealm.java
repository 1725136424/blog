package com.study.realm;

import com.study.pojo.permission.Permission;
import com.study.pojo.role.Role;
import com.study.pojo.user.User;
import com.study.service.RoleService;
import com.study.service.UserService;
import com.study.util.Field;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = userService.findByUsername(username);
        if (user == null) {
            // 没有当前用户
            return null;
        }
        // 认证
        return new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(Field.salt),
                this.getName());
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        // 角色集合
        List<String> roles = new ArrayList<>();
        // 权限集合
        List<String> permissions = new ArrayList<>();
        // 根据用户查询对应的的角色
        userService.fillRoles(user);
        // 角色填充对应的权限
        List<Role> resRoles = user.getRoles();
        roleService.fillPermissions(resRoles);
        // 转换对应的数据
        for (Role resRole : resRoles) {
            roles.add(resRole.getNumber());
            List<Permission> resPermissions = resRole.getPermissions();
            for (Permission resPermission : resPermissions) {
                permissions.add(resPermission.getResource());
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }


}
