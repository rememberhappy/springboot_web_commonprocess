package com.example.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Author zhangdj
 * @Date 2021/6/3:12:07
 * @Description
 */
@ControllerAdvice
public class GlobalException {
    private final static Logger log = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public String handle(IllegalArgumentException e) {
        log.error("参数非法异常", e);
        String msg = e.getMessage();
        if (StringUtils.isEmpty(msg) || msg.length() > 30) {
            msg = "您传入的参数有误";
        }
        return msg;
    }
}