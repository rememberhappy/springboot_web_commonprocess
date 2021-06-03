package com.example.interceptor;

import com.example.common.CommonInfoHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * FeignRequestInterceptor：feign调用是的请求拦截
 * RequestInterceptor：Feign 支持请求拦截器，在发送请求前，可以对发送的模板进行操作，例如设置请求头等属性
 *
 * @Author zhangdj
 * @Date 2021/6/3:09:42
 * @Description
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    private final static Logger log = LoggerFactory.getLogger(FeignRequestInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 从公共信息持有者中获取公共信息
        String token = CommonInfoHolder.getToken();
        Integer userId = CommonInfoHolder.getUserId();
        // 将公共信息保存到RequestTemplate中
        requestTemplate.header("userId", userId == null ? null : String.valueOf(userId));
        requestTemplate.header("token", token == null ? null : String.valueOf(token));

        // 对参数的处理
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        Enumeration headerNames = request.getHeaderNames();
        try {
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement().toString();
                    String values = request.getHeader(name);
                    requestTemplate.header(name, values);
                }
            }
            log.info("接口路径：" + request.getRequestURL().toString());
            Enumeration bodyNames = request.getParameterNames();
            if (bodyNames != null) {
                Map<String, String> map = new HashMap<String, String>();
                while (bodyNames.hasMoreElements()) {
                    String name = bodyNames.nextElement().toString();
                    String values = request.getParameter(name);
                    requestTemplate.query(name, values);
                    map.put(name, values);
                }
                log.info("传入参数：" + map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}