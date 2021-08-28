package com.nowcoder.community.config;

import com.nowcoder.community.interceptor.AlphaInterceptor;
import com.nowcoder.community.interceptor.LoginTicketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private AlphaInterceptor alphaInterceptor;

    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(alphaInterceptor)    //不写下几行会拦截一切请求
                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.jpg","/**/*.jpeg")  //这么写会排除拦截一些静态资源
                .addPathPatterns("/register","/login")      //这么写会增加拦截的路径
        ;
        registry.addInterceptor(loginTicketInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.jpg","/**/*.jpeg");
    }
}
