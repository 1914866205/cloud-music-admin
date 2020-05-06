package com.soft1851.music.admin.service;

import com.soft1851.music.admin.domain.entity.SysAdmin;
import com.soft1851.music.admin.util.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class SysAdminServiceTest {
    @Resource
    private SysAdminService sysAdminService;
    @Resource
    private DataSource dataSource;
    @Test
    void getAdminByName(){
//        SysAdmin sysAdmin = sysAdminService.getAdminByName("taoranran2");
//        System.out.println(sysAdmin);
//        sysAdmin.setName("taoranran");
//        System.out.println(sysAdminService.setAdminInfo(sysAdmin));
//        System.out.println(dataSource.getClass());
//        System.out.println(sysAdminService.getAdminByName("taoranran"));
//        System.out.println(sysAdminService.getAdminById("22516FB6A9D389D7FC21420806150A7B"));
//            String t="2019-12-04T01:52:21Z";
//        System.out.println(t.replace("T"," ").substring(0,t.length()-1));
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String timeStr = "2019-01-01 00:00:00";
//        LocalDateTime dateTime = LocalDateTime.parse(timeStr, formatter);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String createTimeStr = "2020-05-06 07:34:15";
        String updateTimeStr = "2020-05-06 07:34:15";
        LocalDateTime createTimeLDT = LocalDateTime.parse(createTimeStr,df);
        LocalDateTime updateTimeLDT = LocalDateTime.parse(updateTimeStr,df);
//

        SysAdmin sysAdmin=SysAdmin.builder()
                .id("C8651E6614")
                .name("1914866205")
                .password("E10ADC3949BA59ABBE56E057F20F883E")
                .salt("C33367701511B4F6020EC61DED352059")
                .avatar("https://avatars2.githubusercontent.com/u/58495771?v=4")
                .createTime(createTimeLDT)
                .updateTime(updateTimeLDT)
                .status(1)
                .build();
         sysAdminService.addAdmin(sysAdmin);
    }

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