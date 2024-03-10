package com.banne.template.config;

import com.banne.template.interceptor.GlobalInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    public GlobalInterceptor globalInterceptor;

    // 对swagger的请求不进行拦截
    String[] excludePatterns = new String[]{
            "/swagger-resources/**",
            "/webjars/**",
            "/v2/**",
            "/swagger-ui.html/**",
            "/api",
            "/api-docs",
            "/api-docs/**",
            "/doc.html/**",
            "/user/login",
            "/user/register"
    };

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePatterns);
    }
}
