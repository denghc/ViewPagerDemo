package com.example.myviewpagertest.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ImageThreadPool {
	private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 5, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20), new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void execute(Runnable task) {
        threadPool.execute(task);
    }
}
