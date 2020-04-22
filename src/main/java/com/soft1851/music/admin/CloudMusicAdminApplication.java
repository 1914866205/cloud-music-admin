package com.soft1851.music.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@MapperScan("com.soft1851.music.admin.mapper")
@ServletComponentScan
public class CloudMusicAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudMusicAdminApplication.class, args);
    }
}
