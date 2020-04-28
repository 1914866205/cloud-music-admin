package com.soft1851.music.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.soft1851.music.admin.common.ResultCode;
import com.soft1851.music.admin.exception.CustomException;
import com.soft1851.music.admin.handler.RequestWrapper;
import com.soft1851.music.admin.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description TODO
 * @Author 涛涛
 * @Date 2020/4/21 18:56
 * @Version 1.0
 **/
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private RedisService redisService;

    /**
     * 前置处理，拦截请求
     * 众所周知所有的post请求中的body参数是已流形式存在的，
     * 而流数据只能读取一次（为啥看这里），如果在拦截器中需要对post参数进行处理的话，就会报Required request body is missing 异常。
     * 既然知道原因，那只要能将流保存起来就可以解决问题。
     *
     * 前置处理，拦截登录请求，校验参数、验证码等
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//在拦截器的的preHandle中用RequestWrapper替代HttpServletRequest
        RequestWrapper requestWrapper = new RequestWrapper(request);
        String body = requestWrapper.getBody();
        log.info(body);
        JSONObject jsonObject = JSONObject.parseObject(body);
        //获取验证码
        String key = jsonObject.getString("userIp"); //根据参数名称获取到参数值
        String checkCode = jsonObject.getString("checkCode"); //根据参数名称获取到参数值

        log.info("输入的验证码：" + checkCode);
        log.info("传来的key：" + key);
        log.info("真实的验证码：" + redisService.getValue(key,String.class).trim());
        //判断验证码的有效性和正确性
        //忽略大小写比对，成功则放行到controller调用登录接口
        log.info("是否放行" + checkCode.equalsIgnoreCase(redisService.getValue(key,String.class).trim()));
        if (checkCode.equalsIgnoreCase(redisService.getValue(key,String.class).trim())) {
            //验证码有效且正确      放行
//            LoginDto loginDto = LoginDto.builder().username(jsonObject.getString("username")).password(jsonObject.getString("password")).checkCode(checkCode).userIp(key).build();
            log.info("放行");
            return true;
        } else {
            log.info("不放行");
            //验证码错误或失效
            throw new CustomException("验证码错误或失效", ResultCode.USER_VERIFY_CODE_ERROR);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
