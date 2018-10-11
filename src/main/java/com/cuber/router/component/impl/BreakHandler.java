package com.cuber.router.component.impl;

import com.cuber.router.common.ChannelStatus;
import com.cuber.router.component.AsyncListener;
import com.cuber.router.component.Handler;
import com.cuber.router.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("breakHandler")
public class BreakHandler implements Handler {

    @Autowired
    @Qualifier("recoverListener")
    private AsyncListener listener;

    @Override
    public void handle(String channelCode) {
        try {
            Thread.sleep(5 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listener.notifyAsync(Event.builder()
                .channelCode(channelCode)
                .timeStamp(System.currentTimeMillis())
                .from(ChannelStatus.BROKEN)
                .to(ChannelStatus.RECOVER)
                .build()
        );
    }
}
