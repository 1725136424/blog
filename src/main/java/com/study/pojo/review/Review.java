package com.study.pojo.review;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.pojo.article.Article;
import com.study.pojo.user.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * review
 * @author 
 */
@Data
public class Review implements Serializable {
    private Long id;

    private String content;

    private Boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date remarkDate;

    private Long userId;

    private Long articleId;

    private Long parentId;

    private User user;

    private Article article;

    private List<Review> children = new ArrayList<>();

    private Review parent;

    private static final long serialVersionUID = 1L;
}