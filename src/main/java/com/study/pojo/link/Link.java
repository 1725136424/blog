package com.study.pojo.link;

import java.io.Serializable;
import lombok.Data;

/**
 * link
 * @author 
 */
@Data
public class Link implements Serializable {
    private Long id;

    private String name;

    private String url;

    private Integer sort;

    private static final long serialVersionUID = 1L;
}