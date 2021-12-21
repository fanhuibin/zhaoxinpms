package com.zhaoxinms.payment.model.paymentpaylog;

import lombok.Data;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentPayLogListVO {
    /** 主键 */
    private String id;

    /** 付款流水号 */
    @JsonProperty("payNo")
    private String payNo;

    /** 付款时间 */
    // 日期输出格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("payTime")
    private Date payTime;

    /** 付款人 */
    @JsonProperty("payerName")
    private String payerName;

    /** 支付方式 */
    @JsonProperty("payMethod")
    private String payMethod;

    /** 滞纳金 */
    @JsonProperty("lateFeeMoney")
    private String lateFeeMoney;

    /** 收费项合计金额 */
    @JsonProperty("itemTotalMoney")
    private String itemTotalMoney;

    /** 应收金额 */
    @JsonProperty("receivableMoney")
    private String receivableMoney;

    /** 优惠金额 */
    @JsonProperty("discountMoney")
    private String discountMoney;

    /** 实际支付金额 */
    @JsonProperty("payMoney")
    private String payMoney;

    /** 预付款支付金额 */
    @JsonProperty("prePayMoney")
    private String prePayMoney;

    /** 找零 */
    @JsonProperty("changeMoney")
    private String changeMoney;

    /** 结存 */
    @JsonProperty("preSaveMoney")
    private String preSaveMoney;

    /** 支付和退款 */
    @JsonProperty("type")
    private String type;

    /** 费用类型 */
    @JsonProperty("feeType")
    private String feeType;

    /** 付款收费内容 */
    @JsonProperty("name")
    private String name;

    /** 备注 */
    @JsonProperty("comment")
    private String comment;

    /** 支付凭证 */
    @JsonProperty("certificate")
    private String certificate;
}