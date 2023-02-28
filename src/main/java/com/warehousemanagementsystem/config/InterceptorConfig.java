package com.warehousemanagementsystem.config;

import com.warehousemanagementsystem.interceptor.Authinterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * 定义整个项目的拦截器配置类
 * */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    /*
     * 将自定义拦截器对象交给Spring容器管理
     * */
    @Bean
    public Authinterceptor authinterceptor() {
        return new Authinterceptor();
    }

    /*
     * 重写addInterceptors方法,将自定义的拦截器对象添加到项目拦截器中
     * 设置要拦截和不拦截的请求
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authinterceptor()).addPathPatterns("/**").excludePathPatterns("/sys_user","/register_sys_user");
    }
}
