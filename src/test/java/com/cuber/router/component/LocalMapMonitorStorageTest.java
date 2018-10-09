package com.cuber.router.component;

import com.cuber.router.entity.MonitorDataBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class LocalMapMonitorStorageTest {

    @Autowired
    private MonitorStorage storage;

    private String channelCode = "xxx";

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void afterPropertiesSet() {
    }

    @org.junit.Test
    public void writeAsync() {
        storage.writeAsync(MonitorDataBuilder.buildASuccess(channelCode));
    }

    @org.junit.Test
    public void read() {
    }
}