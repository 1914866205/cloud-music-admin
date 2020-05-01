package com.soft1851.music.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Description TODO
 * @Author 涛涛
 * @Date 2020/4/21 14:15
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @Size(max=20,min=6)
    @NotNull(message = "username不能为空")
    private String username;
    @Size(max=20,min=6)
    @NotNull(message = "password不能为空")
    private String password;
    @Size(max = 4,min=4)
    @NotNull(message = "checkCode不能为空")
    private String checkCode;
    @NotNull(message = "userIp不能为空")
    private String userIp;
}
