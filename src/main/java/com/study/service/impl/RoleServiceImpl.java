package com.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.dao.role.RoleDao;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.permission.Permission;
import com.study.pojo.role.Role;
import com.study.pojo.role.RoleExample;
import com.study.service.PermissionService;
import com.study.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionService permissionService;

    @Override
    public PageResult<Role> findPage(QueryVo vo) {
        PageHelper.startPage(vo.getPage(), vo.getRows());
        RoleExample roleExample = null;
        if (vo.getKeywords() != null) {
            roleExample = new RoleExample();
            RoleExample.Criteria criteria = roleExample.createCriteria();
            criteria.andNameLike("%" + vo.getKeywords() + "%");
        }
        List<Role> roles = roleDao.selectByExample(roleExample);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        return new PageResult<>(pageInfo.getTotal(), roles);
    }

    @Override
    public void save(Role role) {
        roleDao.insertSelective(role);
    }

    @Override
    public void saveRolePermissionRel(List<Role> roles, List<Permission> permissions) {
        for (Role role : roles) {
            saveRolePermissionRel(role, permissions);
        }
    }

    @Override
    public void saveRolePermissionRel(Role role, List<Permission> permissions) {
        for (Permission permission : permissions) {
            saveRolePermissionRel(role, permission);
        }
    }

    @Override
    public void saveRolePermissionRel(List<Role> roles, Permission permission) {
        for (Role role : roles) {
            saveRolePermissionRel(role, permission);
        }
    }

    @Override
    public void saveRolePermissionRel(Role role, Permission permission) {
        saveRolePermissionRel(role.getId(), permission.getId());
    }

    @Override
    public void saveRolePermissionRel(Long rid, Long pid) {
        roleDao.saveRolePermissionRel(rid, pid);
    }

    @Override
    public void edit(Role role) {
        roleDao.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteRolePermissionRel(Role role) {
        deleteRolePermissionRel(role.getId());
    }

    @Override
    public void deleteRolePermissionRel(Long id) {
        roleDao.deleteRolePermissionRel(id);
    }

    @Override
    public Role findById(Long id) {
        return roleDao.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        roleDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Role> list() {
        return roleDao.selectByExample(null);
    }

    @Override
    public List<Long> findRidsByUid(Long id) {
        return roleDao.findRidsByUid(id);
    }

    @Override
    public List<Role> findByRids(List<Long> rids) {
        if (rids != null && rids.size() > 0) {
            RoleExample roleExample = new RoleExample();
            RoleExample.Criteria criteria = roleExample.createCriteria();
            criteria.andIdIn(rids);
            return roleDao.selectByExample(roleExample);
        } else {
            return null;
        }
    }

    @Override
    public List<Long> findRidsByPid(Long id) {
        if (id != null) {
            return roleDao.findRidsByPid(id);
        } else {
            return null;
        }
    }

    @Override
    public void fillPermissions(List<Role> resRoles) {
        for (Role resRole : resRoles) {
            fillPermissions(resRole);
        }
    }

    @Override
    public void fillPermissions(Role resRole) {
        // 填充权限数据
        Long id = resRole.getId();
        List<Long> pids = permissionService.findPidByRid(id);
        List<Permission> permissions = permissionService.findByIds(pids);
        resRole.setPermissions(permissions);
    }
}
