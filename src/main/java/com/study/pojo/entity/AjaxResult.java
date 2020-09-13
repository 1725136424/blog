package com.study.pojo.entity;

import lombok.Data;

@Data
public class AjaxResult {

    private Boolean isSuccess;

    private String message;

    // 1 -> 验证码错误
    // 2 -> 账号不存在
    // 3 -> 密码错误
    // 4 -> 未知异常
    private Integer ErrorClassification;

}
