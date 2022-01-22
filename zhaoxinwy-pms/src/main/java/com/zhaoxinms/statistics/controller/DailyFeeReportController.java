package com.zhaoxinms.statistics.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.baseconfig.service.impl.ConfigFeeItemServiceImpl;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.statistics.mapper.PaymentStatisticsMapper;
import com.zhaoxinms.statistics.model.DailyFeeReportHeadModel;
import com.zhaoxinms.statistics.model.DailyFeeReportModel;
import com.zhaoxinms.statistics.model.DailyReportModel;
import com.zhaoxinms.statistics.model.PayLogStatisticsModel;
import com.zhaoxinms.statistics.model.PaymentHouseAndFeeStatisticsModel;
import com.zhaoxinms.statistics.model.PaymentHouseStatisticsModel;
import com.zhaoxinms.statistics.service.PaymentStatisticsService;
import com.zhaoxinms.util.CalculationUtil;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.DateUtils;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "收费报表（收费项目）")
@RequestMapping("/statistics/DailyFeeReport")
public class DailyFeeReportController {

    @Autowired
    private PaymentStatisticsMapper paymentStatisticsMapper;
    @Autowired
    private ConfigFeeItemService configFeeItemService;

    // 收费项目的总金额 - 预收款的金额变动 = 本次实际收到的金额
    @PreAuthorize("@ss.hasPermi('statistics:dailyFee:dailyFee')")
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

        // 商铺收费数据
        List<PaymentHouseAndFeeStatisticsModel> houseStatis = paymentStatisticsMapper.getBillSumGroupByHouseAndFee(beginDate, endDate);
        // 临时收费数据
        List<PaymentHouseAndFeeStatisticsModel> tempStatis = paymentStatisticsMapper.getTempSumGroupByHouseAndFee(beginDate, endDate);
        // 押金收费数据
        List<PaymentHouseAndFeeStatisticsModel> depositStatis = paymentStatisticsMapper.getDepositSumGroupByHouseAndFee(beginDate, endDate);
        // 预存款数据
        List<Map<String, Object>> pres = paymentStatisticsMapper.getPreSumGroupByResourceNameAndType(beginDate, endDate);

        List<DailyFeeReportModel> report = new ArrayList<DailyFeeReportModel>();
        Map<String, DailyFeeReportModel> keyList = new HashMap<String, DailyFeeReportModel>();

        for (PaymentHouseAndFeeStatisticsModel house : houseStatis) {
            if (keyList.containsKey(house.getResourceName())) {
                DailyFeeReportModel model = keyList.get(house.getResourceName());
                Map<String, String> map = model.getHouseFee();
                map.put(house.getFeeItemId(), house.getTotal());
                model.setTotal(CalculationUtil.add(model.getTotal(), house.getTotal(), 2));

            } else {
                DailyFeeReportModel model = new DailyFeeReportModel();
                model.setResourceName(house.getResourceName());
                Map<String, String> map = new HashMap<String, String>();
                map.put(house.getFeeItemId(), house.getTotal());
                model.setHouseFee(map);
                model.setDepositFee(new HashMap<String, String>());
                model.setTempFee(new HashMap<String, String>());
                model.setPreFee(new HashMap<String, String>());
                model.setTotal(house.getTotal());

                keyList.put(house.getResourceName(), model);
                report.add(model);
            }
        }

        for (PaymentHouseAndFeeStatisticsModel temp : tempStatis) {
            if (keyList.containsKey(temp.getResourceName())) {
                DailyFeeReportModel model = keyList.get(temp.getResourceName());
                Map<String, String> map = model.getTempFee();
                map.put(temp.getFeeItemId(), temp.getTotal());
                model.setTotal(CalculationUtil.add(model.getTotal(), temp.getTotal(), 2));

            } else {
                DailyFeeReportModel model = new DailyFeeReportModel();
                model.setResourceName(temp.getResourceName());
                Map<String, String> map = new HashMap<String, String>();
                map.put(temp.getFeeItemId(), temp.getTotal());
                model.setTempFee(map);
                model.setDepositFee(new HashMap<String, String>());
                model.setHouseFee(new HashMap<String, String>());
                model.setPreFee(new HashMap<String, String>());
                model.setTotal(temp.getTotal());

                keyList.put(temp.getResourceName(), model);
                report.add(model);
            }
        }

        for (PaymentHouseAndFeeStatisticsModel deposit : depositStatis) {
            if (keyList.containsKey(deposit.getResourceName())) {
                DailyFeeReportModel model = keyList.get(deposit.getResourceName());
                Map<String, String> map = model.getDepositFee();
                map.put(deposit.getFeeItemId(), deposit.getTotal());
                model.setTotal(CalculationUtil.add(model.getTotal(), deposit.getTotal(), 2));

            } else {
                DailyFeeReportModel model = new DailyFeeReportModel();
                model.setResourceName(deposit.getResourceName());
                Map<String, String> map = new HashMap<String, String>();
                map.put(deposit.getFeeItemId(), deposit.getTotal());
                model.setDepositFee(map);
                model.setHouseFee(new HashMap<String, String>());
                model.setTempFee(new HashMap<String, String>());
                model.setPreFee(new HashMap<String, String>());
                model.setTotal(deposit.getTotal());

                keyList.put(deposit.getResourceName(), model);
                report.add(model);
            }
        }

