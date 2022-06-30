package com.zhaoxinms.payment.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.zhaoxinms.payment.entity.PaymentOrder;
import com.zhaoxinms.payment.entity.PaymentPayLogEntity;
import com.zhaoxinms.payment.entity.pagination.PaymentOrderPagination;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPayForm;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillRefundForm;

/**
 * 在线支付订单Service接口
 * 
 * @author cycberform
 * @date 2022-03-16
 */
public interface PaymentBillPayService
{

    // 保存付款
    PaymentPayLogEntity paySave(PaymentBillPayForm payForm);

    // 付款数据验证
    void payCheck(PaymentBillPayForm payForm, boolean billCanHasOrder);

    // 计算付款金额
    void payCalc(PaymentBillPayForm payForm) throws IllegalAccessException;
}
