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
@TableName("payment_pre")
public class PaymentPreEntity {
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

    @TableField("TYPE")
    private String type;

    @TableField("USE_FEE_ITEM")
    private String useFeeItem;

    /** 收费项名 */
    @TableField("FEE_ITEM_NAME")
    private String feeItemName;

    /** 收费项id */
    @TableField("FEE_ITEM_ID")
    private String feeItemId;

    /** 客户姓名 */
    @TableField("FEE_USER")
    private String feeUser;

    /**  */
    @TableField("AMT")
    private String amt;

    /** 付款方式 */
    @TableField("PAY_TYPE")
    private String payType;

    /** 收款人 */
    @TableField("OPERATE_USER")
    private String operateUser;

    /** 收款时间 */
    @TableField("OPERATE_TIME")
    private Date operateTime;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 支付单号 */
    @TableField("PAY_NO")
    private String payNo;

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
