package com.zhaoxinms.statistics.controller;

import java.io.IOException;
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
import com.zhaoxinms.base.util.DateUtil;
import com.zhaoxinms.base.util.DynDicUtil;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.payment.entity.PaymentPayLogEntity;
import com.zhaoxinms.payment.model.paymentpaylog.PaymentPayLogListVO;
import com.zhaoxinms.payment.model.paymentpaylog.PaymentPayLogPagination;
import com.zhaoxinms.payment.service.PaymentPayLogService;
import com.zhaoxinms.statistics.mapper.PaymentStatisticsMapper;
import com.zhaoxinms.statistics.model.PayLogStatisticsModel;
import com.zhaoxinms.statistics.model.PaymentFeeStatisticsModel;
import com.zhaoxinms.statistics.model.PaymentHouseStatisticsModel;
import com.zhaoxinms.statistics.service.PaymentStatisticsService;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.DateUtils;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "用户支付明细表")
@RequestMapping("/statistics/PaymentStatistics")
public class PaymentStatisticsController {

    @Autowired
    private PaymentStatisticsMapper paymentStatisticsMapper;
    @Autowired
    private PaymentStatisticsService paymentStatisticsService;

    @PreAuthorize("@ss.hasPermi('statistics:PaymentStatistics:PaymentStatistics')")
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

        /**
         * 合计费用信息
         */
        // 合计付款信息
        List<PayLogStatisticsModel> pay =
            paymentStatisticsMapper.getPayLogByDate(beginDate, endDate, ConstantsUtil.PAY_LOG_TYPE_PAY);

        // 合计退款信息
        List<PayLogStatisticsModel> refund =
            paymentStatisticsMapper.getPayLogByDate(beginDate, endDate, ConstantsUtil.PAY_LOG_TYPE_REFUND);

        /**
         * 分费用统计
         */
        // 分费用统计paymentbill
        List<PaymentFeeStatisticsModel> billSum = paymentStatisticsService.getBillSumGroupByFee(beginDate, endDate);

        // 分费用统计押金
        List<PaymentFeeStatisticsModel> depositSum =
            paymentStatisticsService.getDepositSumGroupByFee(beginDate, endDate);
        List<PaymentFeeStatisticsModel> depositRefundSum =
            paymentStatisticsService.getDepositRefundSumGroupByFee(beginDate, endDate);

        // 分费用统计 临时收费
        List<PaymentFeeStatisticsModel> tempSum = paymentStatisticsService.getTempSumGroupByFee(beginDate, endDate);
        List<PaymentFeeStatisticsModel> tempRefundSum =
            paymentStatisticsService.getTempRefundSumGroupByFee(beginDate, endDate);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("paySum", pay.get(0));
        result.put("refundSum", refund.get(0));
        result.put("billSum", billSum);
        result.put("depositSum", depositSum);
        result.put("depositRefundSum", depositRefundSum);
        result.put("tempSum", tempSum);
        result.put("tempRefundSum", tempRefundSum);

        return ActionResult.success(result);
    }

    @PreAuthorize("@ss.hasPermi('statistics:PaymentStatistics:house')")
    @GetMapping("/groupByHouse")
    public ActionResult groupByHouse(HttpServletRequest request) throws IOException {
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");

        // 开始和截止时间不能超过1年
        if (Long.valueOf(endDate) - Long.valueOf(beginDate) > 1l * 365 * 24 * 60 * 60 * 1000) {
            throw new DataException("开始时间和截止时间的间隔不能超过1年");
        }
        beginDate = DateUtils.formatDate(new Date(Long.valueOf(beginDate)), "yyyy-MM-dd 00:00:00");
        endDate = DateUtils.formatDate(new Date(Long.valueOf(endDate)), "yyyy-MM-dd 23:59:59");

        /**
         * 合计费用信息
         */
        // 合计付款信息
        List<PayLogStatisticsModel> pay =
            paymentStatisticsMapper.getPayLogByDate(beginDate, endDate, ConstantsUtil.PAY_LOG_TYPE_PAY);
        // 合计退款信息
        List<PayLogStatisticsModel> refund =
            paymentStatisticsMapper.getPayLogByDate(beginDate, endDate, ConstantsUtil.PAY_LOG_TYPE_REFUND);

        /**
         * 分商铺统计
         */
        List<PaymentHouseStatisticsModel> depositSum =
            paymentStatisticsService.getDepositSumGroupByHouse(beginDate, endDate);
        List<PaymentHouseStatisticsModel> depositRefundSum =
            paymentStatisticsService.getDepositRefundSumGroupByHouse(beginDate, endDate);
        List<PaymentHouseStatisticsModel> tempSum = paymentStatisticsService.getTempSumGroupByHouse(beginDate, endDate);
        List<PaymentHouseStatisticsModel> tempRefundSum =
            paymentStatisticsService.getTempRefundSumGroupByHouse(beginDate, endDate);
        List<PaymentHouseStatisticsModel> billSum = paymentStatisticsService.getBillSumGroupByHouse(beginDate, endDate);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("paySum", pay.get(0));
        result.put("refundSum", refund.get(0));
        result.put("billSum", billSum);
        result.put("depositSum", depositSum);
        result.put("depositRefundSum", depositRefundSum);
        result.put("tempSum", tempSum);
        result.put("tempRefundSum", tempRefundSum);
        return ActionResult.success(result);
    }
}
