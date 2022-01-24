package com.zhaoxinms.workflow.business.entity.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhaoxinms.workflow.engine.entity.ProcessEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 投诉工单视图对象 flow_complaints
 *
 * @author ruoyi
 * @date 2022-01-24
 */
@Data
@ApiModel("投诉工单视图对象")
public class FlowComplaintsVo extends ProcessEntity {

	private static final long serialVersionUID = 1L;

    private String id;
	private String instanceId;

    /**
     * 单号
     */
	@Excel(name = "单号")
	@ApiModelProperty("单号")
	private String no;

    /**
     * 标题
     */
	@Excel(name = "标题")
	@ApiModelProperty("标题")
	private String title;

    /**
     * 申请人电话
     */
	@Excel(name = "申请人电话")
	@ApiModelProperty("申请人电话")
	private String applyPhone;

    /**
     * 申请人姓名
     */
	@Excel(name = "申请人姓名")
	@ApiModelProperty("申请人姓名")
	private String applyName;

    /**
     * 分类
     */
	@Excel(name = "分类")
	@ApiModelProperty("分类")
	private String applyCategory;

    /**
     * 投诉内容
     */
	@Excel(name = "投诉内容")
	@ApiModelProperty("投诉内容")
	private String applyContent;

    /**
     * 投诉人的诉求
     */
	@Excel(name = "投诉人的诉求")
	@ApiModelProperty("投诉人的诉求")
	private String applyRequirements;

    /**
     * 申请的商户编号
     */
	@Excel(name = "申请的商户编号")
	@ApiModelProperty("申请的商户编号")
	private String applyHouse;

    /**
     * 申请时间
     */
	@Excel(name = "申请时间")
	@ApiModelProperty("申请时间")
	private Date applyTime;

    /**
     * 图片
     */
	@Excel(name = "图片")
	@ApiModelProperty("图片")
	private String applyImg;

    /**
     * 受理说明
     */
	@Excel(name = "受理说明")
	@ApiModelProperty("受理说明")
	private String appContent;

    /**
     * 受理时间
     */
	@Excel(name = "受理时间")
	@ApiModelProperty("受理时间")
	private Date appTime;

    /**
     * 问题确认备注
     */
	@Excel(name = "问题确认备注")
	@ApiModelProperty("问题确认备注")
	private String confirmContent;

    /**
     * 领单人员
     */
	@Excel(name = "领单人员")
	@ApiModelProperty("领单人员")
	private String assigneeUser;

    /**
     * 领单人
     */
	@Excel(name = "领单人")
	@ApiModelProperty("领单人")
	private String assigneeUserName;

    /**
     * 处理备注
     */
	@Excel(name = "处理备注")
	@ApiModelProperty("处理备注")
	private String assigneeContent;

    /**
     * 回访状态
     */
	@Excel(name = "回访状态")
	@ApiModelProperty("回访状态")
	private String returnState;

    /**
     * 回访结果
     */
	@Excel(name = "回访结果")
	@ApiModelProperty("回访结果")
	private String returnResult;

    /**
     * 回访意见
     */
	@Excel(name = "回访意见")
	@ApiModelProperty("回访意见")
	private String returnRemark;

    /**
     * 流程结束时间
     */
	@Excel(name = "流程结束时间")
	@ApiModelProperty("流程结束时间")
	private Date endTime;

    /**
     * 优先级
     */
	@Excel(name = "优先级")
	@ApiModelProperty("优先级")
	private Integer priority;

    /**
     * 流程状态
     */
	@Excel(name = "流程状态")
	@ApiModelProperty("流程状态")
	private String state;

    /**
     * 数据来源
     */
	@Excel(name = "数据来源")
	@ApiModelProperty("数据来源")
	private String client;

    /**
     * 备注
     */
	@Excel(name = "备注")
	@ApiModelProperty("备注")
	private String remark;


}
