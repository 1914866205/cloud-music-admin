package com.soft1851.music.admin.service;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.soft1851.music.admin.dto.LoginDto;
import com.soft1851.music.admin.mapper.SysAdminMapper;
import com.soft1851.music.admin.util.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SysAdminServiceTest {
    @Resource
    private SysAdminService sysAdminService;
//    @Resource
//    private DefaultKaptcha defaultKaptcha;
//    @Test
//    void login() {
//        //登录，输入账号密码
////        LoginDto loginDto = LoginDto.builder().name("mqxu").password("123123").build();
//        LoginDto loginDto = LoginDto.builder().name("mqxu").password("123456").build();
//        //产生验证码
//        String text = defaultKaptcha.createText();
//        System.out.println(text);
//        //模拟输入验证码
////        Scanner sc = new Scanner(System.in);
////        String str = sc.nextLine();
//        String str = text;
//        //对比验证码
//        if (str.equals(text)){
//            if (sysAdminService.login(loginDto)){
//                System.out.println("登录成功");
//            }else {
//                System.out.println("用户名或密码错误");
//            }
//
//        }else {
//            System.out.println("验证码错误");
//        }
//        //对比账号密码
//    }
}