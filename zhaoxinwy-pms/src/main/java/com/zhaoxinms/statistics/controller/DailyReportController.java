package com.zhaoxinms.statistics.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.statistics.mapper.PaymentStatisticsMapper;
import com.zhaoxinms.statistics.model.DailyReportModel;
import com.zhaoxinms.statistics.model.PayLogStatisticsModel;
import com.zhaoxinms.statistics.service.PaymentStatisticsService;
import com.zhaoxinms.util.CalculationUtil;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.DateUtils;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "收费报表")
@RequestMapping("/statistics/DailyReport")
public class DailyReportController {

    @Autowired
    private PaymentStatisticsMapper paymentStatisticsMapper;
    @Autowired
    private PaymentStatisticsService paymentStatisticsService;

    // 真实的收款场景
    // 支付有两个渠道：
    // 1.payLog的 payMoney-changeMoney
    // 2.preAccount的 add
    // 退款有两个渠道：
    // 1.preAccount return
    // 2.payLog 的 return类型
    @PreAuthorize("@ss.hasPermi('statistics:dailyFeeReport:dailyFeeReport')")
    @GetMapping
    public ActionResult list(HttpServletRequest request) throws IOException {
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");

        // 开始和截止时间不能超过1年
        if (Long.valueOf(endDate) - Long.valueOf(beginDate) > 1l * 365 * 24 * 60 * 60 * 1000) {
            throw new DataException("开始时间和截止时间的间隔不能超过1年");
        }
        beginDate = DateUtils.formatDate(new Date(Long.valueOf(beginDate)), "yyyy-MM-dd 00:00:00");
        endDate = DateUtils.formatDate(new Date(Long.valueOf(endDate)), "yyyy-MM-dd 23:59:59");

        List<DailyReportModel> report = new ArrayList<DailyReportModel>();
        Map<String, DailyReportModel> keyList = new HashMap<String, DailyReportModel>();

        payDetail(beginDate, endDate, report, keyList);
        refundDetail(beginDate, endDate, report, keyList);

        return ActionResult.success(report);
    }

    private void refundDetail(String beginDate, String endDate, List<DailyReportModel> report,
        Map<String, DailyReportModel> keyList) {
        // 退款数据
        List<PayLogStatisticsModel> logs = paymentStatisticsMapper.getPayLogGroupByResourceAndPayType(beginDate,
            endDate, ConstantsUtil.PAY_LOG_TYPE_REFUND);
        // 预存款退还数据
        List<Map<String, Object>> pres = paymentStatisticsMapper.getPreSumGroupByResourceNameAndPayType(beginDate,
            endDate, ConstantsUtil.PAY_PRE_TYPE_REFUND);

        for (PayLogStatisticsModel log : logs) {
            String payMoney = log.getPayMoney();
            String changeMoney = log.getChangeMoney();
            String realPay = CalculationUtil.subtract(payMoney, changeMoney, 2);
            if (keyList.containsKey(log.getResourceName())) {
                DailyReportModel model = keyList.get(log.getResourceName());
                Map<String, String> map = model.getTypeReturnMoney();
                map.put(log.getPayMethod(), realPay);
                model.setReturnTotal(CalculationUtil.add(model.getReturnTotal(), realPay, 2));

            } else {
                DailyReportModel model = new DailyReportModel();
                model.setResourceName(log.getResourceName());
                Map<String, String> map = new HashMap<String, String>();
                map.put(log.getPayMethod(), log.getPayMoney());
                model.setTypeReturnMoney(map);
                model.setReturnTotal(realPay);

                keyList.put(log.getResourceName(), model);
                report.add(model);
            }
        }

        for (Map<String, Object> pre : pres) {
            String resourceName = (String)pre.get("resourceName");
            String payType = (String)pre.get("payType");
            String money = (String)pre.get("amt").toString();
            if (keyList.containsKey(resourceName)) {
                DailyReportModel model = keyList.get(resourceName);
                Map<String, String> map = model.getTypeReturnMoney();
                if (map.containsKey(payType)) {
                    map.put(payType, CalculationUtil.add(map.get(payType), money, 2));
                } else {
                    map.put(payType, money);
                }
                model.setReturnTotal(CalculationUtil.add(model.getReturnTotal(), money, 2));
            } else {
                DailyReportModel model = new DailyReportModel();
                model.setResourceName(resourceName);
                Map<String, String> map = new HashMap<String, String>();
                map.put(payType, money);
                model.setTypeReturnMoney(map);
                model.setReturnTotal(money);

                keyList.put(resourceName, model);
                report.add(model);
            }
        }
    }

    private void payDetail(String beginDate, String endDate, List<DailyReportModel> report,
        Map<String, DailyReportModel> keyList) {
        // 收费数据
        List<PayLogStatisticsModel> logs = paymentStatisticsMapper.getPayLogGroupByResourceAndPayType(beginDate,
            endDate, ConstantsUtil.PAY_LOG_TYPE_PAY);
        // 预存款数据
        List<Map<String, Object>> pres = paymentStatisticsMapper.getPreSumGroupByResourceNameAndPayType(beginDate,
            endDate, ConstantsUtil.PAY_PRE_TYPE_ADD);

        for (PayLogStatisticsModel log : logs) {
            String payMoney = log.getPayMoney();
            String changeMoney = log.getChangeMoney();
            String realPay = CalculationUtil.subtract(payMoney, changeMoney, 2);
            if (keyList.containsKey(log.getResourceName())) {
                DailyReportModel model = keyList.get(log.getResourceName());
                Map<String, String> map = model.getTypeMoney();
                map.put(log.getPayMethod(), realPay);
                model.setTotal(CalculationUtil.add(model.getTotal(), realPay, 2));

            } else {
                DailyReportModel model = new DailyReportModel();
                model.setResourceName(log.getResourceName());
                Map<String, String> map = new HashMap<String, String>();
                map.put(log.getPayMethod(), log.getPayMoney());
                model.setTypeMoney(map);
                model.setTotal(realPay);

                keyList.put(log.getResourceName(), model);
                report.add(model);
            }
        }

        for (Map<String, Object> pre : pres) {
            String resourceName = (String)pre.get("resourceName");
            String payType = (String)pre.get("payType");
            String money = (String)pre.get("amt").toString();
            if (keyList.containsKey(resourceName)) {
                DailyReportModel model = keyList.get(resourceName);
                Map<String, String> map = model.getTypeMoney();
                if (map.containsKey(payType)) {
                    map.put(payType, CalculationUtil.add(map.get(payType), money, 2));
                } else {
                    map.put(payType, money);
                }
                model.setTotal(CalculationUtil.add(model.getTotal(), money, 2));
            } else {
                DailyReportModel model = new DailyReportModel();
                model.setResourceName(resourceName);
                Map<String, String> map = new HashMap<String, String>();
                map.put(payType, money);
                model.setTypeMoney(map);
                model.setTotal(money);

                keyList.put(resourceName, model);
                report.add(model);
            }
        }
    }
}
