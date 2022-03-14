package com.zhaoxinms.payment.model.paymentbill;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PaymentBillInfoVO extends PaymentBillCrForm {
    /** 主键 */
    private String id;
    /** 应收 */
    @JsonProperty("receivable")
    private String receivable;
    
    private Integer refundState;
    private Integer refundTimes;
    private String refundAmount;

    /** 流水记录 */
    @JsonProperty("payLogId")
    private String payLogId;

    /** 流水单号 */
    @JsonProperty("payLogNo")
    private String payLogNo;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("payTime")
    private Date payTime;
    
}