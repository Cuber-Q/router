package com.cuber.router.component;

/**
 * 通道状态处理器
 *  每个状态都应该有相应的handler。
 *  根据监控数据，在通道状态需要变更时通知相应状态的handler
 */
public interface Handler {

    void handle(String channelCode);

}
