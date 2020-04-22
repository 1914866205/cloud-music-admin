//package com.soft1851.music.admin.util;
//
//import com.aliyuncs.CommonRequest;
//import com.aliyuncs.CommonResponse;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.exceptions.ServerException;
//import com.aliyuncs.http.MethodType;
//import com.aliyuncs.profile.DefaultProfile;
//
///**
// * @Description 短信消息测试程序
// * @Author 涛涛
// * @Date 2020/4/21 12:00
// * @Version 1.0
// **/
//public class SMSTest {
//    public static void main(String[] args) {
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "自己的accessKeyId", "自己的secret");
//        IAcsClient client = new DefaultAcsClient(profile);
//        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
//        request.putQueryParameter("RegionId", "cn-hangzhou");
//        request.putQueryParameter("PhoneNumbers", "接收短信的手机号");
//        request.putQueryParameter("SignName", "短信模板签名");
//        request.putQueryParameter("TemplateCode", "短信模版CODE");
//        request.putQueryParameter("TemplateParam", "{\"code\":\"888888\"}");   //发送的验证码
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//    }
//}