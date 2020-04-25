package com.soft1851.music.admin.service;

import com.soft1851.music.admin.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 根据角色id获取角色信息（基础信息和该角色所有菜单）
     * @param roleId
     * @return
     */
    Map<String,Object> selectRoleById(int roleId);

    /**
     * 检查roleId是否在roles中存在
     * @param roles
     * @param roleId
     * @return boolean
     */
    boolean checkRole(List<SysRole> roles, int roleId);
}
