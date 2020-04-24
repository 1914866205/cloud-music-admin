package com.soft1851.music.admin.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description TODO
 * @Author 涛涛
 * @Date 2020/4/24 9:46
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TreeNode {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private String title;
    private String icon;
    private String path;
    private Integer sort;
    private List<TreeNode> subMenus;

    public TreeNode(Integer id, Integer parentId, Integer type, String title, String icon, String path, Integer sort) {
        this.id = id;
        this.parentId = parentId;
        this.type = type;
        this.title = title;
        this.icon = icon;
        this.path = path;
        this.sort = sort;
    }
}
