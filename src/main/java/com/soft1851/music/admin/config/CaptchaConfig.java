package com.soft1851.music.admin.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Description
 * @Author 涛涛
 * @Date 2020/4/21 10:59
 * @Version 1.0
 **/
@Configuration
public class CaptchaConfig {
    //Bean可以使这个对象被容器托管
    //要想被容器托管，必须要有无参构造器
    @Bean
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        //setter注入
        properties.setProperty("kaptcha.textproducer.char.length","4");
        properties.setProperty("kaptcha.border","yes");
        properties.setProperty("kaptcha.border.color","105,180,90");
        //验证码字体的颜色
        properties.setProperty("kaptcha.textproducer.font.color","red");
        //验证码字体的大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        //验证码字体的风格
        properties.setProperty("kaptcha.textproducer.font.names", "微软雅黑,楷体,宋体");

        properties.setProperty("kaptcha.image.width","120");
        properties.setProperty("kaptcha.image.height","45");
        properties.setProperty("kaptcha.session.key","code");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
