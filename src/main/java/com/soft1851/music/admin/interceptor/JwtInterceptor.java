package com.soft1851.music.admin.interceptor;

import com.alibaba.fastjson.JSONArray;
import com.soft1851.music.admin.common.ResultCode;
import com.soft1851.music.admin.domain.entity.SysRole;
import com.soft1851.music.admin.exception.JwtException;
import com.soft1851.music.admin.service.RedisService;
import com.soft1851.music.admin.service.SysAdminService;
import com.soft1851.music.admin.service.SysRoleService;
import com.soft1851.music.admin.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description Jwt拦截器
 * @Author 涛涛
 * @Date 2020/4/15 17:11
 * @Version 1.0
 **/

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private RedisService redisService;

    /**
     * 前置处理，拦截请求
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取token
        String token = request.getHeader("Authorization");
        //认证
        if (token == null) {
            log.info("### 用户未登录，请先登录 ###");
            //通过自定义异常抛出未登录的信息，由全局统一处理
            throw new JwtException("用户未登录，请先登录", ResultCode.USER_NOT_SIGN_IN);
        } else {
            //已经登录
            log.info("## token={}", token);
            //从请求头中取出ip
            String userIp = request.getHeader("userIp");
            //鉴权
//            //根据id查到权限
//            String getAdminMenuByAdminId = sysAdminService.getAdminMenuByAdminId(JwtTokenUtil.getUserId(token)).toString();
            //查询token的权限
            //用这个secrect私钥从token中解析出roles字符串
            String secrect = redisService.getValue(userIp,String.class);
            System.out.println("secret"+secrect);
            //从token中解析出rolds字符串
            String tokenRole=JwtTokenUtil.getUserRole(token,secrect);
            log.info("## tokenRole={}",tokenRole);
//            反序列化成List
            List<SysRole> roleList = JSONArray.parseArray(tokenRole, SysRole.class);
            //从request中取出客户端传来的roleId
            String roleId=request.getParameter("roleId");
            log.info("## roleId={}",roleId);
            //到 roles中查找对比，此部分代码在SysRoleService
            boolean flag = sysRoleService.checkRole(roleList, Integer.parseInt(roleId));
            log.info("## flag={}", flag);
            //在token中解析出的roles中含有请求的role值，放行到controller中获取数据
//            log.info("根据id查权限"+sysAdminService.getAdminMenuByAdminId(JwtTokenUtil.getUserId(token)).toString());
//            log.info("根据权限查id"+JwtTokenUtil.getUserRole(token));
//            if (!getAdminMenuByAdminId.equals(tokenRole)) {
            if (!flag) {
                log.info("### 用户权限不足 ###");
                //通过自定义异常抛出权限不足的信息，由全局统一处理
                throw new JwtException("用户权限不足", ResultCode.PERMISSION_NO_ACCESS);
            } else {
                //@Description 到期时间在当前时间之前
//                if (JwtTokenUtil.isExpiration(token)) {
//                    log.info("### token已失效 ###");
                    //通过自定义异常抛出token失效的信息，由全局统一处理
//                    throw new JwtException("token已失效", ResultCode.USER_TOKEN_EXPIRES);
//                } else {
                    log.info("通过认证，放行到controller层");
                    //通过认证，放行到controller层
                    return true;
//                }
            }
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
