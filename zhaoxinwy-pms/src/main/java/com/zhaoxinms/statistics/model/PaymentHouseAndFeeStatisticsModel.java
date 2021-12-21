package com.zhaoxinms.statistics.model;

import lombok.Data;

@Data
public class PaymentHouseAndFeeStatisticsModel {
    private String resourceId;
    private String resourceName;
    private String feeItemId;
    private String total;
    private String returnTotal;
    private String discount;
    private String lateFee;
    private String count;
}
