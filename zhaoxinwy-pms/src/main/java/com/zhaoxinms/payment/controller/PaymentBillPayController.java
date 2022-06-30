/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms website：http://pms.zhaoxinms.com wx： zhaoxinms
 *
 */
package com.zhaoxinms.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.payment.entity.PaymentPayLogEntity;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPayForm;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillRefundForm;
import com.zhaoxinms.payment.service.PaymentBillPayService;
import com.zhaoxinms.payment.service.PaymentBillService;
import com.zhaoxinms.payment.service.PaymentOrderService;
import com.zhaoxinms.payment.service.PaymentPayLogService;
import com.zhaoxinms.payment.service.PaymentPreAccountService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "收费数据支付", description = "")
@RequestMapping("/payment/PaymentBillPay")
public class PaymentBillPayController {
    @Autowired
    private PaymentBillService paymentBillService;
    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private PaymentBillPayService paymentBillPayService;

    @PreAuthorize("@ss.hasRole('payee')")
    @PostMapping("/payCalc")
    public ActionResult calc(@RequestBody PaymentBillPayForm payForm) throws IllegalAccessException {
        paymentBillPayService.payCalc(payForm);
        return ActionResult.success(payForm);
    }

    @PreAuthorize("@ss.hasRole('payee')")
    @PostMapping("/payChceck")
    public ActionResult check(@RequestBody PaymentBillPayForm payForm) throws IllegalAccessException {
        paymentBillPayService.payCalc(payForm);
        paymentBillPayService.payCheck(payForm, false);
        return ActionResult.success();
    }

    @PreAuthorize("@ss.hasRole('payee')")
    @Log(title = "收费数据支付", businessType = BusinessType.PAY)
    @PostMapping("/payBill")
    @Transactional
    public ActionResult pay(@RequestBody PaymentBillPayForm payForm) throws IllegalAccessException {
        paymentBillPayService.payCalc(payForm);
        paymentBillPayService.payCheck(payForm, false);
        PaymentPayLogEntity payLog = paymentBillPayService.paySave(payForm);
        return ActionResult.success(payLog);
    }

    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "收费数据支付", businessType = BusinessType.REFUND)
    @PostMapping("/refundBill")
    @Transactional
    public ActionResult refund(@RequestBody PaymentBillRefundForm refundForm) throws IllegalAccessException {
        paymentOrderService.refundBill(refundForm);
        return ActionResult.success("退款成功");
    }
}
