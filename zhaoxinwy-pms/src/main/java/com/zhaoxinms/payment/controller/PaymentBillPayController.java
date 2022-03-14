package com.zhaoxinms.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
import com.zhaoxinms.payment.service.PaymentBillService;
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

    @PreAuthorize("@ss.hasRole('payee')")
    @PostMapping("/payCalc")
    public ActionResult calc(@RequestBody PaymentBillPayForm payForm) throws IllegalAccessException {
        paymentBillService.payCalc(payForm);
        return ActionResult.success(payForm);
    }

    @PreAuthorize("@ss.hasRole('payee')")
    @PostMapping("/payChceck")
    public ActionResult check(@RequestBody PaymentBillPayForm payForm) throws IllegalAccessException {
        paymentBillService.payCalc(payForm);
        paymentBillService.payCheck(payForm);
        return ActionResult.success();
    }

    @PreAuthorize("@ss.hasRole('payee')")
    @Log(title = "收费数据支付", businessType = BusinessType.PAY)
    @PostMapping("/payBill")
    @Transactional
    public ActionResult pay(@RequestBody PaymentBillPayForm payForm) throws IllegalAccessException {
        paymentBillService.payCalc(payForm);
        paymentBillService.payCheck(payForm);
        PaymentPayLogEntity payLog = paymentBillService.paySave(payForm);
        return ActionResult.success(payLog);
    }
    
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "收费数据支付", businessType = BusinessType.REFUND)
    @PostMapping("/refundBill")
    @Transactional
    public ActionResult refund(@RequestBody PaymentBillRefundForm refundForm) throws IllegalAccessException {
        paymentBillService.refundBill(refundForm);
        return ActionResult.success("退款成功");
    }
}
