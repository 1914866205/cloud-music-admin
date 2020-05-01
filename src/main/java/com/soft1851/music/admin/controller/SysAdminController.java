package com.soft1851.music.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.soft1851.music.admin.common.ResponseResult;
import com.soft1851.music.admin.common.ResultCode;
import com.soft1851.music.admin.domain.dto.LoginDto;
import com.soft1851.music.admin.domain.entity.SysAdmin;
import com.soft1851.music.admin.domain.entity.SysRole;
import com.soft1851.music.admin.service.RedisService;
import com.soft1851.music.admin.service.SysAdminService;
import com.soft1851.music.admin.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
@RequestMapping(value = "/sysAdmin")
@RestController
@Slf4j
public class SysAdminController {
    @Resource
    private SysAdminService sysAdminService;

    @Resource
    private RedisService redisService;

    @PostMapping("/login")
    public Map login(@RequestBody LoginDto loginDto) {
//    public Map login(LoginDto loginDto) {
        log.info(loginDto.toString());
        log.info("进入登录");
        Map<String, Object> map = new TreeMap<>();
        //判断登录结果
        boolean isLogin = sysAdminService.isLogin(loginDto);
        log.info(String.valueOf(isLogin));
        if (isLogin) {
            //登录成功，取得admin的信息，包括所有角色
            SysAdmin admin = sysAdminService.getAdminAndRolesByName(loginDto.getUsername());
            //roles是一个list,也可能会是多个
            List<SysRole> roles = admin.getRoles();
            String roleString = JSONObject.toJSONString(roles);
            log.info("管理员角色列表：" + roleString);
            //将盐加入到redis缓存  因为验证码已使用，所以不需要了
            redisService.set(loginDto.getUserIp(), admin.getSalt(), 10L);
            System.out.println(redisService.getValue(loginDto.getUserIp(), String.class));
            //将该管理员 的所有角色的集合roles存入token,在后面鉴权的时候查找,有效时间为10分钟
            System.out.println(admin);
            String token = JwtTokenUtil.getToken(admin.getId(), JSONObject.toJSONString(roles), admin.getSalt(), new Date(System.currentTimeMillis() + 10L * 60L * 1000L));
            map.put("admin", admin);
            log.info(token);
            map.put("token", token);
        } else {
            map.put("msg", "登录失败");
        }
        return map;
    }

    @GetMapping("/permission")
    public ResponseResult getPerMissions(@Param("name") String name) {
        log.info("鉴权成功");
        System.out.println(sysAdminService.getAdminAndRolesByName(name));
        return ResponseResult.success(sysAdminService.getAdminAndRolesByName(name));
    }

    @GetMapping("/adminInfo")
    public SysAdmin getAdminInfo(@Param("name") String name) {
        log.info("查看用户信息");
        return sysAdminService.getAdminByName(name);
    }

    @PostMapping("/setAdminInfo")
    public ResponseResult setAdminInfo(@RequestBody SysAdmin sysAdmin) {
        log.info("*******************");
        log.info(sysAdmin.toString());
        log.info("*******************");
        if (sysAdmin.getName()==null&&sysAdmin.getName().length()<4){
            return ResponseResult.failure(ResultCode.DATA_IS_WRONG);
        }
        if (sysAdmin.getPassword()==null&&sysAdmin.getPassword().length()>50&&sysAdmin.getPassword().length()<6){
            return ResponseResult.failure(ResultCode.DATA_IS_WRONG);
        }
        return ResponseResult.success(sysAdminService.setAdminInfo(sysAdmin));
    }
}
