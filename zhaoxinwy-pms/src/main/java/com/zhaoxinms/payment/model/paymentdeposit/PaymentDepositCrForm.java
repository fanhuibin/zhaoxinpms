package com.zhaoxinms.payment.model.paymentdeposit;

import lombok.Data;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentDepositCrForm {
    /** 资源名 */
    @JsonProperty("resourceName")
    private String resourceName;

    @JsonProperty("resourceId")
    private String resourceId;

    /** 商业区 */
    @JsonProperty("block")
    private String block;

    @JsonProperty("feeItemId")
    private String feeItemId;

    /** 客户姓名 */
    @JsonProperty("feeUser")
    @NotBlank(message = "客户姓名不能为空")
    private String feeUser;

    /** 金额 */
    @JsonProperty("amt")
    @NotNull
    private BigDecimal amt;

    /** 付款方式 */
    @JsonProperty("payType")
    @NotBlank(message = "付款方式不能为空")
    private String payType;

    /** 收取时间 */
    @JsonProperty("operateTime")
    @NotNull(message = "收款人不能为空")
    private long operateTime;
}