package com.zhaoxinms.payment.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@TableName("payment_pay_log")
public class PaymentPayLogEntity {
    /** 主键 */
    @TableId("ID")
    @JsonProperty("id")
    private String id;

    /** 费用类型（临时费、商铺费、押金） */
    @TableField("resource_name")
    private String resourceName;

    /** 费用类型（临时费、商铺费、押金） */
    @TableField("FEE_TYPE")
    private String feeType;

    /** 支付和退款 */
    @TableField("TYPE")
    private String type;

    /** 付款流水号 */
    @TableField("PAY_NO")
    private String payNo;

    /** 付款时间 */
    @TableField("PAY_TIME")
    private Date payTime;

    /** 支付方式 */
    @TableField("PAY_METHOD")
    private String payMethod;

    /** 滞纳金 */
    @TableField("LATE_FEE_MONEY")
    private String lateFeeMoney;

    /** 收费项合计金额 */
    @TableField("ITEM_TOTAL_MONEY")
    private String itemTotalMoney;

    /** 应收金额 */
    @TableField("RECEIVABLE_MONEY")
    private String receivableMoney;

    /** 优惠金额 */
    @TableField("DISCOUNT_MONEY")
    private String discountMoney;

    /** 实际支付金额 */
    @TableField("PAY_MONEY")
    private String payMoney;

    /** 预付款支付金额 */
    @TableField("PRE_PAY_MONEY")
    private String prePayMoney;

    /** 找零 */
    @TableField("CHANGE_MONEY")
    private String changeMoney;

    /** 结存 */
    @TableField("PRE_SAVE_MONEY")
    private String preSaveMoney;

    /** 描述 */
    @TableField("name")
    private String name;

    /** 备注 */
    @TableField("comment")
    private String comment;

    /** 支付凭证 */
    @TableField("CERTIFICATE")
    private String certificate;

    /** 支付凭证 */
    @TableField("PAYER_IDCARD")
    private String payerIdcard;

    /** 支付凭证 */
    @TableField("PAYER_NAME")
    private String payerName;

    /** 支付凭证 */
    @TableField("PAYER_PHONE")
    private String payerPhone;

    /** 创建用户 */
    @TableField(value = "CREATOR_USER_ID", fill = FieldFill.INSERT)
    private String creatorUserId;

    /** 创建时间 */
    @TableField(value = "CREATOR_TIME", fill = FieldFill.INSERT)
    private Date creatorTime;

    /** 业务id */
    @TableField(value = "business_id")
    private String businessId;
}
