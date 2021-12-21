package com.zhaoxinms.payment.entity;

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
@TableName("payment_meter")
public class PaymentMeterEntity {
    /** 主键 */
    @TableId("id")
    @JsonProperty("id")
    private String id;

    /** 收费的资源 */
    @TableField("block_code")
    private String blockCode;

    /** 收费的资源 */
    @TableField("RESOURCE_ID")
    private String resourceId;

    /** 资源类型 */
    @TableField("RESOURCE_TYPE")
    private String resourceType;

    /** 资源名 */
    @TableField("RESOURCE_NAME")
    private String resourceName;

    /** 收费项id */
    @TableField("FEE_ITEM_ID")
    private String feeItemId;

    /** 收费项名 */
    @TableField("FEE_ITEM_NAME")
    private String feeItemName;

    /** 本期读表时间 */
    @TableField("CURRENT_INDEX_DATE")
    private Date currentIndexDate;

    /** 本期读数 */
    @TableField("CURRENT_INDEX")
    private String currentIndex;

    /** 上期读数 */
    @TableField("LAST_INDEX")
    private String lastIndex;

    /** 上期读表时间 */
    @TableField("LAST_INDEX_DATE")
    private Date lastIndexDate;

    /** 倍率 */
    @TableField("multiple")
    private String multiple;

    /** 损耗 */
    @TableField("loss")
    private String loss;

    /** 最终计算结果 */
    @TableField("result")
    private String result;

    /** 有效标志 */
    @TableField(value = "ENABLED_MARK", fill = FieldFill.INSERT)
    private Integer enabledMark;

    /** 创建用户 */
    @TableField(value = "CREATOR_USER_ID", fill = FieldFill.INSERT)
    private String creatorUserId;

    /** 创建时间 */
    @TableField(value = "CREATOR_TIME", fill = FieldFill.INSERT)
    private Date creatorTime;

    /** 修改用户 */
    @TableField(value = "LAST_MODIFY_USER_ID", fill = FieldFill.UPDATE)
    private String lastModifyUserId;

    /** 修改时间 */
    @TableField(value = "LAST_MODIFY_TIME", fill = FieldFill.UPDATE)
    private Date lastModifyTime;
}
