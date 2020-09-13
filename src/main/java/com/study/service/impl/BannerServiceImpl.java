package com.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.dao.banner.BannerDao;
import com.study.pojo.banner.Banner;
import com.study.pojo.banner.BannerExample;
import com.study.pojo.category.Category;
import com.study.pojo.category.CategoryExample;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.service.BannerService;
import com.study.util.Field;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDao bannerDao;

    @Override
    public PageResult<Banner> findPage(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getPage(), queryVo.getRows());
        BannerExample bannerExample =  new BannerExample();
        bannerExample.setOrderByClause("sort desc");
        if (queryVo.getKeywords() != null) {
            BannerExample.Criteria criteria = bannerExample.createCriteria();
            criteria.andNameLike("%" + queryVo.getKeywords() + "%");
        }
        List<Banner> banners = bannerDao.selectByExample(bannerExample);
        PageInfo<Banner> pageInfo = new PageInfo<>(banners);
        return new PageResult<>(pageInfo.getTotal(), banners);
    }

    @Override
    public void save(Banner banner) {
        bannerDao.insertSelective(banner);
    }

    @Override
    public void edit(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    public void delete(Long id) {
        bannerDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Banner> findAll() {
        BannerExample bannerExample = new BannerExample();
        bannerExample.setOrderByClause("sort desc");
        return bannerDao.selectByExample(bannerExample);
    }
}
