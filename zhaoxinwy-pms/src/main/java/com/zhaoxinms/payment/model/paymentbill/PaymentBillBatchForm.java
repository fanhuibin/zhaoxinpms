package com.zhaoxinms.payment.model.paymentbill;

import lombok.Data;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentBillBatchForm {
    /** 资源名 */
    @JsonProperty("resourceName")
    @NotBlank(message = "商铺信息不能为空")
    private String resourceName;

    /** 收费项id */
    @JsonProperty("feeItemId")
    @NotBlank(message = "收费项不能为空")
    private String feeItemId;

    /** 收费项名 */
    @JsonProperty("feeItemName")
    private String feeItemName;

    /** 账单对应的周期 */
    @JsonProperty("beginDate")
    @NotNull
    private Long beginDate;

    /** 账单对应的周期 */
    @JsonProperty("endDate")
    @NotNull
    private Long endDate;

    /** 缴费限期 */
    @JsonProperty("deadline")
    @NotNull
    private Long deadline;

    /** 单价 */
    @JsonProperty("price")
    @NotBlank(message = "单价不能为空")
    @Range(min=0,max=100000000)
    private String price;
}