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
@TableName("payment_contract_fee")
public class PaymentContractFeeEntity {
    /** 主键 */
    @TableId("ID")
    private String id;

    /** 收费项id */
    @TableField("FEE_ITEM_ID")
    private String feeItemId;

    /** 资源id */
    @TableField("CONTRACT_ID")
    private String contractId;

    /** 收费开始时间 */
    @TableField("BEGIN_DATE")
    private Date beginDate;

    /** 收费结束时间 */
    @TableField("END_DATE")
    private Date endDate;

    /** 下次收费时间 */
    @TableField("NEXT_BILL_DATE")
    private Date nextBillDate;
    
    /** 收费次数 */
    @TableField("times")
    private int times;

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

    /** 删除用户 */
    @TableField("DELETE_USER_ID")
    @JsonProperty("delete_user_id")
    private String deleteUserId;

    /** 删除时间 */
    @TableField("DELETE_TIME")
    @JsonProperty("delete_time")
    private Date deleteTime;

}
