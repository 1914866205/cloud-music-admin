package com.soft1851.music.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft1851.music.admin.common.ResultCode;
import com.soft1851.music.admin.entity.Song;
import com.soft1851.music.admin.entity.SongList;
import com.soft1851.music.admin.exception.CustomException;
import com.soft1851.music.admin.mapper.SongMapper;
import com.soft1851.music.admin.service.SongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {
    @Resource
    private SongMapper songMapper;
    /**
     * 查询所有歌曲
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> selectAll() {
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        //因为是根据表自动生成的类，所以不需要知道默认表名
        wrapper.select("song_id", "song_name", "sort_id", "singer", "duration", "thumbnail", "url",
                "lyric","comment_count","like_count","play_count","delete_flag","update_time","create_time"
        ).orderByDesc("sort_id");
        List<Map<String, Object>> songLists = songMapper.selectMaps(wrapper);
        if (songLists != null) {
            return songLists;
        }
        throw new CustomException("歌曲查询异常", ResultCode.DATABASE_ERROR);
    }

    /**
     * 分页查询
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public List<Song> getByPage(int current, int size) {
        Page<Song> page = new Page<>(current, size);
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        //翻页查询
        IPage<Song> iPage = songMapper.selectPage(page, wrapper);
        return iPage.getRecords();
    }


    /**
     * 根据id删除歌曲
     * @param songId
     * @return
     */
    @Override
    public int deleteSongById(String songId) {
//        QueryWrapper<Song> wrapper = new QueryWrapper();
//        wrapper.select("song_id", songId);
        return songMapper.deleteById(songId);
    }


}
