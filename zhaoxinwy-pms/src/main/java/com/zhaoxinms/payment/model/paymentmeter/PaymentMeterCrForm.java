package com.zhaoxinms.payment.model.paymentmeter;

import lombok.Data;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentMeterCrForm {
    /** 收费的资源 */
    @NotBlank
    @JsonProperty("resourceId")
    private String resourceId;

    /** 资源类型 */
    @JsonProperty("resourceType")
    private String resourceType;

    /** 资源名 */
    @JsonProperty("resourceName")
    private String resourceName;

    /** 收费项id */
    @NotBlank
    @JsonProperty("feeItemId")
    private String feeItemId;

    /** 收费项名 */
    @JsonProperty("feeItemName")
    private String feeItemName;

    /** 本期读表时间 */
    @Min(value = 1, message = "当前读数不能为空")
    @JsonProperty("currentIndexDate")
    private Long currentIndexDate;

    /** 本期读数 */
    @NotBlank(message = "本期度数不能为空")
    @JsonProperty("currentIndex")
    @Pattern(regexp = "(([1-9]{1}\\d{0,7})|(0{1}))(\\.\\d{0,2})?",message = "读数格式不正确")
    private String currentIndex;

    /** 上期读数 */
    @NotBlank(message = "上期度数不能为空")
    @JsonProperty("lastIndex")
    @Pattern(regexp = "(([1-9]{1}\\d{0,7})|(0{1}))(\\.\\d{0,2})?",message = "读数格式不正确")
    private String lastIndex;

    /** 上期读表时间 */
    @JsonProperty("lastIndexDate")
    private Long lastIndexDate;

    /** 倍率 */
    @NotBlank(message = "倍率不能为空")
    @JsonProperty("multiple")
    @Pattern(regexp = "(([1-9]{1}\\d{0,1})|(0{1}))(\\.\\d{0,2})?",message = "倍率格式不正确")
    private String multiple;

    /** 损耗 */
    @NotBlank(message = "损耗不能为空")
    @JsonProperty("loss")
    @Pattern(regexp = "(([1-9]{1}\\d{0,7})|(0{1}))(\\.\\d{0,2})?",message = "损耗格式不正确")
    private String loss;

    /** 最终数量 */
    @JsonProperty("result")
    @Pattern(regexp = "(([1-9]{1}\\d{0,7})|(0{1}))(\\.\\d{0,2})?",message = "数量格式不正确")
    private String result;

}