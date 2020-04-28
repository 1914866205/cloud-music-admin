package com.soft1851.music.admin.controller;


import com.soft1851.music.admin.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
@RequestMapping(value = "/tokenFlush")
@RestController
@Slf4j
public class TokenController {

    @PostMapping()
    public String tokenFlush(@RequestBody String token) {
//        log.info("原始token" + token);
//        log.info("更新token");
//        //将该管理员 的所有角色的集合roles存入token,在后面鉴权的时候查找,有效时间为10分钟
//        String newToken = JwtTokenUtil.getToken(JwtTokenUtil.getUserId(token,), JwtTokenUtil.getUserRole(token), new Date(System.currentTimeMillis() + 10L * 60L * 1000L));
//        log.info("最新token" + newToken);
//        return newToken;
        return null;
    }
}
