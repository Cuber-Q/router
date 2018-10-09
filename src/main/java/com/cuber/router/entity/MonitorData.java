package com.cuber.router.entity;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitorData {

    private String channelCode;
    private List<MonitorMetaData> metaDataList;

}
