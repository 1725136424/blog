package com.study.service;

import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.link.Link;

import java.util.List;

public interface LinkService {

    PageResult<Link> findPage(QueryVo queryVo);

    void save(Link link);

    void edit(Link link);

    void delete(Long id);

    List<Link> findAll();
}
