package com.vrv.vap.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author: liujinhui
 * @Date: 2019/9/12 14:10
 */
@Component
public class TimerInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Date date = new Date();
        request.setAttribute("startTime", date.getTime());
       /* String methodName = ((HandlerMethod) handler).getMethod().getName();
        String beanName = ((HandlerMethod) handler).getBean().getClass().getName();
        logger.info("method: {}, beanName: {}", methodName, beanName);*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        logger.info("TimerInterceptor >>>> postHandle time: {}", (endTime - startTime));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        logger.info("TimerInterceptor --------- afterCompletion time: {}", (endTime - startTime));
        logger.info("TimerInterceptor >>>> afterCompletion  time: {}， ex: {}", (endTime - startTime), ex);
//        logger.info("TimerInterceptor >>>> afterCompletion  time: {}， ex: {}", (new Date().getTime() - startTime), ex.getMessage());
    }
}
