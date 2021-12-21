package com.zhaoxinms.statistics.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zhaoxinms.baseconfig.mapper.ConfigHouseMapper;
import com.zhaoxinms.statistics.mapper.PaymentStatisticsMapper;
import com.zhaoxinms.statistics.model.PayLogStatisticsModel;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.DateUtils;

@Service
public class DashboardService {
    @Autowired
    private PaymentStatisticsMapper mapper;
    @Autowired
    private ConfigHouseMapper configHouseMapper;

    // 月度统计
    public Map<String, List> getMonthPayStatistics() {
        // 月度统计
        List<String> months = DateUtils.getSixMonth(DateUtils.getDateTime());

        List<String> data = new ArrayList<String>();
        Map<String, List> result = new HashMap<String, List>();
        for (String month : months) {
            List<PayLogStatisticsModel> map = mapper.getMonthPayLog(month, ConstantsUtil.PAY_LOG_TYPE_PAY);
            data.add(map.get(0).getReceivable());
        }
        result.put("month", months);
        result.put("value", data);
        return result;
    }

    // 商铺租售比例
    public Map<String, Integer> getHouseCount() {
        // 查询商铺统计
        int emptyCount = configHouseMapper.getHouseCountByState(ConstantsUtil.HOUSE_STATE_EMPTY);
        int selledCount = configHouseMapper.getHouseCountByState(ConstantsUtil.HOUSE_STATE_SELLED);
        int rentedCount = configHouseMapper.getHouseCountByState(ConstantsUtil.HOUSE_STATE_RENTED);

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("emptyCount", emptyCount);
        map.put("selledCount", selledCount);
        map.put("rentedCount", rentedCount);
        return map;
    }
}
