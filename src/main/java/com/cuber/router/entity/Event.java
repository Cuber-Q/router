package com.cuber.router.entity;


import com.cuber.router.common.ChannelStatus;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    protected String channelCode;
    protected Long timeStamp;
    protected ChannelStatus from;
    protected ChannelStatus to;
}
