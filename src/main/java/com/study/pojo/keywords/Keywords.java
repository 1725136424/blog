package com.study.pojo.keywords;

import java.io.Serializable;
import lombok.Data;

/**
 * keywords
 * @author 
 */
@Data
public class Keywords implements Serializable {
    private Long id;

    private String name;

    private Integer sort;

    private static final long serialVersionUID = 1L;
}