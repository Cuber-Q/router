package com.cuber.router.component;

import com.cuber.router.common.ChannelStatus;

public interface RecoverSubHandler {

    ChannelStatus doRecover(String channelCode);
}
