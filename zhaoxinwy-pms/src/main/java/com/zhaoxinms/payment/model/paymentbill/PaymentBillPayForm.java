package com.zhaoxinms.payment.model.paymentbill;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.model.paymentpaylog.PaymentPayLogCrForm;
import com.zhaoxinms.payment.model.paymentpreaccount.PaymentPreAccountPayForm;

import lombok.Data;

@Data
public class PaymentBillPayForm extends PaymentPayLogCrForm {

    /** 支付的缴费单 */
    @JsonProperty("paymentBills")
    private List<PaymentBillListVO> paymentBills;

    @JsonProperty("accountForms")
    private List<PaymentPreAccountPayForm> accountForms;
}