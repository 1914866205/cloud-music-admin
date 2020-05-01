package com.soft1851.music.admin.mapper;

import com.soft1851.music.admin.domain.entity.SysAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
public interface SysAdminMapper extends BaseMapper<SysAdmin> {
//    /**
//     * 根据用户id查询用户信息及角色信息
//     * @param userId
//     * @return
//     */
//    List<Map<String, Object>> getAdminMenuByAdminId(String userId);

    /**
     * 根据name查询管理员信息，包含其所有角色
     * @param name
     * @return
     */
    SysAdmin selectByName(@Param("name") String name);


    /**
     * 根据name查询admin基础信息，用于个人信息，登录等
     * 避开其中的List<SysRole>属性，因为它没有映射字段
     * @param name
     * @return
     */
    @Select("SELECT * FROM sys_admin WHERE name = #{name}")
    SysAdmin getSysAdminByName(@Param("name") String name);

    /**
     * 设置用户信息
     * @param sysAdmin
     * @return
     */
    int setSysAdmin(SysAdmin sysAdmin);

    @Select("SELECT id,name,password,salt,avatar,create_time,update_time,status FROM sys_admin WHERE id = #{id}")
    SysAdmin selectByAdminId(@Param("id") String id);

}
