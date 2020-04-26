package com.soft1851.music.admin.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SongListServiceTest {
    @Resource
    private SongListService songListService;
    @Test
    void selectAll() {
        System.out.println(songListService.selectAll());
    }

    @Test
    void blurSelect() {
        System.out.println(songListService.blurSelect("欧美"));
    }
}