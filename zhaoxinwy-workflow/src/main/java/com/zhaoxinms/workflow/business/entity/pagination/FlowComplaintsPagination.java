package com.zhaoxinms.workflow.business.entity.pagination;

import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;
import com.zhaoxinms.base.vo.Pagination;
import java.util.Date;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhaoxinms.common.core.domain.BaseEntity;

/**
 * 投诉工单业务对象 flow_complaints
 *
 * @author ruoyi
 * @date 2022-01-24
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("投诉工单业务对象")
public class FlowComplaintsPagination extends Pagination {

    @ApiModelProperty(value = "流程id")
    private String instanceId;
    
    @ApiModelProperty(value = "ownerid")
    private String ownerId;

    @ApiModelProperty(value = "单号", required = true)
    @NotBlank(message = "单号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String no;

    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String title;

    @ApiModelProperty(value = "申请人电话", required = true)
    @NotBlank(message = "申请人电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyPhone;

    @ApiModelProperty(value = "申请人姓名", required = true)
    @NotBlank(message = "申请人姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyName;

    @ApiModelProperty(value = "分类", required = true)
    @NotBlank(message = "分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyCategory;

    @ApiModelProperty(value = "投诉内容", required = true)
    @NotBlank(message = "投诉内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyContent;

    @ApiModelProperty(value = "投诉人的诉求")
    private String applyRequirements;

    @ApiModelProperty(value = "申请的商户编号")
    private String applyHouse;

    @ApiModelProperty(value = "申请时间")
    private Date applyTime;

    @ApiModelProperty(value = "图片")
    private String applyImg;

    @ApiModelProperty(value = "受理说明")
    private String appContent;

    @ApiModelProperty(value = "受理时间")
    private Date appTime;

    @ApiModelProperty(value = "问题确认备注")
    private String confirmContent;

    @ApiModelProperty(value = "领单人员")
    private String assigneeUser;

    @ApiModelProperty(value = "领单人")
    private String aggigneeUserName;

    @ApiModelProperty(value = "处理备注")
    private String assigneeContent;

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
