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
@TableName("payment_deposit")
public class PaymentDepositEntity {
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

    /**  */
    @TableField("FEE_ITEM_ID")
    private String feeItemId;

    /** 收费项名 */
    @TableField("FEE_ITEM_NAME")
    private String feeItemName;

    /** 客户姓名 */
    @TableField("FEE_USER")
    private String feeUser;

    /**  */
    @TableField("AMT")
    private String amt;

    /** 付款方式 */
    @TableField("PAY_TYPE")
    private String payType;

    /** 退款方式 */
    @TableField("REFUND_TYPE")
    private String refundType;

    /** 收款人 */
    @TableField("OPERATE_USER")
    private String operateUser;

    /** 收款时间 */
    @TableField("OPERATE_TIME")
    private Date operateTime;

    /** 退款人 */
    @TableField("REFUND_USER")
    private String refundUser;

    /** 退款时间 */
    @TableField("REFUND_TIME")
    private Date refundTime;

    /** 付款单号 */
    @TableField("PAY_NO")
    private String payNo;

    /** 退款单号 */
    @TableField("REFUND_NO")
    private String refundNo;

    /** 状态 */
    @TableField("STATE")
    private String state;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

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
