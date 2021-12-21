package com.zhaoxinms.statistics.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class DailyReportModel {
    private String resourceName;
    private String total = "0";
    private String returnTotal = "0";
    private Map<String, String> typeMoney = new HashMap<String, String>();
    private Map<String, String> typeReturnMoney = new HashMap<String, String>();
}
