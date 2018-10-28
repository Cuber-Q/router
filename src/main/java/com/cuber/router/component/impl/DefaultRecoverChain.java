package com.cuber.router.component.impl;

import com.cuber.router.common.ChannelStatus;
import com.cuber.router.component.RecoverChain;
import com.cuber.router.component.RecoverSubHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class DefaultRecoverChain implements RecoverChain {

    private List<RecoverSubHandler> subHandlerList = new ArrayList<>();

    @Override
    public RecoverChain addLast(RecoverSubHandler subHandler) {
        subHandlerList.add(subHandler);
        return this;
    }

    @Override
    public RecoverChain getChain() {
        return this;
    }

    @Override
    public ChannelStatus doChainedRecover(String channelCode) {
        Iterator<RecoverSubHandler> it = subHandlerList.iterator();
        while (it.hasNext()) {
            ChannelStatus status = it.next().doRecover(channelCode);
            if (status == ChannelStatus.BROKEN) {
                return status;
            }
            if (status == ChannelStatus.NORMAL && it.hasNext()) {
                continue;
            }
            if (status == ChannelStatus.NORMAL) {
                return status;
            }
        }
        return ChannelStatus.NORMAL;
    }
}
