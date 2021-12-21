package com.zhaoxinms.baseconfig.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@TableName("config_fee_item")
public class ConfigFeeItemEntity  {
    /** 主键 */
    @TableId("ID")
    @JsonProperty("id")
    private String id;

    /** 收费项分类 */
    @TableField("TYPE")
    private String type;

    /** 收费项目名 */
    @TableField("NAME")
    private String name;

    /** 单价 */
    @TableField("PRICE")
    private String price;

    /** 数量 */
    @TableField("NUM_TYPE")
    private String numType;

    /** 费用周期(月为单位) */
    @TableField("PERIOD")
    private String period;

    /** 费用计算公式 */
    @TableField("FORMULA")
    private String formula;

    /** 自定义 */
    @TableField("FORMULA_EXPRESSION")
    private String formulaExpression;
    
    /** 代码生成类型 */
    @TableField("GENERATE_TYPE")
    private String generateType;

    /** 是否产生滞纳金 */
    @TableField("LATE_FEE_ENABLE")
    private String lateFeeEnable;

    /** 滞纳金延迟多久收 */
    @TableField("LATE_FEE_DAYS")
    private String lateFeeDays;

    /** 滞纳金比例 */
    @TableField("LATE_FEE_RATE")
    private String lateFeeRate;

    /** 创建时间 */
    @TableField(value="CREATOR_TIME",fill= FieldFill.INSERT)
    @JsonProperty("creatortime")
    private Date creatorTime;

    /** 创建用户 */
    @TableField(value="CREATOR_USER_ID",fill= FieldFill.INSERT)
    @JsonProperty("creatoruserid")
    private String creatorUserId;

    /** 修改用户 */
    @TableField(value="LAST_MODIFY_USER_ID",fill= FieldFill.UPDATE)
    @JsonProperty("lastmodifyuserid")
    private String lastModifyUserId;

    /** 修改时间 */
    @TableField(value="LAST_MODIFY_TIME",fill= FieldFill.UPDATE)
    @JsonProperty("lastmodifytime")
    private Date lastModifyTime;

    /** 删除用户 */
    @TableField(value="DELETE_USER_ID")
    @JsonProperty("deleteuserid")
    private String deleteUserId;

    /** 删除时间 */
    @TableField("DELETE_TIME")
    @JsonProperty("deletetime")
    private Date deleteTime;

    /** 有效标志 */
    @TableField("ENABLED_MARK")
    @JsonProperty("enabledmark")
    private Integer enabledMark = 1;

}
