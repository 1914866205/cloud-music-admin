package com.soft1851.music.admin.service;

import com.soft1851.music.admin.domain.entity.Song;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
public interface SongService extends IService<Song> {

    /**
     * 查询所有歌曲
     * @return
     */
    List<Map<String,Object>> selectAll();

//    /**
//     * 根据type字段进行分组，将每种类型的所有歌手作为该类型的子菜单
//     * @return
//     */
//    List<Map<String,Object>> getByType();

    /**
     * 分页查询
     * @param current
     * @param size
     * @return
     */
    List<Song> getByPage(int current, int size);


//    /**
//     * 模糊查询
//     * @param field
//     * @return
//     */
//    List<SongList> blurSelect(String field);

    /**
     * 根据id删除歌曲
     * @param songId
     * @return
     */
    int deleteSongById(String songId);

    /**
     * 导出数据
     */
    void exportData();
}
