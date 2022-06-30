package com.zhaoxinms.payment.model.paymentbill;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhaoxinms.util.ConstantsUtil;

@Data
public class PaymentBillListVO {
    /** 主键 */
    private String id;
    
    @JsonProperty("orderId")
    private String orderId;

    /** 资源id */
    @JsonProperty("resourceId")
    private String resourceId;

    /** 资源名 */
    @JsonProperty("resourceName")
    private String resourceName;

    @JsonProperty("feeItemId")
    private String feeItemId;

    /** 收费项名 */
    @JsonProperty("feeItemName")
    private String feeItemName;

    /** 缴费单对应的周期 */
    // 日期输出格式化
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty("beginDate")
    private Date beginDate;

    /** 缴费单对应的周期 */
    // 日期输出格式化
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty("endDate")
    private Date endDate;

    // 缴费限期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty("deadline")
    private Date deadline;

    /** 客户 */
    @JsonProperty("feeUser")
    private String feeUser;

    /** 起数 */
    @JsonProperty("lastIndex")
    private BigDecimal lastIndex;

    /** 止数 */
    @JsonProperty("currentIndex")
    private BigDecimal currentIndex;

    /** 倍率 */
    @JsonProperty("multiple")
    private BigDecimal multiple;

    /** 损耗 */
    @JsonProperty("loss")
    private BigDecimal loss;

    /** 数量 */
    @JsonProperty("num")
    private String num;

    /** 单价 */
    @JsonProperty("price")
    private String price;

    /** 金额 */
    @JsonProperty("total")
    private String total;

    /** 滞纳金 */
    @JsonProperty("lateFee")
    private String lateFee;

    /** 折扣 */
    @JsonProperty("discount")
    private String discount;

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
    
    /** 支付状态 */
    @JsonProperty("payState")
    private String payState;
    
    @JsonProperty("payStateName")   
    private String payStateName;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("payTime")
    private Date payTime;
    
    public String getPayStateName() {
        if(StringUtils.isEmpty(this.getPayState())) {
            return "";
        }
        if(this.getPayState().equals(""+ConstantsUtil.PAY_BILL_PAY_STATE_UNPAIED)) {
            return "待付款";
        }
        if(this.getPayState().equals(""+ConstantsUtil.PAY_BILL_PAY_STATE_PAYING)) {
            return "付款中";
        }
        return "";
    }
}