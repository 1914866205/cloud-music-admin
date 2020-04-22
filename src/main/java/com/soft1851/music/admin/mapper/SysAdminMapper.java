package com.soft1851.music.admin.mapper;

import com.soft1851.music.admin.entity.SysAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
public interface SysAdminMapper extends BaseMapper<SysAdmin> {
    /**
     * 根据用户id查询用户信息及角色信息
     * @param userId
     * @return
     */
    List<Map<String, Object>> getAdminMenuByAdminId(String userId);


}
