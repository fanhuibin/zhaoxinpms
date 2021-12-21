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
@TableName("payment_pre_account")
public class PaymentPreAccountEntity {
    /** 主键 */
    @TableId("ID")
    private String id;

    /** 资源名 */
    @TableField("RESOURCE_NAME")
    private String resourceName;

    /** 资源id */
    @TableField("RESOURCE_ID")
    private String resourceId;

    /** 商业区 */
    @TableField("BLOCK")
    private String block;

    /** 收费项目 */
    @TableField("FEE_ITEM_ID")
    private String feeItemId;

    /** 收费项目 */
    @TableField("USE_FEE_ITEM")
    private String useFeeItem;

    /** 账户金额 */
    @TableField("AMT")
    private String amt;

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
