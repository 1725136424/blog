package com.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.dao.keywords.KeywordsDao;
import com.study.pojo.banner.Banner;
import com.study.pojo.banner.BannerExample;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.keywords.Keywords;
import com.study.pojo.keywords.KeywordsExample;
import com.study.service.KeywordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordsServiceImpl implements KeywordsService {

    @Autowired
    private KeywordsDao keywordsDao;

    @Override
    public PageResult<Keywords> findPage(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getPage(), queryVo.getRows());
        KeywordsExample keywordsExample =  new KeywordsExample();
        keywordsExample.setOrderByClause("sort desc");
        if (queryVo.getKeywords() != null) {
            KeywordsExample.Criteria criteria = keywordsExample.createCriteria();
            criteria.andNameLike("%" + queryVo.getKeywords() + "%");
        }
        List<Keywords> keywords = keywordsDao.selectByExample(keywordsExample);
        PageInfo<Keywords> pageInfo = new PageInfo<>(keywords);
        return new PageResult<>(pageInfo.getTotal(), keywords);
    }

    @Override
    public void save(Keywords keywords) {
        keywordsDao.insertSelective(keywords);
    }

    @Override
    public void edit(Keywords keywords) {
        keywordsDao.updateByPrimaryKeySelective(keywords);
    }

    @Override
    public void delete(Long id) {
        keywordsDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Keywords> findAll() {
        KeywordsExample keywordsExample = new KeywordsExample();
        keywordsExample.setOrderByClause("sort desc");
        return keywordsDao.selectByExample(keywordsExample);
    }
}
