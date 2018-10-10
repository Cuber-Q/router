package com.cuber.router.component.impl;

import com.cuber.router.component.Handler;
import com.cuber.router.entity.Event;
import com.cuber.router.entity.NormalEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


@Component("recoverListener")
public class RecoverListener extends AsyncAbstractListener {

    @Autowired
    @Qualifier("recoverHandler")
    private Handler recoverHandler;


    @Override
    public void notifyAsync(Event event) {
        if (!(event instanceof NormalEvent)) {
            return;
        }

        CompletableFuture
                .supplyAsync(() ->
                        channelStatusManager.updateStatus(event.getChannelCode(),event.getTo()), executor)
                .thenAccept((result) -> {
                    if (result) {
                        channelStatusManager.updateWeight(event.getChannelCode(), 5);
                    }})
                .thenRun(() ->
                        recoverHandler.handle(event.getChannelCode()));
    }
}
