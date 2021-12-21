package com.zhaoxinms.statistics.model;

import lombok.Data;

@Data
public class PaymentHouseStatisticsModel {
    private String resourceId;
    private String resourceName;
    private String total;
    private String discount;
    private String lateFee;
    private String count;
}
