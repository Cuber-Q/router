package com.cuber.router.entity;

import java.util.Arrays;

public class MonitorDataBuilder {

    public static MonitorData buildASuccess(String channelCode) {
        MonitorMetaData metaData = MonitorMetaData.builder()
                .total(1)
                .success(1)
                .fail(0)
                .timestamp(System.currentTimeMillis())
                .build();

        return MonitorData.builder()
                .channelCode(channelCode)
                .metaDataList(Arrays.asList(metaData))
                .build();
    }
}
