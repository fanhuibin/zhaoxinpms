package com.zhaoxinms.baseconfig.model.configfeeitem;


import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ConfigFeeItemListVO{
    /** 主键 */
    private String id;

    /** 收费项分类 */
    @JsonProperty("type")
    private String type;

    /** 收费项目名 */
    @JsonProperty("name")
    private String name;

    /** 单价 */
    @JsonProperty("price")
    private String price;

    /** 数量 */
    @JsonProperty("numType")
    private String numType;

    /** 费用周期(月为单位) */
    @JsonProperty("period")
    private Long period;

    /** 费用计算公式 */
    @JsonProperty("formula")
    private String formula;

    /** 自定义 */
    @JsonProperty("formulaExpression")
    private String formulaExpression;

    /** 是否产生滞纳金 */
    @JsonProperty("lateFeeEnable")
    private Long lateFeeEnable;

    /** 滞纳金延迟多久收 */
    @JsonProperty("lateFeeDays")
    private Long lateFeeDays;

    /** 滞纳金比例 */
    @JsonProperty("lateFeeRate")
    private String lateFeeRate;

}