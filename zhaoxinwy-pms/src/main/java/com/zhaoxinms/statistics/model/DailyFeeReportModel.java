package com.zhaoxinms.statistics.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class DailyFeeReportModel {
    private String resourceName;
    private String total = "0";
    private Map<String, String> houseFee = new HashMap<String, String>();
    private Map<String, String> tempFee = new HashMap<String, String>();
    private Map<String, String> depositFee = new HashMap<String, String>();
    private Map<String, String> preFee = new HashMap<String, String>();
}
