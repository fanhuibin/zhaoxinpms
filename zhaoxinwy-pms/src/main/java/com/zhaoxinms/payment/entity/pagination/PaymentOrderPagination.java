package com.zhaoxinms.payment.entity.pagination;

import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;
import com.zhaoxinms.base.vo.Pagination;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhaoxinms.common.core.domain.BaseEntity;

/**
 * 在线支付订单业务对象 payment_order
 *
 * @author cycberform
 * @date 2022-03-16
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("在线支付订单业务对象")
public class PaymentOrderPagination extends Pagination {

    @ApiModelProperty(value = "支付状态: 0-订单生成, 1-支付中, 2-支付成功, 3-支付失败, 4-已撤销, 5-已退款, 6-订单关闭", required = true)
    @NotNull(message = "支付状态: 0-订单生成, 1-支付中, 2-支付成功, 3-支付失败, 4-已撤销, 5-已退款, 6-订单关闭不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer state;

    @ApiModelProperty(value = "渠道用户标识,如微信openId,支付宝账号")
    private String openId;

    @ApiModelProperty(value = "退款状态: 0-未发生实际退款, 1-部分退款, 2-全额退款", required = true)
    @NotNull(message = "退款状态: 0-未发生实际退款, 1-部分退款, 2-全额退款不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer refundState;

    @ApiModelProperty(value = "渠道支付错误码")
    private String errCode;

    @ApiModelProperty(value = "订单失效时间")
    private Date expiredTime;

    @ApiModelProperty(value = "订单支付成功时间")
    private Date successTime;
    
    @ApiModelProperty(value = "用户id")
    private String userId;
    
    @ApiModelProperty(value = "类型")
    private String type;
    
    @ApiModelProperty(value = "支付方式")
    private String wayCode;
    
    @ApiModelProperty("付款时间范围")
    private List<Long> payTimeRange = new ArrayList<Long>();
}
