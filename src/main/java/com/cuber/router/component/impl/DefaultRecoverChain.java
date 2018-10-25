package com.cuber.router.component.impl;

import com.cuber.router.common.ChannelStatus;
import com.cuber.router.component.RecoverChain;
import com.cuber.router.component.RecoverSubHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        return null;
    }
}
