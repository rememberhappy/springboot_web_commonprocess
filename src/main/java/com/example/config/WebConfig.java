package com.example.config;

import com.example.exception.GlobalException;
import com.example.filter.CommonInfoFilter;
import com.example.interceptor.FeignRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.annotation.WebFilter;

/**
 * @Author zhangdj
 * @Date 2021/6/3:12:11
 * @Description
 */
@Configuration
public class WebConfig {
    @Bean
    public GlobalException globalException() {
        return new GlobalException();
    }

    @ConditionalOnClass({WebFilter.class})
    @Bean
    public CommonInfoFilter commonInfoFilter() {
        return new CommonInfoFilter();
    }

    @ConditionalOnClass({RequestInterceptor.class})
    @Bean
    public FeignRequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }
}