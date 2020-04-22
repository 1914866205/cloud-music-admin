package com.soft1851.music.admin.config;

import com.soft1851.music.admin.interceptor.JwtInterceptor;
import com.soft1851.music.admin.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author 涛涛
 * @Date 2020/4/15 17:09
 * @Version 1.0
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private JwtInterceptor jwtInterceptor;

    @Resource
    private LoginInterceptor loginInterceptor;
    /**
     * 添加拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截路径可自行配置多个  可用 , 分割开                                                                          放行特殊请求
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns("/login","/captcha").excludePathPatterns("/static/**");

        //添加验证码的拦截器
        registry.addInterceptor(loginInterceptor).addPathPatterns("/login");
    }



}
