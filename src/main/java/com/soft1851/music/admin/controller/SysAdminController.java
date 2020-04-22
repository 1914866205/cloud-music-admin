package com.soft1851.music.admin.controller;


import com.soft1851.music.admin.common.ResponseResult;
import com.soft1851.music.admin.common.ResultCode;
import com.soft1851.music.admin.dto.LoginDto;
import com.soft1851.music.admin.entity.SysAdmin;
import com.soft1851.music.admin.service.RedisService;
import com.soft1851.music.admin.service.SysAdminService;
import com.soft1851.music.admin.util.JwtTokenUtil;
import com.soft1851.music.admin.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
//@RequestMapping("/sysAdmin")
@RestController
@Slf4j
public class SysAdminController {
    @Resource
    private SysAdminService sysAdminService;
    @PostMapping("/login")
    public ResponseResult adminLogin(String username, String password,String checkCode) {
            LoginDto loginDto = new LoginDto();
            loginDto.setName(username);
            loginDto.setPassword(password);
            loginDto.setVerigyCode(checkCode);
        return ResponseResult.success(sysAdminService.sign(loginDto));
        }

    @GetMapping("/permission")
    public ResponseResult getPerMissions() {
        return ResponseResult.success(sysAdminService.getAdminMenuByAdminId());
    }
}
