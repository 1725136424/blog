package com.study.pojo.article;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.pojo.category.Category;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * article
 * @author 
 */
@Data
public class Article implements Serializable {
    private Long id;

    private String title;

    private String subTitle;

    private String content;

    private String picture;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date editDate;

    private Long userId;

    private Long categoryId;

    private Category category;

    private static final long serialVersionUID = 1L;
}