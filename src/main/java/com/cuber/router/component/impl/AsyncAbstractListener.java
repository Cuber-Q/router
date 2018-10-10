package com.cuber.router.component.impl;

import com.cuber.router.component.AsyncListener;
import com.cuber.router.component.ChannelStatusManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.*;

public abstract class AsyncAbstractListener implements AsyncListener {

    protected Executor executor = new ThreadPoolExecutor(
            10,
            50,
            1000L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());


    @Autowired
    protected ChannelStatusManager channelStatusManager;

}
