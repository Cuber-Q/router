package com.cuber.router.component.eventbus;


import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executors;

@Component
public class EventBusCenter {
    // 管理同步事件
    private EventBus syncEventBus = new EventBus();

    // 管理异步事件
    private AsyncEventBus asyncEventBus = new AsyncEventBus(Executors.newCachedThreadPool());

    public void postSync(Object event) {
        syncEventBus.post(event);
    }

    public void postAsync(Object event) {
        asyncEventBus.post(event);
    }

    @PostConstruct
    public void init() {
        // 获取所有带有 @EventBusListener 的 bean，将他们注册为监听者
        List<Object> listeners = SpringContextUtils.getBeansWithAnnotation(EventBusListener.class);
        listeners.forEach(l->{
            asyncEventBus.register(l);
            syncEventBus.register(l);
        });
    }
}
