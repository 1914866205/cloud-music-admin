package com.soft1851.music.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.soft1851.music.admin.annotation.ExcelVoAttribute;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("song")
public class Song extends Model<Song> {

    private static final long serialVersionUID = 1L;

    /**
     * 歌曲id
     */
    @TableId("song_id")
    @ExcelVoAttribute(name="歌曲ID",column = 0)
    private String songId;

    /**
     * 歌曲名称
     */
    @TableField("song_name")
    @ExcelVoAttribute(name="歌曲名称",column = 1)
    private String songName;

    /**
     * 排序id
     */
    @TableField("sort_id")
    private String sortId;

    /**
     * 歌手
     */
    @TableField("singer")
    @ExcelVoAttribute(name = "歌手姓名",column = 2)
    private String singer;

    /**
     * 时长
     */
    @TableField("duration")
    @ExcelVoAttribute(name="歌曲时长",column = 3)
    private String duration;

    /**
     * 封面图
     */
    @TableField("thumbnail")
    @ExcelVoAttribute(name="歌曲封面图",column = 4)
    private String thumbnail;

    /**
     * 歌曲地址
     */
    @TableField("url")
    @ExcelVoAttribute(name="歌曲链接",column = 5)
    private String url;

    /**
     * 歌词
     */
    @TableField("lyric")
    private String lyric;

    /**
     * 评论量
     */
    @TableField("comment_count")
    private Integer commentCount;

    /**
     * 收藏量
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 播放量
     */
    @TableField("play_count")
    @ExcelVoAttribute(name="播放量",column = 6,isNumber = true)
    private Integer playCount;

    /**
     * 删除标志
     */
    @TableField("delete_flag")
    private String deleteFlag;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @ExcelVoAttribute(name="创建时间",column = 7,isDateTime = true)
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.songId;
    }

}
