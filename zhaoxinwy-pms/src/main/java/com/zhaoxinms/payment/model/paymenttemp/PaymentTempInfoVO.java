package com.zhaoxinms.payment.model.paymenttemp;

import lombok.Data;

@Data
public class PaymentTempInfoVO extends PaymentTempCrForm {
    /** 主键 */
    private String id;
    //支付号
    private String payNo;
}