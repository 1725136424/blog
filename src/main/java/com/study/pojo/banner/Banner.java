package com.study.pojo.banner;

import java.io.Serializable;
import lombok.Data;

/**
 * banner
 * @author 
 */
@Data
public class Banner implements Serializable {
    private Long id;

    private String picture;

    private String url;

    private Integer sort;

    private String name;

    private static final long serialVersionUID = 1L;
}