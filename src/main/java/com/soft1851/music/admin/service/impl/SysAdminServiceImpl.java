package com.soft1851.music.admin.service.impl;

import com.soft1851.music.admin.common.ResultCode;
import com.soft1851.music.admin.domain.dto.LoginDto;
import com.soft1851.music.admin.domain.entity.SysAdmin;
import com.soft1851.music.admin.exception.CustomException;
import com.soft1851.music.admin.mapper.SysAdminMapper;
import com.soft1851.music.admin.service.SysAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft1851.music.admin.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SysAdminServiceImpl extends ServiceImpl<SysAdminMapper, SysAdmin> implements SysAdminService {
    @Resource
    private SysAdminMapper sysAdminMapper;

//    @Resource
//    private SysMenuMapper menuMapper;


    @Override
    public boolean isLogin(LoginDto loginDto) {
        SysAdmin admin = sysAdminMapper.getSysAdminByName(loginDto.getUsername());
        if (admin!=null){
            String password = Md5Util.getMd5(loginDto.getPassword(), true, 32);
            if (admin.getPassword().equals(password)) {
                return true;
            }else {
                log.info("密码错误");
                throw new CustomException("密码错误", ResultCode.USER_PASSWORD_ERROR);
            }
        }else {
            log.error("用户名不存在");
            throw new CustomException("用户名不存在", ResultCode.USER_NOT_FOUND);
        }

//        log.info(loginDto.toString());
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", loginDto.getUsername());
//        List<SysAdmin> list = sysAdminMapper.selectByMap(map);
//        log.info(list.toString());
//        String password= Md5Util.getMd5(loginDto.getPassword(), true, 32);
//        if (password.equals(list.get(0).getPassword())) {
//            return true;
//        }
//        return false;
    }

    @Override
    public SysAdmin getAdminAndRolesByName(String name) {
        return sysAdminMapper.selectByName(name);
    }

//    @Override
//    public Map<String, Object> getAdminMenuByAdminId(String userId) {
//       //定义一个map
//        Map<String, Object> map = new HashMap<>();
//        //得到用户信息及角色信息
//        List<Map<String, Object>> admin = sysAdminMapper.getAdminMenuByAdminId(userId);
//        //取出用户的角色Id
//        log.info(String.valueOf(admin));
//        int roleId = Integer.parseInt(admin.get(0).get("role_id").toString());
//
//        //通过角色Id得到该用户的权限
//        List<Map<String, Object>> maps = menuMapper.getParentMenuByRoleId(roleId);
//        //移除多于字段
//        for (Map<String, Object> map1 : maps) {
//            map1.remove("role_id");
//            map1.remove("menu_id");
//        }
//        //将得到的两个集合写入map,返回数据
//        map.put("user", admin);
//        map.put("permission", maps);
//        return map;
//    }
//
//    @Override
//    public String getIdByAdmin(SysAdmin sysAdmin) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("name", sysAdmin.getName());
//        params.put("password", sysAdmin.getPassword());
//        return sysAdminMapper.selectByMap(params).get(0).getId();
//    }

//    @Override
//    public String sign(LoginDto loginDto) {
//        if(login(loginDto)) {
//            //验证通过
//            SysAdmin sysAdmin = new SysAdmin();
//            sysAdmin.setName(loginDto.getUsername());
//            sysAdmin.setPassword(Md5Util.getMd5(loginDto.getPassword(), true, 32));
//            String userId=getIdByAdmin(sysAdmin);
//            //用userId和role来生成token，并指定过期时间
//            Date expiresAt = new Date(System.currentTimeMillis() + 600L * 1000L);
//            String token = JwtTokenUtil.getToken(userId,getAdminMenuByAdminId(userId).toString(), expiresAt);
//            //记录token
//            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//            assert sra != null;
//            //获取HttpServletResponse对象
//            HttpServletResponse response = sra.getResponse();
//            assert response != null;
//            //将token放在响应头返回，此处需在跨域配置中配置allowedHeaders和allowedExposedHeaders
//            response.setHeader("Authorization", token);
//            // 返回登录成功
//            return "登录成功";
//        }else {
//            //密码错误,用户不存在
//            return "账号或密码错误";
//        }
//    }

//    @Override
//    public Map<String, Object> getAdminMenuByAdminId() {
//        log.info("###  查询当前角色的权限 ###");
//        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        assert sra != null;
//        HttpServletRequest request = sra.getRequest();
//        String token = request.getHeader("Authorization");
//        String userId = JwtTokenUtil.getUserId(token);
//        Map<String, Object> adminMenuByAdminId = getAdminMenuByAdminId(userId);
//        return adminMenuByAdminId;
//    }
}
