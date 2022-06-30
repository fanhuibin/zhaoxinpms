package com.zhaoxinms.payment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhaoxinms.common.core.mybatisplus.BaseEntity;

/**
 * 在线支付订单对象 payment_order
 *
 * @author cycberform
 * @date 2022-03-16
 */
@Data
@Accessors(chain = true)
@TableName("payment_order")
public class PaymentOrder extends BaseEntity {

    private static final long serialVersionUID=1L;
    public static final int STATE_INIT = 0; //订单生成
    public static final int STATE_ING = 1; //支付中
    public static final int STATE_SUCCESS = 2; //支付成功
    //目前不支持这几个状态，后期会添加支持
    public static final int STATE_FAIL = 3; //支付失败
    //public static final int STATE_CANCEL = 4; //已撤销
    //public static final int STATE_REFUND = 5; //已退款
    public static final int STATE_CLOSED = 6; //订单关闭
    
    public static final String ERROR_CODE_ERROR = "error"; //错误
    public static final String ERROR_CODE_OK = "ok"; //正常
    
    public static final String WAY_CODE_WX = "miniapp_weixin"; //微信支付,与数据库的要对应
    
    public static final int REFUND_STATE_NONE = 0;//未发生实际退款
    public static final int REFUND_STATE_PART = 1;//部分退款
    public static final int REFUND_STATE_ALL = 2;//全额退款
    
    //订单类型，bill类型，parking类型，其他类型。
    public static final String TYPE_BILL = "bill";
    public static final String TYPE_PARK = "park";

    /**
     * 支付订单号
     */
    @TableId(value = "id")
    private String id;
    /**
     * 应用ID
     */
    private String appId;
    /**
     * 类型
     */
    private String type;
    /**
     * 支付方式代码
     */
    private String wayCode;
    /**
     * 支付金额
     */
    private BigDecimal amount;
    /**
     * 支付状态: 0-订单生成, 1-支付中, 2-支付成功, 3-支付失败, 4-已撤销, 5-已退款, 6-订单关闭
     */
    private Integer state;
    /**
     * 商品标题
     */
    private String subject;
    /**
     * 商品描述信息
     */
    private String body;
    /**
     * 订单参数
     */
    private String param;
    /**
     * 渠道用户标识,如微信openId,支付宝账号
     */
    private String openId;

    /**
     * 退款状态: 0-未发生实际退款, 1-部分退款, 2-全额退款
     */
    private Integer refundState;
    /**
     * 退款次数
     */
    private Long refundTimes;
    /**
     * 退款总金额
     */
    private BigDecimal refundAmount;
    /**
     * 渠道支付错误码
     */
    private String errCode;
    /**
     * 渠道支付错误描述
     */
    private String errMsg;
    /**
     * 订单失效时间
     */
    private Date expiredTime;
    /**
     * 订单支付成功时间
     */
    private Date successTime;
    
    /**
     * 生成订单的端（pc,业主小程序等）
     */
    private int client;
    
    /**
     * 流水记录的id
     */
    private String logId;
    
    /**
     * 用户id
     */
    private String userId;
}
