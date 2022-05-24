package com.zhaoxinms.payment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 支付方式对象 payment_method
 *
 * @author fanhuibin
 * @date 2022-04-15
 */
@Data
@Accessors(chain = true)
@TableName("payment_method")
public class PaymentMethod {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private String id;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 数据端
     */
    private String client;
    /**
     * 排序
     */
    @TableField("sort")
    private Long sortField;

}
