package com.cuber.router.component.impl;

import com.cuber.router.common.ChannelStatus;
import com.cuber.router.component.Handler;
import com.cuber.router.component.eventbus.EventBusCenter;
import com.cuber.router.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("breakHandler")
public class BreakHandler implements Handler {

    @Autowired
    private EventBusCenter eventBusCenter;

    @Override
    public void handle(String channelCode) {
        try {
            Thread.sleep(5 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        eventBusCenter.postAsync(Event.builder()
                .channelCode(channelCode)
                .timeStamp(System.currentTimeMillis())
                .from(ChannelStatus.BROKEN)
                .to(ChannelStatus.RECOVER)
                .weight(1)
                .build());
    }
}
