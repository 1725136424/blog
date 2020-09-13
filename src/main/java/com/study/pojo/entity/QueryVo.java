package com.study.pojo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryVo implements Serializable {

    private Integer page = 1;

    private Integer rows = 5;

    private String keywords;

    private static final long serialVersionUID = 1L;
}
