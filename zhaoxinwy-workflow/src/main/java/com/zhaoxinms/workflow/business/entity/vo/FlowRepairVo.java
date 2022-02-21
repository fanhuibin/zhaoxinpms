package com.zhaoxinms.workflow.business.entity.vo;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhaoxinms.workflow.engine.entity.ProcessEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;



/**
 * 报修工单视图对象 flow_repair
 *
 * @author ruoyi
 * @date 2022-01-06
 */
@Data
@ApiModel("报修工单视图对象")
public class FlowRepairVo extends ProcessEntity {

	private static final long serialVersionUID = 1L;
	
	private String id;

	@Excel(name = "流程实例")
	@ApiModelProperty("流程实例")
	private String instanceId;

    /**
     * 报修单号
     */
	@Excel(name = "报修单号")
	@ApiModelProperty("报修单号")
	private String no;

    /**
     * 报修标题
     */
	@Excel(name = "报修标题")
	@ApiModelProperty("报修标题")
	private String title;

    /**
     * 报修电话
     */
	@Excel(name = "报修电话")
	@ApiModelProperty("报修电话")
	private String applyPhone;

    /**
     * 报修者姓名
     */
	@Excel(name = "报修者姓名")
	@ApiModelProperty("报修者姓名")
	private String applyName;

    /**
     * 维修对象分类
     */
    @Excel(name = "维修对象分类")
	@ApiModelProperty("维修对象分类")
	private String applyCategory;

    /**
     * 报修的内容
     */
	@Excel(name = "报修的内容")
	@ApiModelProperty("报修的内容")
	private String applyContent;

    /**
     * 报修商户编号
     */
	@Excel(name = "报修商户编号")
	@ApiModelProperty("报修商户编号")
	private String applyHouse;

    /**
     * 报修时间
     */
	@Excel(name = "报修时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@ApiModelProperty("报修时间")
	private Date applyTime;

    /**
     * 报修图片
     */
	@Excel(name = "报修图片")
	@ApiModelProperty("报修图片")
	private String applyImg;

    /**
     * 受理说明
     */
	@Excel(name = "受理说明")
	@ApiModelProperty("受理说明")
	private String appContent;

    /**
     * 预约时间
     */
	@Excel(name = "预约时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@ApiModelProperty("预约时间")
	private Date appointmentTime;

    /**
     * 受理时间
     */
	@Excel(name = "受理时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@ApiModelProperty("受理时间")
	private Date appTime;

    /**
     * 指派维修人员
     */
	@Excel(name = "指派维修人员")
	@ApiModelProperty("指派维修人员")
	private String repairUser;
	
    /**
     * 指派维修人员显示名
     */
	@Excel(name = "指派维修人员名称")
    @ApiModelProperty(value = "指派维修人员显示名")
    private String repairUserName;

    /**
     * 维修状态
     */
    @Excel(name = "维修状态")
    @ApiModelProperty(value = "维修状态")
    private String repairState;
	
    /**
     * 维修材料费用
     */
	@Excel(name = "维修材料费用")
	@ApiModelProperty("维修材料费用")
	private BigDecimal repairMaterialsFee;

    /**
     * 维修服务费用
     */
	@Excel(name = "维修服务费用")
	@ApiModelProperty("维修服务费用")
	private BigDecimal repairServiceFee;

    /**
     * 修理备注
     */
	@Excel(name = "修理备注")
	@ApiModelProperty("修理备注")
	private String repairContent;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
