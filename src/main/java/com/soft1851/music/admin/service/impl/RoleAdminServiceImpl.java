package com.soft1851.music.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft1851.music.admin.domain.entity.RoleAdmin;
import com.soft1851.music.admin.mapper.RoleAdminMapper;
import com.soft1851.music.admin.service.RoleAdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
@Service
public class RoleAdminServiceImpl extends ServiceImpl<RoleAdminMapper, RoleAdmin> implements RoleAdminService {
    @Resource
    private RoleAdminMapper roleAdminMapper;
    @Override
    public int addRoleAdmin(RoleAdmin roleAdmin) {
        return roleAdminMapper.insert(roleAdmin);
    }
}
