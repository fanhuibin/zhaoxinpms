package com.zhaoxinms.payment.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@TableName("payment_meter_index")
public class PaymentMeterIndexEntity {
    /** 主键 */
    @TableId("ID")
    @JsonProperty("id")
    private String id;

    /** 商业区编码 */
    @TableField("BLOCK_CODE")
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

    /** 读数时间 */
    @TableField("CURRENT_INDEX_DATE")
    private Date currentIndexDate;

    /** 当前表读数 */
    @TableField("CURRENT_INDEX")
    private String currentIndex;

    /** 倍率 */
    @TableField("multiple")
    private String multiple;

    /** 创建时间 */
    @TableField("CREATOR_TIME")
    private Date creatorTime;

    /** 创建用户 */
    @TableField("CREATOR_USER_ID")
    private String creatorUserId;

    /** 修改用户 */
    @TableField("LAST_MODIFY_USER_ID")
    private String lastModifyUserId;

    /** 修改时间 */
    @TableField("LAST_MODIFY_TIME")
    private Date lastModifyTime;

}