        for (Map<String, Object> pre : pres) {
            String resourceName = (String)pre.get("resourceName");
            String type = (String)pre.get("type");
            String money = pre.get("amt").toString();
            if (keyList.containsKey(resourceName)) {
                DailyFeeReportModel model = keyList.get(resourceName);
                Map<String, String> map = model.getPreFee();
                map.put(type, money);

                if (!type.equals(ConstantsUtil.PAY_PRE_TYPE_REFUND)) {
                    model.setTotal(CalculationUtil.add(model.getTotal(), money, 2));
                }

            } else {
                DailyFeeReportModel model = new DailyFeeReportModel();
                model.setResourceName(resourceName);
                Map<String, String> map = new HashMap<String, String>();
                map.put(type, money);
                model.setPreFee(map);
                model.setDepositFee(new HashMap<String, String>());
                model.setTempFee(new HashMap<String, String>());
                model.setHouseFee(new HashMap<String, String>());
                if (type.equals(ConstantsUtil.PAY_PRE_TYPE_ADD) || type.equals(ConstantsUtil.PAY_PRE_TYPE_PAY)) {
                    model.setTotal(CalculationUtil.add(model.getTotal(), money, 2));
                }
                keyList.put(resourceName, model);
                report.add(model);
            }
        }

        // 抽取表头
        DailyFeeReportHeadModel heads = this.getFeeReportHead(report);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("head", heads);
        result.put("list", report);
        return ActionResult.success(result);
    }

    // 抽取报表表头
    private DailyFeeReportHeadModel getFeeReportHead(List<DailyFeeReportModel> reports) {
        DailyFeeReportHeadModel heads = new DailyFeeReportHeadModel();
        Map<String, String> addedHead = new HashMap<String, String>();

        // 查询所有收费项
        QueryWrapper<ConfigFeeItemEntity> queryWrapper = new QueryWrapper<>();
        List<ConfigFeeItemEntity> fees = configFeeItemService.list(queryWrapper);

        List<Map<String, String>> houseFeeHead = new ArrayList<Map<String, String>>();
        List<Map<String, String>> tempFeeHead = new ArrayList<Map<String, String>>();
        List<Map<String, String>> depositFeeHead = new ArrayList<Map<String, String>>();

        for (DailyFeeReportModel report : reports) {

            Map<String, String> houseFee = report.getHouseFee();
            for (String s : houseFee.keySet()) {
                if (!addedHead.containsKey(s)) {
                    for (ConfigFeeItemEntity fee : fees) {
                        if (fee.getId().equals(s)) {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("id", s);
                            map.put("name", fee.getName());
                            addedHead.put(s, fee.getName());
                            houseFeeHead.add(map);
                        }
                    }
                }
            }

            Map<String, String> tempFee = report.getTempFee();
            for (String s : tempFee.keySet()) {
                if (!addedHead.containsKey(s)) {
                    for (ConfigFeeItemEntity fee : fees) {
                        if (fee.getId().equals(s)) {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("id", s);
                            map.put("name", fee.getName());
                            addedHead.put(s, fee.getName());
                            tempFeeHead.add(map);
                        }
                    }
                }
            }

            Map<String, String> depositFee = report.getDepositFee();
            for (String s : depositFee.keySet()) {
                if (!addedHead.containsKey(s)) {
                    for (ConfigFeeItemEntity fee : fees) {
                        if (fee.getId().equals(s)) {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("id", s);
                            map.put("name", fee.getName());
                            addedHead.put(s, fee.getName());
                            depositFeeHead.add(map);
                        }
                    }
                }
            }
        }

        heads.setHouseFeeHead(houseFeeHead);
        heads.setTempFeeHead(tempFeeHead);
        heads.setDepositFeeHead(depositFeeHead);
        return heads;
    }

    @GetMapping("getTitleHead")
    public ActionResult getTitleHead() {
        QueryWrapper<ConfigFeeItemEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getType, ConfigFeeItemServiceImpl.TYPE_HOUSE));
        queryWrapper.lambda().orderByDesc(ConfigFeeItemEntity::getCreatorTime);
        List<ConfigFeeItemEntity> houseFees = configFeeItemService.list(queryWrapper);

        queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getType, ConfigFeeItemServiceImpl.TYPE_TEMPORARY));
        queryWrapper.lambda().orderByDesc(ConfigFeeItemEntity::getCreatorTime);
        List<ConfigFeeItemEntity> tempFees = configFeeItemService.list(queryWrapper);

        queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getType, ConfigFeeItemServiceImpl.TYPE_DEPOSIT));
        queryWrapper.lambda().orderByDesc(ConfigFeeItemEntity::getCreatorTime);
        List<ConfigFeeItemEntity> depositFees = configFeeItemService.list(queryWrapper);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("houseFees", houseFees);
        result.put("tempFees", tempFees);
        result.put("depositFees", depositFees);
        return ActionResult.success(result);
    }
}
