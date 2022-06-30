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
public interface PaymentOrderService extends IService<PaymentOrder>
{

    // 创建并支付订单
    void paySave(PaymentOrder paymentOrder);

    // 退款
    void refundBill(PaymentBillRefundForm refundForm);
    
    List<PaymentOrder> getList(PaymentOrderPagination pagination);

    PaymentOrder getInfo(String id);
}
