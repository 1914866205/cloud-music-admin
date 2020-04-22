package com.soft1851.music.admin.service;

import com.soft1851.music.admin.dto.LoginDto;
import com.soft1851.music.admin.entity.SysAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
public interface SysAdminService extends IService<SysAdmin> {

    /**
     * 登录
     *
     * @param loginDto
     * @return boolean
     */
    boolean login(LoginDto loginDto);

    /**
     * 根据用户id查询用户信息及角色信息
     * @param userId
     * @return
     */
    Map<String, Object> getAdminMenuByAdminId(String userId);


    /**
     * 根据用户查id
     * @return
     */
    String getIdByAdmin(SysAdmin sysAdmin);


    /**
     * 登录
     * @param loginDto
     * @return
     */
    String sign(LoginDto loginDto);

    Map<String,Object> getAdminMenuByAdminId();
}
