package com.soft1851.music.admin.controller;


import com.soft1851.music.admin.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
@Slf4j
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;
    @GetMapping()
    public Map getRoleId(@Param("roleId") String roleId){
        log.info(sysRoleService.selectRoleById(Integer.parseInt(roleId)).toString());
        return sysRoleService.selectRoleById(Integer.parseInt(roleId));
    }
}
