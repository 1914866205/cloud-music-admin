//package com.soft1851.music.admin.service.Impl;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.aliyuncs.CommonRequest;
//import com.aliyuncs.CommonResponse;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.http.MethodType;
//import com.aliyuncs.profile.DefaultProfile;
//import com.scs.web.space.api.Common.Result;
//import com.scs.web.space.api.Common.ResultCode;
//import com.scs.web.space.api.domain.dto.SignDto;
//import com.scs.web.space.api.service.RedisService;
//import com.scs.web.space.api.service.SmsService;
//import com.scs.web.space.api.util.StringUtil;
//import com.soft1851.music.admin.service.RedisService;
//import com.soft1851.music.admin.service.SmsService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import javax.annotation.Resource;
///**
// * @Description TODO
// * @Author 涛涛
// * @Date 2020/4/21 12:08
// * @Version 1.0
// **/
//@Service
//@Slf4j
//public class SmsServiceImpl implements SmsService {
//    @Resource
//    private RedisService redisService;
//    @Override
//    public Result sendSms(SignDto signDto) {
//        String mobile = signDto.getMobile();
//        DefaultProfile profile = DefaultProfile.getProfile(
//                "cn-shanghai",
//                "LTAI4FpzLFy8uA2PWAXH8cwQ",
//                "XLTomRADcglUJ5IgRrHxWKJMjPqg8b");
//        IAcsClient client = new DefaultAcsClient(profile);
//        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
//        request.putQueryParameter("RegionId", "cn-shanghai");
//        request.putQueryParameter("PhoneNumbers", mobile);
//        request.putQueryParameter("SignName", "蜂王");
//        request.putQueryParameter("TemplateCode", "SMS_179226026");
//        String verifyCode = StringUtil.getVerifyCode();
//        request.putQueryParameter("TemplateParam", "{\"code\":" + verifyCode + "}");
//        CommonResponse response;
//        try {
//            response = client.getCommonResponse(request);
//        } catch (ClientException e) {
//            log.error("短信发送异常");
//            return Result.failure(ResultCode.SMS_ERROR);
//        }
//        //resData样例：{"Message":"OK","RequestId":"0F3A84A6-55CA-4984-962D-F6F54281303E","BizId":"300504175696737408^0","Code":"OK"}
//        String resData = response.getData();
//        //将返回的JSON字符串转成JSON对象
//        JSONObject jsonObject = JSONObject.parseObject(resData);
//        if ("OK".equals(jsonObject.get("Code"))) {
//            System.out.println(verifyCode);
//            //存入redis，1分钟有效
//            redisService.set(mobile, verifyCode, 2L);
//            return Result.success(verifyCode);
//        } else {
//            return Result.failure(ResultCode.SMS_ERROR);
//        }
//    }
//    @Override
//    public Result checkSms(SignDto signDto) {
//        String mobile = signDto.getMobile();
//        String sms = signDto.getSms();
//        System.out.println(signDto);
//        String correctSms = redisService.getValue(mobile,String.class);
//        System.out.println(correctSms);
//        if (correctSms != null) {
//            //将客户端传来的短信验证码和redis取出的短信验证码比对
//            if (correctSms.equals(sms)) {
//                return Result.success();
//            } else {
//                //验证码错误
//                return Result.failure(ResultCode.USER_VERIFY_CODE_ERROR);
//            }
//        }
//        //验证码失效
//        return Result.failure(ResultCode.USER_CODE_TIMEOUT);
//    }
//}