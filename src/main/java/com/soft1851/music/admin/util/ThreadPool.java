package com.soft1851.music.admin.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description 线程池类
 * @Author 涛涛
 * @Date 2020/4/27 21:17
 * @Version 1.0
 **/
public class ThreadPool {
    /**
     * 异步线程
     */
    private final static ThreadPoolExecutor executor =
            new ThreadPoolExecutor(20, 100, 10, TimeUnit.MINUTES,
                    new ArrayBlockingQueue<>(2000),
                    r -> new Thread(r, "excelExportThread"),
                    new ThreadPoolExecutor.AbortPolicy());
    public static ThreadPoolExecutor getExecutor(){
        return executor;
    }
}
