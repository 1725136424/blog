package com.study.service;

import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.menu.Menu;

import java.util.List;

public interface MenuService {


    PageResult<Menu> findPage(QueryVo queryVo);

    void fillParent(List<Menu> menus);

    void fillParent(Menu menu);

    void save(Menu menu);

    void edit(Menu menu);

    void delete(Long id);

    List<Menu> findAll();

    void fillPermission(List<Menu> menus);

    void fillPermission(Menu menu);

    List<Menu> findParent();

    void fillChildren(List<Menu> menus);

    void fillChildren(Menu menu);

    List<Menu> checkUserPermission(List<Menu> menus);

    void fillRoles(List<Menu> menus);

    void fillRoles(Menu menu);
}
