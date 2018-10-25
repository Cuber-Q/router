package com.cuber.router.component.eventbus;

import com.cuber.router.common.ChannelStatus;
import com.cuber.router.component.ChannelStatusManager;
import com.cuber.router.component.Handler;
import com.cuber.router.entity.Event;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
@EventBusListener
public class ChannelStatusListener {

    private Executor executor = new ThreadPoolExecutor(
            10,
            50,
            1000L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    @Autowired
    private ChannelStatusManager channelStatusManager;

    @Autowired
    @Qualifier("normalHandler")
    private Handler normalHandler;

    @Autowired
    @Qualifier("breakHandler")
    private Handler breakHandler;

    @Autowired
    @Qualifier("recoverHandler")
    private Handler recoverHandler;


    @Subscribe
    public void onNormal(Event event) {
        if (event.getTo() != ChannelStatus.NORMAL) {
            return;
        }

        event.setWeight(100);
        CompletableFuture.supplyAsync(() -> channelStatusManager.atomUpdate(event))
                .thenRun(() -> normalHandler.handle(event.getChannelCode()));
    }

    @Subscribe
    public void onBreak(Event event) {
        if (event.getTo() != ChannelStatus.BROKEN) {
            return;
        }

        event.setWeight(1);
        CompletableFuture.supplyAsync(() -> channelStatusManager.atomUpdate(event))
                .thenRun(() -> breakHandler.handle(event.getChannelCode()));
    }

    @Subscribe
    public void onRecover(Event event) {
        if (event.getTo() != ChannelStatus.RECOVER) {
            return;
        }

        event.setWeight(5);
        CompletableFuture.supplyAsync(() -> channelStatusManager.atomUpdate(event))
                .thenRun(() -> recoverHandler.handle(event.getChannelCode()));
    }
}
