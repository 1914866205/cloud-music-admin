package com.soft1851.music.admin.controller;


import com.soft1851.music.admin.domain.entity.Song;
import com.soft1851.music.admin.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ntt
 * @since 2020-04-21
 */
@Slf4j
@RestController
@RequestMapping("/song")
public class SongController {
    @Resource
    private SongService songService;
    /**
     * 查询所有歌曲
     *
     * @return
     */
    @GetMapping("/list")
    public List<Map<String, Object>> selectAll() {
        log.info("*********");
        log.info("查询所有歌曲");
        log.info("---------");
        return songService.selectAll();
    }


    /**
     * 分页查询
     *
     * @param currentPage
     * @param size
     * @return
     */
    @GetMapping("/page")
    List<Song> getByPage(@Param("currentPage") int currentPage, @Param("size") int size) {
        log.info("*********");
        log.info("查询分页");
        log.info("---------");
        return songService.getByPage(currentPage, size);
    }
    /**
     * 根据id删除
     * @param songId
     * @return
     */
    @GetMapping("/deleteById")
    int deleteBySongId(@Param("songId") String songId) {
        log.info("*********");
        log.info("进入删除");
        log.info("---------");
        return songService.deleteSongById(songId);
    }
}
