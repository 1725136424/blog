package com.study.service;

import com.study.pojo.announcement.Announcement;
import com.study.pojo.banner.Banner;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface AnnouncementService {

    PageResult<Announcement> findPage(QueryVo queryVo);


    void save(Announcement announcement);

    void edit(Announcement announcement);

    void delete(Long id);

    Announcement findFirst();
}
