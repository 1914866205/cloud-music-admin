package com.soft1851.music.admin.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description TODO
 * @Author 涛涛
 * @Date 2020/4/26 11:22
 * @Version 1.0
 **/
//开启事务支出
@EnableTransactionManagement
@Configuration
@MapperScan("com.soft1851.music.admin.mapper")
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //设置请求的页面大于最大页后操作,true调回首页，false继续请求，默认为false
        // pagintionInterceptor.setOverflow(false)
        // 设置最大单页限制数量，默认 500 条，-1不受限制
        //paginationInterceptor.setLimit(500)
        //开启count的join优化，只针对部分left join
//        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
}
