package com.zhaoxinms.payment.model.paymentbill;

import lombok.Data;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentBillPreviewForm {
    /** 资源名 */
    @JsonProperty("resourceName")
    private String resourceName;

    /** 资源id */
    @JsonProperty("resourceId")
    @NotBlank(message = "资源信息不能为空")
    private String resourceId;

    /** 收费项id */
    @JsonProperty("feeItemId")
    @NotBlank(message = "收费项不能为空")
    private String feeItemId;

    /** 收费项名 */
    @JsonProperty("feeItemName")
    private String feeItemName;

    /** 客户 */
    @JsonProperty("feeUser")
    private String feeUser;

    /** 账单对应的周期 */
    @JsonProperty("beginDate")
    @NotNull
    private Long beginDate;

    /** 账单对应的周期 */
    @JsonProperty("endDate")
    @NotNull
    private Long endDate;

    /** 数量 */
    @JsonProperty("num")
    @NotBlank(message = "数量不能为空")
    private String num;

    /** 单价 */
    @JsonProperty("price")
    @NotBlank(message = "单价不能为空")
    private String price;

    /** 金额 */
    @JsonProperty("total")
    @NotBlank(message = "金额不能为空")
    private String total;
}