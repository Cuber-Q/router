package com.cuber.router.component;

import com.cuber.router.common.ChannelStatus;
import com.cuber.router.entity.Event;

import java.util.Map;

public interface ChannelStatusManager {

    void updateWeight(String channelCode, Integer newValue);

    Boolean updateStatus(String channelCode, ChannelStatus newValue);

    Integer getWeight(String channelCode);

    Map<String, Integer> getAllWeights();

    ChannelStatus getStatus(String channelCode);

    Boolean atomUpdate(Event event);
}
