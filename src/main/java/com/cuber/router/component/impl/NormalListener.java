package com.cuber.router.component.impl;

import com.cuber.router.component.Handler;
import com.cuber.router.component.Listener;
import com.cuber.router.entity.Event;
import com.cuber.router.entity.NormalEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class NormalListener implements Listener {

    @Autowired
    @Qualifier("normalHandler")

    private Handler normalHandler;

    @Override
    public void trigger(Event event) {
        if (event instanceof NormalEvent) {
            // call handler
//            normalHandler.handle();
        }
    }
}
