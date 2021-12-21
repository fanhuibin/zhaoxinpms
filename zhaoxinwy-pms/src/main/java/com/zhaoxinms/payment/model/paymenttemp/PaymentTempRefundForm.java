package com.zhaoxinms.payment.model.paymenttemp;

import lombok.Data;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentTempRefundForm {

    @JsonProperty("refundTime")
    @NotNull
    private Long refundTime;

    /** 退款方式 */
    @JsonProperty("refundType")
    @NotNull
    private String refundType;

    /** 备注 */
    @JsonProperty("remark")
    private String remark;
}