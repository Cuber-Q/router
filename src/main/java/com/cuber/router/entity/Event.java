package com.cuber.router.entity;


import com.cuber.router.common.ChannelStatus;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private String channelCode;
    private Long timeStamp;
    private ChannelStatus from;
    private ChannelStatus to;
}
