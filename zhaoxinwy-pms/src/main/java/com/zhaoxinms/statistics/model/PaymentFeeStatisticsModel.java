package com.zhaoxinms.statistics.model;

import lombok.Data;

@Data
public class PaymentFeeStatisticsModel {
    private String feeItemId;
    private String feeItemName;
    private String total;
    private String discount;
    private String lateFee;
    private String count;
    private String resourceName;
}
