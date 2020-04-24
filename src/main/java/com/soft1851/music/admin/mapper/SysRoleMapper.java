package com.soft1851.music.admin.mapper;

import com.soft1851.music.admin.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据id查询角色，包含其所有菜单
     * @param roleId
     * @return
     */
    SysRole selectRoleById(int roleId);
}
