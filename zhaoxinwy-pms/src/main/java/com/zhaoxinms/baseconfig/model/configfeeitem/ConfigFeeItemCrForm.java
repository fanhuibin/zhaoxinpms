package com.zhaoxinms.baseconfig.model.configfeeitem;


import lombok.Data;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ConfigFeeItemCrForm  {
    /** 收费项分类 */
    @JsonProperty("type")
    private String type;

    /** 收费项目名 */
    @JsonProperty("name")
    @NotBlank(message = "收费项名不能为空")
    @Length(min = 0, max = 100)
    private String name;

    /** 单价 */
    @Range(min=0,max=1000000)
    @JsonProperty("price")
    private BigDecimal price;

    /** 数量 */
    @JsonProperty("numType")
    private String numType;

    /** 费用周期(月为单位) */
    @JsonProperty("period")
    private Integer period;

    /** 费用计算公式 */
    @JsonProperty("formula")
    private String formula;

    /** 自定义 */
    @JsonProperty("formulaExpression")
    private String formulaExpression;

    /** 是否产生滞纳金 */
    @JsonProperty("lateFeeEnable")
    private Integer lateFeeEnable;

    /** 滞纳金延迟多久收 */
    @JsonProperty("lateFeeDays")
    private Integer lateFeeDays;

    /** 滞纳金比例 */
    @JsonProperty("lateFeeRate")
    private String lateFeeRate;
}