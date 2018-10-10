package com.cuber.router.component;

import com.cuber.router.entity.Event;

/**
 * 事件监听器
 */
public interface AsyncListener {

    /**
     * 事件发生时异步通知对应的处理器
     * @param event
     */
    void notifyAsync(Event event);
}
