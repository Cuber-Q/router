package com.cuber.router.component.impl;

import com.cuber.router.component.AsyncListener;
import com.cuber.router.component.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("normalHandler")
public class NormalHandler implements Handler {

    @Autowired
    private List<AsyncListener> listener;


    // Scheduled read monitor-metadata in storage
    // and decide whether to BREAK the channel or do nothing
    // dependent on calculated result.
    @Override
    public void handle(String channelCode) {

    }
}
