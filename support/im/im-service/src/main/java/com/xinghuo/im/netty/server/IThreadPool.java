package com.xinghuo.im.netty.server;

import java.util.concurrent.ExecutorService;

public interface IThreadPool {
    ExecutorService getTaskExecutorService();
}
