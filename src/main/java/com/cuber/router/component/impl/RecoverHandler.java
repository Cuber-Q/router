package com.cuber.router.component.impl;

import com.cuber.router.common.ChannelStatus;
import com.cuber.router.component.Handler;
import com.cuber.router.component.RecoverChain;
import com.cuber.router.component.eventbus.EventBusCenter;
import com.cuber.router.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("recoverHandler")
public class RecoverHandler implements Handler {

    @Autowired
    private EventBusCenter eventBusCenter;

    @Autowired
    private RecoverChain chain;

    @Override
    public void handle(String channelCode) {
        ChannelStatus status = chain.doChainedRecover(channelCode);
        eventBusCenter.postAsync(Event.builder()
                .channelCode(channelCode)
                .timeStamp(System.currentTimeMillis())
                .from(ChannelStatus.RECOVER)
                .to(status)
                .build()
        );
    }
}
