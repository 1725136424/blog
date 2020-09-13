package com.study.service;

import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.permission.Permission;
import com.study.pojo.role.Role;

import java.util.List;

public interface RoleService {

    PageResult<Role> findPage(QueryVo vo);

    void save(Role role);

    void saveRolePermissionRel(List<Role> roles, List<Permission> permissions);

    void saveRolePermissionRel(Role role, List<Permission> permissions);

    void saveRolePermissionRel(List<Role> roles, Permission permission);

    void saveRolePermissionRel(Role role, Permission permission);

    void saveRolePermissionRel(Long rid, Long pid);

    void edit(Role role);

    void deleteRolePermissionRel(Role role);

    void deleteRolePermissionRel(Long id);

    Role findById(Long id);

    void delete(Long id);

    List<Role> list();

    List<Long> findRidsByUid(Long id);

    List<Role> findByRids(List<Long> rids);

    List<Long> findRidsByPid(Long id);

    void fillPermissions(List<Role> resRoles);

    void fillPermissions(Role resRole);
}
