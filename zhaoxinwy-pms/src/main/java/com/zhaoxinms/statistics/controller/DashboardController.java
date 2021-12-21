package com.zhaoxinms.statistics.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.baseconfig.mapper.ConfigHouseMapper;
import com.zhaoxinms.statistics.mapper.PaymentStatisticsMapper;
import com.zhaoxinms.statistics.service.DashboardService;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.DateUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "首页统计", description = "")
@RequestMapping("/statistics/dashboard")
public class DashboardController {
    @Autowired
    private PaymentStatisticsMapper mapper;
    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    @ApiOperation("仪表盘")
    public ActionResult list() {

        // 欠费数据统计
        Map<String, Object> delayFee = mapper.getDelayPayCount();
        if (!delayFee.containsKey("sum")) {
            delayFee.put("sum", 0);
        }

        // 待收费数据统计
        Map<String, Object> needPay = mapper.getUnpaiedCount();
        if (!needPay.containsKey("sum")) {
            needPay.put("sum", 0);
        }

        // 商铺统计
        Map<String, Integer> houseCount = dashboardService.getHouseCount();
        // 月统计
        Map<String, List> monthPay = dashboardService.getMonthPayStatistics();

        Map<String, Map> result = new HashMap<String, Map>();
        result.put("delayFee", delayFee);
        result.put("needPay", needPay);
        result.put("houseCount", houseCount);
        result.put("monthPay", monthPay);

        return ActionResult.success(result);
    }
}
