package com.cuber.router.entity;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitorMetaData {
    private Integer total;
    private Integer success;
    private Integer fail;
    private Long timestamp;

    public void merge(MonitorMetaData metaData) {
        this.total += metaData.total;
        this.success += metaData.success;
        this.fail += metaData.fail;
    }
}
