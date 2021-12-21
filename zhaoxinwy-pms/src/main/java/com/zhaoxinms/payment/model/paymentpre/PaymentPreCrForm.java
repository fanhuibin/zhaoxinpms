package com.zhaoxinms.payment.model.paymentpre;

import lombok.Data;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentPreCrForm {
    /** 资源名 */
    @JsonProperty("resourceName")
    private String resourceName;

    @JsonProperty("resourceId")
    @NotBlank(message = "商铺信息不能为空")
    private String resourceId;

    /** 商业区 */
    @JsonProperty("block")
    private String block;

    @JsonProperty("useFeeItem")
    @NotBlank(message = "请选择是否指定收费项")
    private String useFeeItem;

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

    /** 收款时间 */
    @JsonProperty("operateTime")
    @NotNull(message = "收款时间不能为空")
    private long operateTime;
}