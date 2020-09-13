package com.study.pojo.announcement;

import java.io.Serializable;
import lombok.Data;

/**
 * announcement
 * @author 
 */
@Data
public class Announcement implements Serializable {
    private Long id;

    private String picture;

    private String content;

    private String title;

    private Integer sort;

    private static final long serialVersionUID = 1L;
}