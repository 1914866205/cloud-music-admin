package com.soft1851.music.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * 
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_admin")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysAdmin extends Model<SysAdmin> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 用户名
     */
    @Size(min = 4,max = 20)
    @NotNull
    @TableField("name")
    private String name;

    /**
     * 密码
     */
    //加上此注解，不会被客户端看到
//    @JsonIgnore
    @Size(min = 4,max = 50)
    @NotNull
    @TableField("password")
    private String password;

    /**
     * 加密盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private  LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 账户状态：0 禁用 1 启用
     */
    @TableField("status")
    private Integer status;

    /**
     * github账号
     */
    @TableField("github_name")
    private String githubName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * 权限集合
     * 该注解用于数据库不存在的属性
     */
//    @TableField(exist = false)
    private List<SysRole> roles;
}
