package com.cuber.router.component.impl;

import com.cuber.router.common.ChannelStatus;
import com.cuber.router.component.Handler;
import com.cuber.router.component.MonitorStorage;
import com.cuber.router.component.eventbus.EventBusCenter;
import com.cuber.router.entity.Event;
import com.cuber.router.entity.MonitorMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component("normalHandler")
public class NormalHandler implements Handler {

    @Autowired
    private EventBusCenter eventBusCenter;

    @Autowired
    private MonitorStorage monitorStorage;

    // Scheduled read monitor-metadata in storage
    // and decide whether to BREAK the channel or do nothing
    // dependent on calculated result.
    @Override
    public void handle(String channelCode) {
        List<MonitorMetaData> dataList = monitorStorage.read(channelCode,5);
        AtomicInteger sum = new AtomicInteger();
        AtomicInteger failSum = new AtomicInteger();
        dataList.forEach((data) -> {
            sum.addAndGet(data.getTotal());
            failSum.addAndGet(data.getFail());
        });

        if (sum.get() == 0) {
            return;
        }

        // fail rate > 0.5, trigger BREAK_EVENT
        if ((double) failSum.get() / (double) sum.get() > 0.5) {
            eventBusCenter.postAsync(Event.builder()
                    .channelCode(channelCode)
                    .timeStamp(System.currentTimeMillis())
                    .from(ChannelStatus.NORMAL)
                    .to(ChannelStatus.BROKEN)
                    .build()
            );
        }
    }
}
