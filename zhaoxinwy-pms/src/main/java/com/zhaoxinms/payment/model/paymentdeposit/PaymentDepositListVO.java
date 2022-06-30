package com.zhaoxinms.payment.model.paymentdeposit;

import lombok.Data;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentDepositListVO {
    /** 主键 */
    private String id;

    /** 资源名 */
    @JsonProperty("resourceName")
    private String resourceName;

    /** 商业区 */
    @JsonProperty("block")
    private String block;

    /** 费用id */
    @JsonProperty("feeItemId")
    private String feeItemId;

    /** 费用id */
    @JsonProperty("feeItemName")
    private String feeItemName;

    /** 客户姓名 */
    @JsonProperty("feeUser")
    private String feeUser;

    /**  */
    @JsonProperty("amt")
    private String amt;

    /** 付款方式 */
    @JsonProperty("payType")
    private String payType;

    /** 备注 */
    @JsonProperty("remark")
    private String remark;

    /** 收取人 */
    @JsonProperty("operateUser")
    private String operateUser;

    /** 收取时间 */
    @JsonProperty("operateTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date operateTime;

    /** 退款人 */
    @JsonProperty("refundUser")
    private String refundUser;

    /** 退款时间 */
    @JsonProperty("refundTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date refundTime;

    /** 状态 */
    private String state;
    
    /** 支付流水号 */
    private String payNo;
    
    /** 退款流水号 */
    private String refundNo;
}