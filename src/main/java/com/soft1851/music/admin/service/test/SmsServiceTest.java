//package com.soft1851.music.admin.service;
//
//
//import com.scs.web.space.api.Common.Result;
//import com.scs.web.space.api.SpaceApiApplication;
//import com.scs.web.space.api.domain.dto.SignDto;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import javax.annotation.Resource;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * @Description TODO
// * @Author 涛涛
// * @Date 2020/4/21 12:09
// * @Version 1.0
// **/
//@SpringBootTest(classes = SpaceApiApplication.class)
//class SmsServiceTest {
//    @Resource
//    private SmsService smsService;
//
//    @Test
//    void sendSms() {
//        //发端短信到***手机号
//        SignDto signDto = SignDto.builder().mobile("14752191369").build();
//        Result result = smsService.sendSms(signDto);
//        assertEquals(1, result.getCode());
//        System.out.println(result);
//    }
//
//    @Test
//    void checkSms() {
//        // 这里手机号要与你发送验证码的手机号一致
//        SignDto signDto = SignDto.builder().mobile("14752191369").sms("642691").build();
//        Result result = smsService.checkSms(signDto);
//        if (result.getCode() == 1) {
//            System.out.println("验证通过");
//        } else {
//            System.out.println(result.getMsg());
//        }
//    }
//}
