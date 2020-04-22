package com.soft1851.music.admin.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * @Description TODO
 * @Author 涛涛
 * @Date 2020/4/15 16:52
 * @Version 1.0
 **/
@Configuration
public class CorsConfig {
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //放行所有跨域的客户端domain
        //domain通常就代表了与数据库表--一一对应的javaBean
        config.addAllowedOrigin("*");
        //允许的请求方法列表
        String[] requestMethods = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};
        List<String> allowedRequestMethods = Arrays.asList(requestMethods);
        config.setAllowedMethods(allowedRequestMethods);
        //允许的客户端请求列表
        String[] requestHeaders = {"x-requested-with", "Content-Type", "Authorization"};
        List<String> allowedHeaders = Arrays.asList(requestHeaders);
        config.setAllowedHeaders(allowedHeaders);

        //允许的响应头列表
        String[] responseHeaders = {"Access-Control-Expose-Headers", "Authorization"};
        List<String> allowedExposeHeaders = Arrays.asList(responseHeaders);
        config.setExposedHeaders(allowedExposeHeaders);

        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        ///这个bean的顺序很重要，设置在最前
        bean.setOrder(0);
        return bean;
    }
}
