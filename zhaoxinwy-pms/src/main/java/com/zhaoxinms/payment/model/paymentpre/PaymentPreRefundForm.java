package com.zhaoxinms.payment.model.paymentpre;

import lombok.Data;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentPreRefundForm {
    /** 资源名 */
    @JsonProperty("resourceName")
    private String resourceName;

    @JsonProperty("resourceId")
    @NotBlank(message = "商铺信息不能为空")
    private String resourceId;

    /** 商业区 */
    @JsonProperty("block")
    private String block;

    /** 客户姓名 */
    @JsonProperty("feeUser")
    @NotBlank(message = "客户姓名不能为空")
    private String feeUser;

    /** 付款方式 */
    @JsonProperty("payType")
    @NotBlank(message = "付款方式不能为空")
    private String payType;

    /** 退还时间 */
    @JsonProperty("operateTime")
    @NotNull(message = "退还时间不能为空")
    private long operateTime;

    @JsonProperty("refundItems")
    private List<Map<String, String>> refundItems;
}