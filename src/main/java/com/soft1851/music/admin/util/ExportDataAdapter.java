package com.soft1851.music.admin.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description TODO
 * @Author 涛涛
 * @Date 2020/4/27 18:43
 * @Version 1.0
 **/
public class ExportDataAdapter<T> {
    /**
     * 默认队列大小
     */
    private static Integer DEFAULT_SIZE = 1000;


    private BlockingQueue<T> resourceQueue = null;

    public ExportDataAdapter() {
        this.resourceQueue = new LinkedBlockingDeque<>(DEFAULT_SIZE);
    }

    /**
     * 添加数据
     *
     * @param data
     */
    public void addData(T data) {
        try {
            resourceQueue.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取剩余数据的数量
     *
     * @return
     */
    public Integer getDataSize() {
        return resourceQueue.size();
    }

    /**
     * 从队列中获取数据
     *
     * @return
     */
    public T getData() {
        try {
            return resourceQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

