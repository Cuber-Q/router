package com.cuber.router.component.impl;

import com.cuber.router.common.ChannelStatus;
import com.cuber.router.component.MonitorStorage;
import com.cuber.router.component.RecoverSubHandler;
import com.cuber.router.entity.MonitorMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("jotRecoverSubHandler")
public class JotRecoverSubHandler implements RecoverSubHandler {

    @Autowired
    private MonitorStorage storage;

    @Override
    public ChannelStatus doRecover(String channelCode) {
        double successRate = storage.readSuccessRate(channelCode, 1);
        List<MonitorMetaData> list = storage.read(channelCode, 1);
        int total = list.stream().mapToInt(MonitorMetaData::getTotal).sum();
        if (successRate > 0.9 && total > 5) {
            return ChannelStatus.NORMAL;
        }
        if (successRate < 0.5 && total > 5) {
            return ChannelStatus.BROKEN;
        }
        try {
            Thread.sleep(1000L);
            doRecover(channelCode);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
        return null;
    }
}
