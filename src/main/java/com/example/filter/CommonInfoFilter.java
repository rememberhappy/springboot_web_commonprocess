package com.example.filter;

import com.example.common.CommonInfoHolder;
import com.example.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @Author zhangdj
 * @Date 2021/6/3:11:56
 * @Description
 */
@WebFilter(
        urlPatterns = {"/*"}
)
@Order(1)
public class CommonInfoFilter implements Filter {
    private final static Logger log = LoggerFactory.getLogger(CommonInfoFilter.class);

    public void init(FilterConfig filterConfig) {
        log.info("CommonInfoFilter init");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("*********   开始拦截");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        // 静态资源请求直接放过
        if (!requestURI.endsWith(".html") && !requestURI.endsWith(".css") && !requestURI.endsWith(".js") && !requestURI.endsWith(".png") && !requestURI.endsWith(".jpg")) {
            Enumeration<String> headerNames = request.getHeaderNames();
            String userId = request.getHeader("userId");
            String token = request.getHeader("token");
            log.info("请求头信息 userId：{}", userId);
            log.info("请求头信息 token：{}", token);
            if (StringUtils.isNotEmpty(userId)) {
                CommonInfoHolder.setUserId(Integer.valueOf(userId));
            }
            CommonInfoHolder.setToken(token);
            log.info("公共信息持有中信息设置完毕");
        }
        // 执行方法
        filterChain.doFilter(servletRequest, servletResponse);
        // 执行完后请理垃圾数据
        CommonInfoHolder.clean();
    }

    public void destroy() {
    }
}
