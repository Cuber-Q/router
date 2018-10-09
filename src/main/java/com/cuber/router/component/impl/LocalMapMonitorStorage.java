package com.cuber.router.component.impl;

import com.cuber.router.component.MonitorStorage;
import com.cuber.router.entity.MonitorData;
import com.cuber.router.entity.MonitorMetaData;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

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
                        String key = String.valueOf(metaData.getTimestamp() % DateUtils.MILLIS_PER_SECOND);
                        MonitorMetaData previousData = localMap.putIfAbsent(key, metaData);
                        if (null != previousData) {
                            previousData.merge(metaData);
                        }
                    });

        });

    }

    @Override
    public List<MonitorMetaData> read(int range) {
        return null;
    }

}
