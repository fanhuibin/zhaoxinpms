package com.zhaoxinms.payment.model.paymentbill;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentBillCrForm {
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

    /** 缴费限期 */
    @JsonProperty("deadline")
    @NotNull
    private Long deadline;

    /** 起数 */
    @JsonProperty("lastIndex")
    @Range(min=0,max=100000000)
    private BigDecimal lastIndex;

    /** 止数 */
    @JsonProperty("currentIndex")
    @Range(min=0,max=100000000)
    private BigDecimal currentIndex;

    /** 倍率 */
    @JsonProperty("multiple")
    @Range(min=0,max=100)
    private BigDecimal multiple;

    /** 损耗 */
    @JsonProperty("loss")
    @Range(min=0,max=100000000)
    private BigDecimal loss;

    /** 数量 */
    @JsonProperty("num")
    @NotBlank(message = "数量不能为空")
    @Pattern(regexp = "(([1-9]{1}\\d{0,7})|(0{1}))(\\.\\d{0,2})?",message = "数量格式不正确")
    private String num;

    /** 单价 */
    @JsonProperty("price")
    @NotBlank(message = "单价不能为空")
    @Pattern(regexp = "(([1-9]{1}\\d{0,5})|(0{1}))(\\.\\d{0,4})?",message = "金额格式不正确")
    private String price;

    /** 金额 */
    @JsonProperty("total")
    @NotBlank(message = "金额不能为空")
    @Pattern(regexp = "(([1-9]{1}\\d{0,7})|(0{1}))(\\.\\d{0,2})?",message = "金额格式不正确")
    private String total;
}