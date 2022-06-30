package com.zhaoxinms.payment.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.payment.entity.PaymentDepositEntity;
import com.zhaoxinms.payment.entity.PaymentPayLogEntity;
import com.zhaoxinms.payment.entity.PaymentPreEntity;
import com.zhaoxinms.payment.entity.PaymentTempEntity;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPayForm;
import com.zhaoxinms.payment.model.paymentpaylog.PaymentPayLogPagination;

/**
 *
 * payment_pay_log 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-09-01 09:31:28
 */
public interface PaymentPayLogService extends IService<PaymentPayLogEntity> {

    List<PaymentPayLogEntity> getList(PaymentPayLogPagination paymentPayLogPagination);

    List<PaymentPayLogEntity> getTypeList(PaymentPayLogPagination paymentPayLogPagination, String dataType);

    PaymentPayLogEntity getInfo(String id);
    
    PaymentPayLogEntity getByPayNo(String payNo);

    // 创建押金的支付记录
    PaymentPayLogEntity createDepositPayLog(PaymentDepositEntity deposit, String method);

    // 创建押金的退还记录
    PaymentPayLogEntity createDepositRefundLog(PaymentDepositEntity deposit, String method);

    // 线下支付常规收费项记录
    PaymentPayLogEntity payBill(PaymentBillPayForm payForm, PaymentContractEntity contract);
    
    // 线下常规收费项退款记录
    PaymentPayLogEntity refundBill(PaymentBillEntity entity, String payMethod, String amcount, String comment);
    
    // 创建临时收费支付记录
    PaymentPayLogEntity createTempPayLog(PaymentTempEntity entity, String payType);

    // 创建临时收费退款记录
    PaymentPayLogEntity createTempRefundLog(PaymentTempEntity deposit, String refundType);
}
