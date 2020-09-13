package com.study.service;

import com.study.pojo.banner.Banner;
import com.study.pojo.entity.PageResult;
import com.study.pojo.entity.QueryVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface BannerService {

    PageResult<Banner> findPage(QueryVo queryVo);
    
    void save(Banner banner);

    void edit(Banner banner);

    void delete(Long id);

    List<Banner> findAll();
}
