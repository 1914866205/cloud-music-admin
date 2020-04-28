package com.soft1851.music.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft1851.music.admin.common.ResultCode;
import com.soft1851.music.admin.domain.entity.Song;
import com.soft1851.music.admin.exception.CustomException;
import com.soft1851.music.admin.mapper.SongMapper;
import com.soft1851.music.admin.service.SongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft1851.music.admin.util.ExcelConsumer;
import com.soft1851.music.admin.util.ExportDataAdapter;
import com.soft1851.music.admin.util.ThreadPool;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
@Slf4j
@Transactional(rollbackFor = RuntimeException.class)
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
                "lyric", "comment_count", "like_count", "play_count", "delete_flag", "update_time", "create_time"
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
     *
     * @param songId
     * @return
     */
    @Override
    public int deleteSongById(String songId) {
//        QueryWrapper<Song> wrapper = new QueryWrapper();
//        wrapper.select("song_id", songId);
        return songMapper.deleteById(songId);
    }

    @SneakyThrows
    @Override
    public void exportData() {
        String excelPath = "D:\\360MoveData\\Users\\lenovo\\Desktop\\数据导出.xlsx";
        //导出excel对象
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(1000);
        //数据缓存
        ExportDataAdapter<Song> exportDataAdapter = new ExportDataAdapter<>();
        //线程同步对象
        CountDownLatch latch = new CountDownLatch(2);
        //启动线程获取数据      生产者
        ThreadPool.getExecutor().submit(() -> produceExportData(exportDataAdapter, latch));
        //启动线程导出数据     消费者
        ThreadPool.getExecutor().submit(() -> new ExcelConsumer<>(Song.class, exportDataAdapter, sxssfWorkbook, latch, "歌曲数据").run());
        latch.await();
        //使用字节流写数据
        OutputStream outputStream = new FileOutputStream(excelPath);
        sxssfWorkbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    private void produceExportData(ExportDataAdapter<Song> exportDataAdapter, CountDownLatch latch) {
        List<Song> songs = songMapper.selectList(null);
//        for (int i = 0; i < 2; i++) {
//            exportDataAdapter.addData(songs.get(i));
//        }
        songs.forEach(exportDataAdapter::addData);
        log.info("数据生成完成");
        //数据生产结束
        latch.countDown();
    }
}
