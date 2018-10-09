package com.cuber.router.component;

import com.cuber.router.entity.MonitorData;
import com.cuber.router.entity.MonitorMetaData;

import java.util.List;

public interface MonitorStorage {

    void writeAsync(MonitorData data);

    List<MonitorMetaData> read(int range);

}
