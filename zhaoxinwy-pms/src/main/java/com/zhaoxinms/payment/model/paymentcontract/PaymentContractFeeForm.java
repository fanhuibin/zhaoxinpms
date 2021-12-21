package com.zhaoxinms.payment.model.paymentcontract;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class PaymentContractFeeForm {
    /** 主键 */
    private String id;

    /** 收费项id */
    private String feeItemId;

    /** 收费项名 */
    private String name;

    /** 资源id */
    private String contractId;

    private Long beginDate;

    private Long endDate;

    /** 单价 */
    private String price;
}
