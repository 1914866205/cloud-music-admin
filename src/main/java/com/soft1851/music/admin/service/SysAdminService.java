package com.soft1851.music.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.soft1851.music.admin.domain.dto.LoginDto;
import com.soft1851.music.admin.domain.entity.SysAdmin;

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
    boolean isLogin(LoginDto loginDto);
//    /**
//     * 根据用户id查询用户信息及角色信息
//     * @param userId
//     * @return
//     */
//    Map<String, Object> getAdminMenuByAdminId(String userId);
//
//
//    /**
//     * 根据用户查id
//     * @return
//     */
//    String getIdByAdmin(SysAdmin sysAdmin);
//
//
//    Map<String, Object> getAdminMenuByAdminId();


    /**
     * 根据name查询Admin信息，其中包含其所有角色
     * @param name
     * @return
     */
    SysAdmin getAdminAndRolesByName(String name);

    /**
     * 根据name查询Admin信息
     * @param name
     * @return
     */
    SysAdmin getAdminByName(String name);

    /**
     * 修改用户信息
     * @param sysAdmin
     */
    int setAdminInfo(SysAdmin sysAdmin);

    /**
     * id
     * @param id
     * @return
     */
    SysAdmin getAdminById(String id);


    int addAdmin(SysAdmin sysAdmin);
}
