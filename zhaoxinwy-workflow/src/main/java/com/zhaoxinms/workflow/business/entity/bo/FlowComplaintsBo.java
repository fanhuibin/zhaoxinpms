package com.zhaoxinms.workflow.business.entity.bo;

import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;
import com.zhaoxinms.workflow.engine.entity.ProcessEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhaoxinms.common.core.mybatisplus.BaseEntity;

/**
 * 投诉工单业务对象 flow_complaints
 *
 * @author ruoyi
 * @date 2022-01-24
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("投诉工单业务对象")
public class FlowComplaintsBo extends ProcessEntity {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 单号
     */
    @ApiModelProperty(value = "单号", required = true)
    @NotBlank(message = "单号不能为空", groups = { EditGroup.class })
    private String no;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空", groups = { EditGroup.class })
    private String title;

    /**
     * 申请人电话
     */
    @ApiModelProperty(value = "申请人电话", required = true)
    @NotBlank(message = "申请人电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyPhone;

    /**
     * 申请人姓名
     */
    @ApiModelProperty(value = "申请人姓名", required = true)
    @NotBlank(message = "申请人姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyName;

    /**
     * 分类
     */
    @ApiModelProperty(value = "分类", required = true)
    @NotBlank(message = "分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyCategory;

    /**
     * 投诉内容
     */
    @ApiModelProperty(value = "投诉内容", required = true)
    @NotBlank(message = "投诉内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyContent;

    /**
     * 投诉人的诉求
     */
    @ApiModelProperty(value = "投诉人的诉求")
    private String applyRequirements;

    /**
     * 申请的商户编号
     */
    @ApiModelProperty(value = "申请的商户编号")
    private String applyHouse;

    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    private Date applyTime;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String applyImg;

    /**
     * 受理说明
     */
    @ApiModelProperty(value = "受理说明")
    private String appContent;

    /**
     * 受理时间
     */
    @ApiModelProperty(value = "受理时间")
    private Date appTime;

    /**
     * 问题确认备注
     */
    @ApiModelProperty(value = "问题确认备注")
    private String confirmContent;

    /**
     * 领单人员
     */
    @ApiModelProperty(value = "领单人员")
    private String assigneeUser;

    /**
     * 领单人
     */
    @ApiModelProperty(value = "领单人")
    private String assigneeUserName;

    /**
     * 处理备注
     */
    @ApiModelProperty(value = "处理备注")
    private String assigneeContent;

    /**
     * 回访状态
     */
    @ApiModelProperty(value = "回访状态")
    private String returnState;

    /**
     * 回访结果
     */
    @ApiModelProperty(value = "回访结果")
    private String returnResult;

    /**
     * 回访意见
     */
    @ApiModelProperty(value = "回访意见")
    private String returnRemark;

    /**
     * 流程结束时间
     */
    @ApiModelProperty(value = "流程结束时间")
    private Date endTime;

    /**
     * 优先级
     */
    @ApiModelProperty(value = "优先级")
    private Integer priority;

    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程状态", required = true)
    @NotBlank(message = "流程状态不能为空", groups = { EditGroup.class })
    private String state;

    /**
     * 数据来源
     */
    @ApiModelProperty(value = "数据来源")
    private String client;


}
