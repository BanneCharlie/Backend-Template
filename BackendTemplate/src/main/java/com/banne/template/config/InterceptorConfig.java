package com.banne.template.config;

import com.banne.template.interceptor.GlobalInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    public GlobalInterceptor globalInterceptor;

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/user/register");
    }
}
