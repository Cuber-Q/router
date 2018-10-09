package com.cuber.router.component;

import com.cuber.router.entity.Event;

public interface Listener {

    void trigger(Event event);
}
