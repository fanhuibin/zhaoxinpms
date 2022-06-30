package com.zhaoxinms.payment.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@TableName("payment_contract")
public class PaymentContractEntity {
    /** 主键 */
    @TableId("ID")
    private String id;

    /** 资源id */
    @TableField("BLOCK_CODE")
    private String blockCode;

    /** 资源id */
    @TableField("RESOURCE_ID")
    private String resourceId;

    /** 资源名 */
    @TableField("RESOURCE_NAME")
    private String resourceName;
    
    /** 业主id */
    @TableField("OWNER_ID")
    private String ownerId;

    /**  */
    @TableField("RESOURCE_TYPE")
    private String resourceType;

    /** 使用状态 */
    @TableField("CONTRACT_TYPE")
    private String contractType;

    /** 开始使用时间 */
    @TableField("BEGIN_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("beginDate")
    private Date beginDate;

    /** 开始使用时间 */
    @TableField("PERIOD")
    private String period;

    /** 结束使用时间 */
    @TableField("END_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("endDate")
    private Date endDate;

    /** 公司名 */
    @TableField("company")
    private String company;
    
    /** 租户姓名/业主姓名 */
    @TableField("USER_NAME")
    private String userName;

    /** 租户身份证/业主身份证 */
    @TableField("USER_IDCARD")
    private String userIdcard;

    /** 联系方式 */
    @TableField("USER_PHONE")
    private String userPhone;

    /** 性别 */
    @TableField("USER_GENDER")
    private String userGender;

    /** 从事的行业 */
    @TableField("USER_TRADE")
    private String userTrade;

    /** 行业的详细描述 */
    @TableField("USER_TRADE_DETAIL")
    private String userTradeDetail;

    /** 描述 */
    @TableField("DESCRIPTION")
    private String description;

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
    private String deleteUserId;

    /** 删除时间 */
    @TableField("DELETE_TIME")
    private Date deleteTime;

}
