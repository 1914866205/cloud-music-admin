package com.soft1851.music.admin.service.impl;

import com.soft1851.music.admin.entity.SysMenu;
import com.soft1851.music.admin.entity.SysRole;
import com.soft1851.music.admin.mapper.SysRoleMapper;
import com.soft1851.music.admin.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft1851.music.admin.util.TreeBuilder;
import com.soft1851.music.admin.util.TreeNode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 根据角色id查询角色信息(基础信息和该角色所有菜单)
     *
     * @param roleId
     * @return
     */
    @Override
    public Map<String, Object> selectRoleById(int roleId) {
        SysRole sysRole = sysRoleMapper.selectRoleById(roleId);
        Map<String, Object> map = new TreeMap<>();
        map.put("roleName", sysRole.getRoleName());
        List<TreeNode> list = new ArrayList<>();
        List<SysMenu> menus = sysRole.getMenus();
        for (SysMenu sysMenu : menus) {
            if (sysMenu.getParentId() == 0) {
                TreeNode treeNode = new TreeNode(sysMenu.getId(), 0, sysMenu.getType(), sysMenu.getTitle(), sysMenu.getIcon(), sysMenu.getPath(), sysMenu.getSort(), new ArrayList<>());
                list.add(treeNode);
            } else {
                TreeNode treeNode = new TreeNode(sysMenu.getId(), sysMenu.getParentId(), sysMenu.getType(), sysMenu.getTitle(), sysMenu.getIcon(), sysMenu.getPath(), sysMenu.getSort(), new ArrayList<>());
                list.add(treeNode);
            }
        }

        List<TreeNode> trees = TreeBuilder.buildTreeByLoop(list);
        map.put("menus", trees);
        return map;
    }

    /**
     * 检查roleId是否在roles中存在
     *
     * @param roles
     * @param roldId
     * @return
     */
    @Override
    public boolean checkRole(List<SysRole> roles, int roldId) {
        boolean flag = false;
        for (SysRole role : roles) {
            if (roldId == role.getRoleId()) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
