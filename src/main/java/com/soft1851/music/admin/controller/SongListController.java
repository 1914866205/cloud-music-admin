package com.soft1851.music.admin.controller;


import com.soft1851.music.admin.domain.entity.SongList;
import com.soft1851.music.admin.service.SongListService;
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
@RequestMapping(value = "/songList")
public class SongListController {

    @Resource
    private SongListService songListService;
    /**
     * 查询所有歌单
     *
     * @return
     */
    @GetMapping("/list")
    public List<Map<String, Object>> selectAll() {
        log.info("*********");
        log.info("查询所有歌单");
        log.info("---------");
        return songListService.selectAll();
    }

    /**
     * 根据type字段进行分组，将每种类型的所有歌手作为该类型的子菜单
     *
     * @return
     */
    @GetMapping("/type")
    public List<Map<String, Object>> getByType() {

        log.info("*********");
        log.info("查询类型");
        log.info("---------");
        return songListService.getByType();
    }

    /**
     * 分页查询
     *
     * @param currentPage
     * @param size
     * @return
     */
    @GetMapping("/page")
    List<SongList> getByPage(@Param("currentPage") int currentPage, @Param("size") int size) {
        log.info("*********");
        log.info("查询分页");
        log.info("---------");
        return songListService.getByPage(currentPage, size);
    }


    /**
     * 模糊查询
     *
     * @param field
     * @return
     */
    @GetMapping("/blur")
    List<SongList> blurSelect(@Param("field") String field) {
        log.info("*********");
        log.info("模糊查询");
        log.info("---------");
        return songListService.blurSelect(field);
    }

    /**
     * 根据id删除
     * @param songListId
     * @return
     */
    @GetMapping("/deleteById")
    int deleteBySongId(@Param("songListId") String songListId) {
        log.info("*********");
        log.info("进入删除");
        log.info("---------");
        return songListService.deleteSongListById(songListId);
    }
}
