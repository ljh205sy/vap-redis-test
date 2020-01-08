package com.vrv.vap.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

/**
 * @Author: liujinhui
 * @Date: 2019/9/12 13:59
 */
public class TimerFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info(getClass().getName() + " init....");
    }

    @Override
    public void destroy() {
        logger.info(getClass().getName() + " destroy....");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Date start = new Date();
        logger.info("timeFilter start time:" + start.getTime());
        HttpServletRequest srequest = (HttpServletRequest)request;
        logger.info("浏览器发送的请求：http://localhost:8081/userVO?a=1&1=1, 但是uri是/userVO, 实际上也可以往@RequestBody中设置参数！");
        logger.info("uri:{}", srequest.getRequestURI());

        logger.info("能获取到参数，但是不能获取到对象的值，http://localhost:8081/userVO?a=1&1=1");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            logger.info("path参数：" + s);
        }
        logger.info("222---------------------------------------------");
        chain.doFilter(request, response);
        logger.info("timeFilter kill time:" + (new Date().getTime() - start.getTime()));
    }
}
