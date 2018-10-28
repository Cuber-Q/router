package com.cuber.router.component.impl;

import com.cuber.router.component.MonitorStorage;
import com.cuber.router.entity.MonitorData;
import com.cuber.router.entity.MonitorMetaData;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LocalMapMonitorStorage implements MonitorStorage, InitializingBean {

    private ConcurrentHashMap<String, MonitorMetaData> localMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        localMap = new ConcurrentHashMap<>();
    }

    @Override
    public void writeAsync(MonitorData data) {
        CompletableFuture.runAsync(()-> {
            data.getMetaDataList()
                    .forEach((metaData) ->{
                        String key = buildKey(metaData.getTimestamp());
                        MonitorMetaData previousData = localMap.putIfAbsent(key, metaData);
                        if (null != previousData) {
                            previousData.merge(metaData);
                        }
                    });

        });

    }

    @Override
    public List<MonitorMetaData> read(String channelCode, int range) {
        List<String> keys = buildKeyList(System.currentTimeMillis(), range);
        List<MonitorMetaData> result = new ArrayList<>(keys.size());
        keys.forEach((key) -> result.add(localMap.get(key)));
        return result;
    }

    @Override
    public double readSuccessRate(String channelCode, int range) {
        List<MonitorMetaData> list = read(channelCode, range);
        double sum = 0;
        double success = 0;
        double successRate = 1;
        sum = list.stream().mapToDouble(MonitorMetaData::getTotal).sum();
        success = list.stream().mapToDouble(MonitorMetaData::getSuccess).sum();
        if (sum != 0){
            successRate = success / sum;
        }
        return successRate;
    }

    private String buildKey(Long timestamp) {
        return String.valueOf(timestamp % DateUtils.MILLIS_PER_SECOND);
    }

    private List<String> buildKeyList(Long timestamp, int range) {
        List<String> keys = new ArrayList<>();
        while (range > 0) {
            keys.add(buildKey(timestamp));
            timestamp -= DateUtils.MILLIS_PER_SECOND;
            range --;
        }
        return keys;
    }
}
