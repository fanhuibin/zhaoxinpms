package com.zhaoxinms.statistics.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class DailyFeeReportHeadModel {
    private List<Map<String, String>> houseFeeHead = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> tempFeeHead = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> depositFeeHead = new ArrayList<Map<String, String>>();
}
