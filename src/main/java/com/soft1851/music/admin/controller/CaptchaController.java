package com.soft1851.music.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.soft1851.music.admin.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Description TODO 获取验证码
 * @Author 涛涛
 * @Date 2020/4/21 11:19
 * @Version 1.0
 **/
@RestController
@Slf4j
public class CaptchaController {
    //变量名 要为bean的 方法名
    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private RedisService redisService;
    @PostMapping("/captcha")
    public void defaultCaptcha(@Param("userIp") String userIp){
//    public void defaultCaptcha(@RequestBody String userIp){
        log.info("**********************");
        log.info(userIp);
//        JSONObject jsonObject = JSONObject.parseObject(userIp);
        //如果key存在，则修改key
//        String ip = jsonObject.getString("userIp");
//        String ip = jsonObject.getString("value");
            //生成验证码
            String text = defaultKaptcha.createText();
            log.info(text);

        //把验证码存放在Redis    有效时长10分钟
            redisService.set(userIp, text, 10L);
//            redisService.set(ip, text, 10L);
//        if (redisService.get(ip) != null) {
//            redisService.set(ip,text,10);
//        }else {
//            //如果key不存在，则保存key
//            redisService.save(ip,text,10);
//        }
        log.info("**********************");
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert sra != null;
        HttpServletResponse response = sra.getResponse();

//        sout只有在自己开发的机器上显示
//        log可以在服务器上显示
//        生成验证码图片，将text写入，并通过response输出到客户端浏览器
        BufferedImage image = defaultKaptcha.createImage(text);
        assert response != null;
        response.setContentType("image/jpeg");
        //设置失效时间            不失效
        response.setDateHeader("Expires",10);
        try {
            ImageIO.write(image, "jpg", response.getOutputStream());
        } catch (IOException e) {
            log.error("图片传输异常");
            e.printStackTrace();
        }

    }
}
