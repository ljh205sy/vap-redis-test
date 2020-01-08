package com.vrv.vap.config;

import com.vrv.vap.filter.TimerFilter;
import com.vrv.vap.interceptor.TimerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liujinhui
 * @Date: 2019/9/12 14:05
 */
@Configuration
public class VapConfiguration  extends WebMvcConfigurerAdapter {

    @Autowired
    private TimerInterceptor timerInterceptor;

    @Bean
    public FilterRegistrationBean timeFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TimerFilter timeFilter = new TimerFilter();
        List<String> urls = new ArrayList<String>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        registrationBean.setFilter(timeFilter);
        return registrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timerInterceptor);
    }
}
