/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms
 * website：http://pms.zhaoxinms.com  wx： zhaoxinms
 *
 */
package com.zhaoxinms.payment.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.Date;

@Data
@TableName("payment_bill")
public class PaymentBillEntity {
    /** 主键 */
    @TableId("ID")
    private String id;

    /** 账单分类 */
    @TableField("TYPE")
    private String type;

    /** 资源名 */
    @TableField("RESOURCE_NAME")
    private String resourceName;

    /** 资源id */
    @TableField("RESOURCE_ID")
    private String resourceId;

    /** 合同 */
    @TableField("CONTRACT_ID")
    private String contractId;
    
    /** 订单 */
    @TableField("ORDER_ID")
    private String orderId;

    /** 收费项id */
    @TableField("FEE_ITEM_ID")
    private String feeItemId;

    /** 收费项名 */
    @TableField("FEE_ITEM_NAME")
    private String feeItemName;

    /** 客户 */
    @TableField("FEE_USER")
    private String feeUser;

    /** 账单对应的周期 */
    @TableField("BEGIN_DATE")
    private Date beginDate;

    /** 账单对应的周期 */
    @TableField("END_DATE")
    private Date endDate;

    /** 缴费截止时间 */
    @TableField("deadline")
    private Date deadline;

    /** 起数 */
    @TableField("LAST_INDEX")
    private String lastIndex;

    /** 止数 */
    @TableField("CURRENT_INDEX")
    private String currentIndex;

    /** 倍率 */
    @TableField("MULTIPLE")
    private String multiple;

    /** 损耗 */
    @TableField("LOSS")
    private String loss;

    /** 数量 */
    @TableField("NUM")
    private String num;

    /** 单价 */
    @TableField("PRICE")
    private String price;

    /** 总价 */
    @TableField("TOTAL")
    private String total;

    /** 滞纳金 */
    @TableField("LATE_FEE")
    private String lateFee;

    /** 折扣 */
    @TableField("DISCOUNT")
    private String discount;

    /** 应收 */
    @TableField("RECEIVABLE")
    private String receivable;

    /** 流水号 */
    @TableField("PAY_LOG_NO")
    private String payLogNo;

    /** 流水表id */
    @TableField("PAY_LOG_ID")
    private String payLogId;

    /**  */
    @TableField("PAY_TIME")
    private Date payTime;

    @TableField("PAY_STATE")
    private String payState;
    
    @TableField("REFUND_STATE")
    private Integer refundState;
    
    @TableField("REFUND_TIMES")
    private Integer refundTimes;
    
    @TableField("REFUND_AMOUNT")
    private String refundAmount;
    
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

    /** 删除用户 */
    @TableField("DELETE_USER_ID")
    private String deleteUserId;

    /** 删除时间 */
    @TableField("DELETE_TIME")
    private Date deleteTime;

    /** 有效标志 */
    @TableField(value = "ENABLED_MARK", fill = FieldFill.INSERT)
    private Integer enabledMark;

}
