package com.zhaoxinms.payment.model.paymentbill;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.model.paymentpaylog.PaymentPayLogCrForm;
import com.zhaoxinms.payment.model.paymentpreaccount.PaymentPreAccountPayForm;

import lombok.Data;

@Data
public class PaymentBillRefundForm {

    /** 支付的缴费单 */
    @JsonProperty("billId")
    @NotBlank(message = "缴费单号不能为空")
    private String billId;
    
    /** 退款方式 */
    @JsonProperty("payMethod")
    @NotBlank(message = "退款方式")
    private String payMethod;

    /** 退款金额*/
    @JsonProperty("currentRefundAmount")
    @NotBlank(message = "退款金额不能为空")
    private String currentRefundAmount;
    
    /** 退款原因 */
    @JsonProperty("refundComment")
    @NotBlank(message = "退款原因不能为空")
    private String refundComment;
}