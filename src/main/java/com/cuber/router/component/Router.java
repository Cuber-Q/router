package com.cuber.router.component;

import com.cuber.router.entity.Channel;

public interface Router {

    Channel routeChannel();

    Boolean isAvailable(String channelCode);

}
