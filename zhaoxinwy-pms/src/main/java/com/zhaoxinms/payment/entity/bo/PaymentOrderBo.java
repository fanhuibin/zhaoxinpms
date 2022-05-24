package com.zhaoxinms.payment.entity.bo;

import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhaoxinms.common.core.mybatisplus.BaseEntity;

/**
 * 在线支付订单业务对象 payment_order
 *
 * @author cycberform
 * @date 2022-03-16
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("在线支付订单业务对象")
public class PaymentOrderBo extends BaseEntity {

    /**
     * 支付订单号
     */
    @ApiModelProperty(value = "支付订单号")
    private String id;

    /**
     * 应用ID
     */
    @ApiModelProperty(value = "应用ID", required = true)
    @NotBlank(message = "应用ID不能为空", groups = { AddGroup.class })
    private String appId;

    /**
     * 支付方式代码
     */
    @ApiModelProperty(value = "支付方式代码", required = true)
    @NotBlank(message = "支付方式代码不能为空", groups = { AddGroup.class })
    private String wayCode;

    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额", required = true)
    @NotNull(message = "支付金额不能为空", groups = { AddGroup.class })
    private BigDecimal amount;

    /**
     * 支付状态: 0-订单生成, 1-支付中, 2-支付成功, 3-支付失败, 4-已撤销, 5-已退款, 6-订单关闭
     */
    @ApiModelProperty(value = "支付状态: 0-订单生成, 1-支付中, 2-支付成功, 3-支付失败, 4-已撤销, 5-已退款, 6-订单关闭", required = true)
    @NotNull(message = "支付状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer state;

    /**
     * 商品标题
     */
    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "商品标题不能为空", groups = { AddGroup.class })
    private String subject;

    /**
     * 商品描述信息
     */
    @ApiModelProperty(value = "描述信息", required = true)
    @NotBlank(message = "描述信息不能为空", groups = { AddGroup.class })
    private String body;

    /**
     * 渠道用户标识,如微信openId,支付宝账号
     */
    @ApiModelProperty(value = "渠道用户标识,如微信openId,支付宝账号")
    private String openId;

    /**
     * 缴费单订单号
     */
    @ApiModelProperty(value = "缴费单订单号")
    private String billId;

    /**
     * 退款状态: 0-未发生实际退款, 1-部分退款, 2-全额退款
     */
    @ApiModelProperty(value = "退款状态: 0-未发生实际退款, 1-部分退款, 2-全额退款", required = true)
    @NotNull(message = "退款状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer refundState;

    /**
     * 退款次数
     */
    @ApiModelProperty(value = "退款次数", required = true)
    @NotNull(message = "退款次数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long refundTimes;

    /**
     * 退款总金额
     */
    @ApiModelProperty(value = "退款总金额", required = true)
    @NotBlank(message = "退款总金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private String refundAmount;

    /**
     * 渠道支付错误码
     */
    @ApiModelProperty(value = "渠道支付错误码")
    private String errCode;

    /**
     * 渠道支付错误描述
     */
    @ApiModelProperty(value = "渠道支付错误描述")
    private String errMsg;

    /**
     * 订单失效时间
     */
    @ApiModelProperty(value = "订单失效时间")
    private Date expiredTime;

    /**
     * 订单支付成功时间
     */
    @ApiModelProperty(value = "订单支付成功时间")
    private Date successTime;


}
