package com.zhaoxinms.workflow.business.entity.pagination;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;
import com.zhaoxinms.base.vo.Pagination;
import com.zhaoxinms.common.core.domain.BaseEntity;
import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;

import java.util.Date;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 报修工单业务对象 flow_repair
 *
 * @author ruoyi
 * @date 2022-01-06
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("报修工单业务对象")
public class FlowRepairPagination extends Pagination {
    
    @ApiModelProperty(value = "业主id", required = true)
    private String ownerId;

    @ApiModelProperty(value = "流程实例", required = true)
    @NotBlank(message = "流程实例不能为空", groups = { AddGroup.class, EditGroup.class })
    private String instanceId;

    @ApiModelProperty(value = "报修单号", required = true)
    @NotBlank(message = "报修单号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String no;

    @ApiModelProperty(value = "报修标题", required = true)
    @NotBlank(message = "报修标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String title;

    @ApiModelProperty(value = "报修电话", required = true)
    @NotBlank(message = "报修电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyPhone;

    @ApiModelProperty(value = "报修者姓名", required = true)
    @NotBlank(message = "报修者姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyName;

    @ApiModelProperty(value = "维修对象分类", required = true)
    @NotBlank(message = "维修对象分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyCategory;

    @ApiModelProperty(value = "报修的内容", required = true)
    @NotBlank(message = "报修的内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyContent;

    @ApiModelProperty(value = "报修商户编号")
    private String applyHouse;

    @ApiModelProperty(value = "报修时间")
    private Date applyTime;

    @ApiModelProperty(value = "报修图片")
    private String applyImg;

    @ApiModelProperty(value = "受理说明")
    private String appContent;

    @ApiModelProperty(value = "预约时间")
    private Date appointmentTime;

    @ApiModelProperty(value = "受理时间")
    private Date appTime;

    @ApiModelProperty(value = "指派维修人员")
    private String repairUser;

    @ApiModelProperty(value = "维修材料费用")
    private BigDecimal repairMaterialsFee;

    @ApiModelProperty(value = "维修服务费用")
    private BigDecimal repairServiceFee;

    @ApiModelProperty(value = "修理备注")
    private String repairContent;

    @ApiModelProperty(value = "回访状态")
    private String returnState;

    @ApiModelProperty(value = "回访结果")
    private String returnResult;

    @ApiModelProperty(value = "回访意见")
    private String returnRemark;

    @ApiModelProperty(value = "流程结束时间")
    private Date endTime;

    @ApiModelProperty(value = "优先级")
    private Integer priority;

    @ApiModelProperty(value = "流程状态", required = true)
    @NotBlank(message = "流程状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String state;

    @ApiModelProperty(value = "数据来源")
    private String client;
}
