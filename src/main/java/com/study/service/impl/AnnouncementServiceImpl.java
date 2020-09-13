package com.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.dao.announcement.AnnouncementDao;
import com.study.pojo.announcement.Announcement;
import com.study.pojo.announcement.AnnouncementExample;
import com.study.pojo.banner.Banner;
import com.study.pojo.banner.BannerExample;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import com.study.service.AnnouncementService;
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
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementDao announcementDao;

    @Override
    public PageResult<Announcement> findPage(QueryVo queryVo) {
        PageHelper.startPage(queryVo.getPage(), queryVo.getRows());
        AnnouncementExample announcementExample =  new AnnouncementExample();
        announcementExample.setOrderByClause("sort desc");
        if (queryVo.getKeywords() != null) {
            AnnouncementExample.Criteria criteria = announcementExample.createCriteria();
            criteria.andTitleLike("%" + queryVo.getKeywords() + "%");
        }
        List<Announcement> announcements = announcementDao.selectByExample(announcementExample);
        PageInfo<Announcement> pageInfo = new PageInfo<>(announcements);
        return new PageResult<>(pageInfo.getTotal(), announcements);
    }


    @Override
    public void save(Announcement announcement) {
        announcementDao.insertSelective(announcement);
    }

    @Override
    public void edit(Announcement announcement) {
        announcementDao.updateByPrimaryKeySelective(announcement);
    }

    @Override
    public void delete(Long id) {
        announcementDao.deleteByPrimaryKey(id);
    }

    @Override
    public Announcement findFirst() {
        AnnouncementExample announcementExample = new AnnouncementExample();
        announcementExample.setOrderByClause("sort desc");
        List<Announcement> announcements = announcementDao.selectByExample(announcementExample);
        if (announcements != null && announcements.size() > 0) {
            return announcements.get(0);
        } else {
            return null;
        }
    }
}
