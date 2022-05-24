package com.zhaoxinms.payment.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 支付方式视图对象 payment_method
 *
 * @author fanhuibin
 * @date 2022-04-15
 */
@Data
@ApiModel("支付方式视图对象")
public class PaymentMethodVo {

	private static final long serialVersionUID = 1L;

	private String id;

    /**
     * 编码
     */
	@Excel(name = "编码")
	@ApiModelProperty("编码")
	private String code;

    /**
     * 名称
     */
	@Excel(name = "名称")
	@ApiModelProperty("名称")
	private String name;

    /**
     * 数据端
     */
	@Excel(name = "数据端")
	@ApiModelProperty("数据端")
	private String client;

    /**
     * 排序
     */
	@Excel(name = "排序")
	@ApiModelProperty("排序")
	private Long sortField;
}
