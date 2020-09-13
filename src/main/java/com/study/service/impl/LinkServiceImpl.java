package com.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.dao.link.LinkDao;
import com.study.pojo.banner.Banner;
import com.study.pojo.banner.BannerExample;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.pojo.link.Link;
import com.study.pojo.link.LinkExample;
import com.study.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkDao linkDao;
    @Override
    public PageResult<Link> findPage(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getPage(), queryVo.getRows());
        LinkExample linkExample =  new LinkExample();
        linkExample.setOrderByClause("sort desc");
        if (queryVo.getKeywords() != null) {
            LinkExample.Criteria criteria = linkExample.createCriteria();
            criteria.andNameLike("%" + queryVo.getKeywords() + "%");
        }
        List<Link> links = linkDao.selectByExample(linkExample);
        PageInfo<Link> pageInfo = new PageInfo<>(links);
        return new PageResult<>(pageInfo.getTotal(), links);
    }

    @Override
    public void save(Link link) {
        linkDao.insertSelective(link);
    }

    @Override
    public void edit(Link link) {
        linkDao.updateByPrimaryKeySelective(link);
    }

    @Override
    public void delete(Long id) {
        linkDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Link> findAll() {
        LinkExample linkExample = new LinkExample();
        linkExample.setOrderByClause("sort desc");
        return  linkDao.selectByExample(linkExample);
    }
}
