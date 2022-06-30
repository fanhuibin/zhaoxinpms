package com.zhaoxinms.payment.entity.vo;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * 在线支付订单视图对象 payment_order
 *
 * @author cycberform
 * @date 2022-03-16
 */
@Data
@ApiModel("在线支付订单视图对象")
public class PaymentOrderVo {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 支付金额
     */
    @Excel(name = "支付金额")
    @ApiModelProperty("支付金额")
    private String amount;
    
    @ApiModelProperty("费用说明")
    private String subject;
    
    /**
     * 支付方式
     */
    @ApiModelProperty("支付方式")
    private String wayCode;

    /**
     * 支付状态: 0-订单生成, 1-支付中, 2-支付成功, 3-支付失败, 4-已撤销, 5-已退款, 6-订单关闭
     */
    @Excel(name = "支付状态: 0-订单生成, 1-支付中, 2-支付成功, 3-支付失败, 4-已撤销, 5-已退款, 6-订单关闭")
    @ApiModelProperty("支付状态: 0-订单生成, 1-支付中, 2-支付成功, 3-支付失败, 4-已撤销, 5-已退款, 6-订单关闭")
    private Integer state;
    
    private String stateName;

    /**
     * 渠道用户标识,如微信openId,支付宝账号
     */
    @Excel(name = "渠道用户标识,如微信openId,支付宝账号")
    @ApiModelProperty("渠道用户标识,如微信openId,支付宝账号")
    private String openId;

    /**
     * 退款状态: 0-未发生实际退款, 1-部分退款, 2-全额退款
     */
    @Excel(name = "退款状态: 0-未发生实际退款, 1-部分退款, 2-全额退款")
    @ApiModelProperty("退款状态: 0-未发生实际退款, 1-部分退款, 2-全额退款")
    private Integer refundState;
    
    private String refundStateName;

    /**
     * 退款次数
     */
    @Excel(name = "退款次数")
    @ApiModelProperty("退款次数")
    private Long refundTimes;

    /**
     * 退款总金额
     */
    @Excel(name = "退款总金额")
    @ApiModelProperty("退款总金额")
    private String refundAmount;

    /**
     * 渠道支付错误码
     */
    @Excel(name = "渠道支付错误码")
    @ApiModelProperty("渠道支付错误码")
    private String errCode;

    /**
     * 订单失效时间
     */
    @Excel(name = "订单失效时间")
    @ApiModelProperty("订单失效时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expiredTime;

    /**
     * 订单支付成功时间
     */
    @Excel(name = "订单支付成功时间")
    @ApiModelProperty("订单支付成功时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date successTime;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("订单来源")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String client;
}
