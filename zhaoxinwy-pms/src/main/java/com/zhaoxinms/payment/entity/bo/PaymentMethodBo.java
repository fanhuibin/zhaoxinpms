package com.zhaoxinms.payment.entity.bo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zhaoxinms.common.core.mybatisplus.BaseEntity;
import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付方式业务对象 payment_method
 *
 * @author fanhuibin
 * @date 2022-04-15
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("支付方式业务对象")
public class PaymentMethodBo extends BaseEntity {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;

    /**
     * 编码
     */
    @ApiModelProperty(value = "编码", required = true)
    @NotBlank(message = "编码不能为空", groups = {AddGroup.class, EditGroup.class})
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank(message = "名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String name;

    /**
     * 数据端
     */
    @ApiModelProperty(value = "数据端", required = true)
    @NotBlank(message = "数据端不能为空", groups = {AddGroup.class, EditGroup.class})
    private String client;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long sortField;

}
