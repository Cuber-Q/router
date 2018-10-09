package com.cuber.router.component.impl;

import com.cuber.router.common.ChannelStatus;
import com.cuber.router.component.ChannelStatusManager;
import com.cuber.router.component.Router;
import com.cuber.router.component.Strategy;
import com.cuber.router.entity.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DefaultRouter implements Router {

    @Autowired
    @Qualifier("weightedRandomStrategy")
    private Strategy strategy;

    @Autowired
    private ChannelStatusManager channelStatusManager;

    @Override
    public Channel routeChannel() {
        return strategy.fetch();
    }

    @Override
    public Boolean isAvailable(String channelCode) {
        return channelStatusManager.getStatus(channelCode) != ChannelStatus.BROKEN;
    }
}
