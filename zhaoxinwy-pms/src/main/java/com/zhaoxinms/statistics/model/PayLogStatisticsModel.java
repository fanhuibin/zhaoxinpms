package com.zhaoxinms.statistics.model;

import lombok.Data;

@Data
public class PayLogStatisticsModel {
    private String month;
    private String count;
    private String resourceName;
    private String payMethod;
    private String receivable;
    private String discount;
    private String payMoney;
    private String lateFeeMoney;
    private String changeMoney;
    private String prePayMoney;
    private String preSaveMoney;
}
