package com.cuber.router.component;

import com.cuber.router.common.ChannelStatus;

public interface RecoverChain extends Chain {

    RecoverChain addLast(RecoverSubHandler subHandler);

    RecoverChain getChain();

    ChannelStatus doChainedRecover(String channelCode);
}
