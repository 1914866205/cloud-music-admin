package com.soft1851.music.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft1851.music.admin.common.ResultCode;
import com.soft1851.music.admin.domain.entity.SongList;
import com.soft1851.music.admin.exception.CustomException;
import com.soft1851.music.admin.mapper.SongListMapper;
import com.soft1851.music.admin.service.SongListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {
    @Resource
    private SongListMapper songListMapper;

    /**
     * 查询所有歌单
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> selectAll() {
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        //因为是根据表自动生成的类，所以不需要知道默认表名
        wrapper.select("song_list_id", "song_list_name", "thumbnail", "play_counts", "type", "song_count", "create_time")
                .orderByDesc("play_counts");
        List<Map<String, Object>> songLists = songListMapper.selectMaps(wrapper);
        if (songLists != null) {
            return songLists;
        }
        throw new CustomException("歌单查询异常", ResultCode.DATABASE_ERROR);
    }

    /**
     * 根据type字段进行分组，将每种类型的所有歌手作为该类型的子菜单
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getByType() {
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        //根据type字段进行分组，play_counts降序排序
        wrapper.select("type").groupBy("type").orderByDesc("play_counts");
        List<Map<String, Object>> maps = songListMapper.selectMaps(wrapper);
        for (Map<String, Object> map : maps) {
            if ("0".equals(map.get("type"))) {
                list().remove(map);
            } else {
                QueryWrapper<SongList> wrapper1 = new QueryWrapper<>();
                //根据父类的type类型查询属于该类型的数据
                //              排序条件                            键名          父类型的键名
                wrapper1.orderByDesc("play_counts").eq("type", map.get("type"));
                //键是列名
                List<Map<String, Object>> songLists = songListMapper.selectMaps(wrapper1);
                map.put("child", songLists);
            }
        }
        return maps;
    }

    /**
     * 分页查询
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public List<SongList> getByPage(int current, int size) {
        Page<SongList> page = new Page<>(current, size);
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        //翻页查询
        IPage<SongList> iPage = songListMapper.selectPage(page, wrapper);
        return iPage.getRecords();
    }


    /**
     * 模糊查询
     *
     * @param field
     * @return
     */
    @Override
    public List<SongList> blurSelect(String field) {
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
//        System.out.println("***************"+field);
        //    or()   不加的话默认为and
        wrapper.like("song_list_name", field).or().like("type", field);
        ;
//        wrapper.select("song_list_id", "song_list_name", "thumbnail", "play_counts", "type", "song_count", "create_time")
//                .like("song_list_name", field).or().like("type", field);
        List<SongList> songLists = songListMapper.selectList(wrapper);
        return songLists;

    }
    /**
     * 根据id删除歌单
     * @param songListId
     * @return
     */
    @Override
    public int deleteSongListById(String songListId) {
        return songListMapper.deleteById(songListId);
    }
}
