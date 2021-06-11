package com.example.exception;

import com.example.enums.ExceptionEnum;

/**
 * 自定义异常信息
 *
 * @Author zhangdj
 * @Date 2021/6/11:17:29
 */
public class CommonException extends Exception {
    private int code;
    private String message;

    public CommonException() {
        this.code = -1;
        this.message = "不存在";
    }

    public CommonException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonException(ExceptionEnum e) {
        this.code = e.getCode();
        this.message = e.getMessage();
    }

}