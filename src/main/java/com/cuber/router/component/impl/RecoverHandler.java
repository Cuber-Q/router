package com.cuber.router.component.impl;

import com.cuber.router.common.ChannelStatus;
import com.cuber.router.component.Handler;
import com.cuber.router.component.RecoverChain;
import com.cuber.router.component.RecoverSubHandler;
import com.cuber.router.component.eventbus.EventBusCenter;
import com.cuber.router.entity.Event;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("recoverHandler")
public class RecoverHandler implements Handler, InitializingBean {

    @Autowired
    private EventBusCenter eventBusCenter;

    @Autowired
    private RecoverChain chain;

    @Autowired
    @Qualifier("jotRecoverSubHandler")
    private RecoverSubHandler jotSubHandler;

    @Autowired
    @Qualifier("halfOpenRecoverSubHandler")
    private RecoverSubHandler halfOpenSubHandler;

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

    @Override
    public void afterPropertiesSet() throws Exception {
        chain.addLast(jotSubHandler).addLast(halfOpenSubHandler);
    }
}
