package com.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.dao.menu.MenuDao;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.menu.Menu;
import com.study.pojo.menu.MenuExample;
import com.study.pojo.permission.Permission;
import com.study.pojo.user.User;
import com.study.service.MenuService;
import com.study.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private PermissionService permissionService;

    @Override
    public PageResult<Menu> findPage(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getPage(), queryVo.getRows());
        MenuExample menuExample = new MenuExample();
        MenuExample.Criteria criteria = menuExample.createCriteria();
        if (queryVo.getKeywords() != null) {
            criteria.andTextLike("%" + queryVo.getKeywords() + "%");
        } else {
            criteria.andParentIdIsNull();
        }
        List<Menu> menus = menuDao.selectByExample(menuExample);
        PageInfo<Menu> pageInfo = new PageInfo<>(menus);
        return new PageResult<>(pageInfo.getTotal(), menus);
    }

    @Override
    public void fillParent(List<Menu> menus) {
        for (Menu menu : menus) {
            fillParent(menu);
        }
    }

    @Override
    public void fillParent(Menu menu) {
        Long parentId = menu.getParentId();
        Menu parent = menuDao.selectByPrimaryKey(parentId);
        menu.setParent(parent);
    }

    @Override
    public void save(Menu menu) {
        menuDao.insertSelective(menu);
    }

    @Override
    public void edit(Menu menu) {
        menuDao.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void delete(Long id) {
        menuDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Menu> findAll() {
        return menuDao.selectByExample(null);
    }

    @Override
    public void fillPermission(List<Menu> menus) {
        for (Menu menu : menus) {
            fillPermission(menu);
        }
    }

    @Override
    public void fillPermission(Menu menu) {
        Long permissionId = menu.getPermissionId();
        if (permissionId != null) {
            Permission permission = permissionService.findById(permissionId);
            menu.setPermission(permission);
            List<Menu> children = menu.getChildren();
            if (children != null && children.size() > 0) {
                fillPermission(children);
            }
        }
    }

    @Override
    public List<Menu> findParent() {
        MenuExample menuExample = new MenuExample();
        MenuExample.Criteria criteria = menuExample.createCriteria();
        criteria.andParentIdIsNull();
        return menuDao.selectByExample(menuExample);
    }

    @Override
    public void fillChildren(List<Menu> menus) {
        for (Menu menu : menus) {
            fillChildren(menu);
        }
    }

    @Override
    public void fillChildren(Menu menu) {
        Long id = menu.getId();
        List<Menu> menus = findByParentId(id);
        if (menus != null && menus.size() > 0) {
            menu.setChildren(menus);
            fillChildren(menus);
        }
    }

    private List<Menu> findByParentId(Long id) {
        MenuExample menuExample = new MenuExample();
        MenuExample.Criteria criteria = menuExample.createCriteria();
        criteria.andParentIdEqualTo(id);
        return menuDao.selectByExample(menuExample);
    }

    @Override
    public List<Menu> checkUserPermission(List<Menu> menus) {
        // 获取主体对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        // 处理数据
        Iterator<Menu> iterator = menus.iterator();
        while (iterator.hasNext()) {
            Menu menu = iterator.next();
            Permission permission = menu.getPermission();
            if (permission != null) {
                if (!subject.isPermitted(permission.getResource())) {
                    iterator.remove();
                    continue;
                }
            }
            // 处理子菜单
            List<Menu> children = menu.getChildren();
            if (children != null && children.size() > 0) {
                children = checkUserPermission(children);
            }
            menu.setChildren(children);
        }
        return menus;
    }

    @Override
    public void fillRoles(List<Menu> menus) {
        for (Menu menu : menus) {
            fillRoles(menu);
        }
    }

    @Override
    public void fillRoles(Menu menu) {
        Permission permission = menu.getPermission();
        permissionService.fillRoles(permission);
        List<Menu> children = menu.getChildren();
        if (children != null && children.size() > 0) {
            fillRoles(children);
        }
    }
}
