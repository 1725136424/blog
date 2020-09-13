package com.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.dao.permission.PermissionDao;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.permission.Permission;
import com.study.pojo.permission.PermissionExample;
import com.study.pojo.role.Role;
import com.study.service.PermissionService;
import com.study.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RoleService roleService;

    @Override
    public PageResult<Permission> findPage(QueryVo vo) {
        PageHelper.startPage(vo.getPage(), vo.getRows());
        PermissionExample permissionExample =  new PermissionExample();
        PermissionExample.Criteria criteria = permissionExample.createCriteria();
        if (vo.getKeywords() != null) {
            criteria.andNameLike("%" + vo.getKeywords() + "%");
        } else {
            criteria.andParentIdIsNull();
        }
        List<Permission> permissions = permissionDao.selectByExample(permissionExample);
        PageInfo<Permission> pageInfo = new PageInfo<>(permissions);
        return new PageResult<>(pageInfo.getTotal(), permissions);
    }

    @Override
    public void save(Permission permission) {
        permissionDao.insertSelective(permission);
    }

    @Override
    public void edit(Permission permission) {
        permissionDao.updateByPrimaryKeySelective(permission);
    }

    @Override
    public void delete(Long id) {
        permissionDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Permission> list() {
        return permissionDao.selectByExample(null);
    }

    @Override
    public List<Long> findPidByRid(Long id) {
        return permissionDao.findPidByRid(id);
    }

    @Override
    public List<Permission> findByIds(List<Long> pids) {
        if (pids != null && pids.size() > 0) {
            PermissionExample permissionExample = new PermissionExample();
            PermissionExample.Criteria criteria = permissionExample.createCriteria();
            criteria.andIdIn(pids);
            return permissionDao.selectByExample(permissionExample);
        } else {
            return null;
        }
    }

    @Override
    public Permission findById(Long id) {
        return permissionDao.selectByPrimaryKey(id);
    }

    @Override
    public void fillRoles(Permission permission) {
        if (permission != null) {
            List<Long> rids = roleService.findRidsByPid(permission.getId());
            List<Role> roles = roleService.findByRids(rids);
            permission.setRoles(roles);
        }
    }

    @Override
    public void fillRoles(List<Permission> permissions) {
        for (Permission permission : permissions) {
            fillRoles(permission);
        }
    }

    @Override
    public List<Permission> findByParentId(Long parentId) {
        PermissionExample permissionExample = new PermissionExample();
        PermissionExample.Criteria criteria = permissionExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        return permissionDao.selectByExample(permissionExample);
    }

    @Override
    public List<Permission> findParent() {
        PermissionExample permissionExample = new PermissionExample();
        PermissionExample.Criteria criteria = permissionExample.createCriteria();
        criteria.andParentIdIsNull();
        return permissionDao.selectByExample(permissionExample);
    }

    @Override
    public void fillChildren(List<Permission> permissions) {
        for (Permission permission : permissions) {
            fillChildren(permission);
        }
    }

    @Override
    public void fillChildren(Permission permission) {
        Long id = permission.getId();
        List<Permission> permissions = findByParentId(id);
        if (permissions != null && permissions.size() > 0) {
            permission.setChildren(permissions);
            fillChildren(permissions);
        }
    }
}
