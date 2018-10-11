package com.cuber.router.component.impl;

import com.cuber.router.common.ChannelStatus;
import com.cuber.router.component.Handler;
import com.cuber.router.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component("breakListener")
public class BreakListener extends AsyncAbstractListener {

    @Autowired
    @Qualifier("breakHandler")
    private Handler breakHandler;

    @Override
    public void notifyAsync(Event event) {
        if (event.getTo() != ChannelStatus.BROKEN) {
            return;
        }

        CompletableFuture
                .supplyAsync(() ->
                        channelStatusManager.updateStatus(event.getChannelCode(),event.getTo()), executor)
                .thenAccept((result) -> {
                    if (result) {
                        channelStatusManager.updateWeight(event.getChannelCode(), 1);
                    }})
                .thenRun(() ->
                        breakHandler.handle(event.getChannelCode()));
    }
}
