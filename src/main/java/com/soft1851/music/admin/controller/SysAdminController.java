package com.soft1851.music.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.soft1851.music.admin.common.ResponseResult;
import com.soft1851.music.admin.dto.LoginDto;
import com.soft1851.music.admin.entity.SysAdmin;
import com.soft1851.music.admin.entity.SysRole;
import com.soft1851.music.admin.service.SysAdminService;
import com.soft1851.music.admin.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
            //将该管理员 的所有角色的集合roles存入token,在后面鉴权的时候查找,有效时间为10分钟
            String token = JwtTokenUtil.getToken(admin.getId(), JSONObject.toJSONString(roles), new Date(System.currentTimeMillis() + 10L * 60L * 1000L));
            map.put("admin", admin);
            map.put("token", token);
        } else {
            map.put("msg", "登录失败");
        }
        return map;
    }

    @GetMapping("/permission")
    public ResponseResult getPerMissions(@Param("name") String name) {
        log.info("鉴权成功");
        return ResponseResult.success(sysAdminService.getAdminAndRolesByName(name));
    }
}
