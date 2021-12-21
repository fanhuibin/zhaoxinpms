package com.zhaoxinms.payment.model.paymentpaylog;

import lombok.Data;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentPayLogCrForm {
    /** 付款时间 */
    @JsonProperty("payTime")
    private Long payTime;

    /** 付款流水号 */
    @JsonProperty("payNo")
    private String payNo;

    /** 支付方式 */
    @JsonProperty("payMethod")
    @NotBlank
    private String payMethod;

    /** 滞纳金 */
    @JsonProperty("itemTotalMoney")
    private String itemTotalMoney;

    /** 滞纳金 */
    @JsonProperty("lateFeeMoney")
    @NotBlank
    private String lateFeeMoney;

    /** 优惠金额 */
    @JsonProperty("discountMoney")
    private String discountMoney;

    /** 应收金额 */
    @JsonProperty("receivableMoney")
    @NotBlank
    private String receivableMoney;

    /** 支付金额 */
    @JsonProperty("payMoney")
    @NotBlank
    private String payMoney;

    /** 是否使用余额账户 */
    private boolean usePrePay;

    /** 是否转存 */
    private boolean usePreSave;

    /** 余额支付金额 */
    @JsonProperty("prePayMoney")
    @NotBlank
    private String prePayMoney;

    /** 找零 */
    @JsonProperty("changeMoney")
    @NotBlank
    private String changeMoney;

    /** 转存金额 */
    @JsonProperty("preSaveMoney")
    @NotBlank
    private String preSaveMoney;

    private String canUsePrePayMoney;

    /** 支付和退款 */
    @JsonProperty("type")
    private String type;

    /** 描述 */
    @JsonProperty("describe")
    private String describe;

    /** 支付凭证 */
    @JsonProperty("certificate")
    private String certificate;
}