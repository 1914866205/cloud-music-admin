package com.soft1851.music.admin.interceptor;

import com.soft1851.music.admin.common.ResponseResult;
import com.soft1851.music.admin.common.ResultCode;
import com.soft1851.music.admin.dto.LoginDto;
import com.soft1851.music.admin.exception.CustomException;
import com.soft1851.music.admin.exception.JwtException;
import com.soft1851.music.admin.service.RedisService;
import com.soft1851.music.admin.service.SysAdminService;
import com.soft1851.music.admin.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

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
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        获取post方式请求参数：
        //获取验证码
        String key = request.getParameter("userIp"); //根据参数名称获取到参数值
        String checkCode = request.getParameter("checkCode"); //根据参数名称获取到参数值
        log.info("输入的验证码：" + checkCode);
        log.info("真实的验证码：" + redisService.get(key));
        //判断验证码的有效性和正确性
        //忽略大小写比对，成功则放行到controller调用登录接口
        if (checkCode.equalsIgnoreCase(redisService.get(key).trim())) {
            //验证码有效且正确      放行
            return true;
        } else {
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
