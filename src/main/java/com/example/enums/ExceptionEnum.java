package com.example.enums;

public enum ExceptionEnum {
    INVALID_AUTHCODE(40001, "无效验证码"),
    INVALID_PASSWORD(40002, "账号/密码不符"),
    INVALID_TOKEN(40003, "无效登录状态"),
    ;

    private int code;
    private String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
