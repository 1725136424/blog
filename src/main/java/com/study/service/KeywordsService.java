package com.study.service;

import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.keywords.Keywords;

import java.util.List;

public interface KeywordsService {
    PageResult<Keywords> findPage(QueryVo queryVo);

    void save(Keywords keywords);

    void edit(Keywords keywords);

    void delete(Long id);

    List<Keywords> findAll();
}
