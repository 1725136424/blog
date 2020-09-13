package com.study.service;

import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.permission.Permission;

import java.util.List;

public interface PermissionService {

    PageResult<Permission> findPage(QueryVo vo);

    void save(Permission permission);

    void edit(Permission permission);

    void delete(Long id);

    List<Permission> list();

    List<Long> findPidByRid(Long id);

    List<Permission> findByIds(List<Long> pids);

    Permission findById(Long id);

    void fillRoles(Permission permission);

    void fillRoles(List<Permission> permissions);

    List<Permission> findByParentId(Long parentId);

    List<Permission> findParent();

    void fillChildren(List<Permission> permissions);

    void fillChildren(Permission permission);
}
