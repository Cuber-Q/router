package com.cuber.router.component.impl;

import com.cuber.router.common.ChannelStatus;
import com.cuber.router.component.ChannelStatusManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.cuber.router.common.ChannelStatus.BROKEN;
import static com.cuber.router.common.ChannelStatus.NORMAL;
import static com.cuber.router.common.ChannelStatus.RECOVER;

/**
 * 通道状态管理器
 * 负责存储及更新通道状态、通道权重值
 */
@Component
public class LocalChannelStatusManager implements ChannelStatusManager {

    private ConcurrentHashMap<String, Integer> weightMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, ChannelStatus> statusMap = new ConcurrentHashMap<>();


    public static Boolean legalChange(ChannelStatus from, ChannelStatus to) {
        List<ChannelStatus> availableChange = legalNext(from);
        return availableChange.contains(to);
    }

    public static List<ChannelStatus> legalNext(ChannelStatus from) {
        List<ChannelStatus> result = new ArrayList<>();
        switch (from) {
            case NORMAL:result.add(BROKEN);break;
            case BROKEN:result.add(RECOVER);break;
            case RECOVER:result.add(NORMAL);result.add(BROKEN);break;
        }
        return result;
    }

    @Override
    public void updateWeight(String channelCode, Integer newValue) {
        weightMap.put(channelCode, newValue);
    }

    @Override
    public Boolean updateStatus(String channelCode, ChannelStatus newValue) {
        ChannelStatus oldValue = statusMap.get(channelCode);
        if (!legalChange(oldValue, newValue)) {
            return false;
        }
        statusMap.put(channelCode, newValue);
        return true;
    }

    @Override
    public Integer getWeight(String channelCode) {
        return weightMap.get(channelCode);
    }

    @Override
    public Map<String, Integer> getAllWeights() {
        return new HashMap<>(weightMap);
    }

    @Override
    public ChannelStatus getStatus(String channelCode) {
        return statusMap.get(channelCode);
    }
}
