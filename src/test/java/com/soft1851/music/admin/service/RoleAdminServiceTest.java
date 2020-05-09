package com.soft1851.music.admin.service;

import com.soft1851.music.admin.domain.entity.RoleAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class RoleAdminServiceTest {
    @Resource
    private RoleAdminService roleAdminService;
    @Test
    void test() {
        roleAdminService.addRoleAdmin(RoleAdmin.builder().roleId("1").adminId("22").build());
    }
}