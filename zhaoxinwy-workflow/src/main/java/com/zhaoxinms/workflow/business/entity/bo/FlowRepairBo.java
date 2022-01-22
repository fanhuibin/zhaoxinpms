package com.zhaoxinms.workflow.business.entity.bo;

import com.zhaoxinms.common.core.mybatisplus.BaseEntity;
import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;
import com.zhaoxinms.workflow.engine.entity.ProcessEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

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
public class FlowRepairBo extends ProcessEntity {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;
    

    /**
     * 报修单号
     */
    @ApiModelProperty(value = "报修单号", required = true)
    @NotBlank(message = "报修单号不能为空", groups = { EditGroup.class })
    private String no;

    /**
     * 报修标题
     */
    @ApiModelProperty(value = "报修标题", required = true)
    @NotBlank(message = "报修标题不能为空", groups = { EditGroup.class })
    private String title;

    /**
     * 报修电话
     */
    @ApiModelProperty(value = "报修电话", required = true)
    @NotBlank(message = "报修电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyPhone;

    /**
     * 报修者姓名
     */
    @ApiModelProperty(value = "报修者姓名", required = true)
    @NotBlank(message = "报修者姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyName;

    /**
     * 维修对象分类
     */
    @ApiModelProperty(value = "维修对象分类", required = true)
    @NotBlank(message = "维修对象分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyCategory;

    /**
     * 报修的内容
     */
    @ApiModelProperty(value = "报修的内容", required = true)
    @NotBlank(message = "报修的内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyContent;

    /**
     * 报修商户编号
     */
    @ApiModelProperty(value = "报修商户编号")
    private String applyHouse;

    /**
     * 报修时间
     */
    @ApiModelProperty(value = "报修时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08")
    private Date applyTime;

    /**
     * 报修图片
     */
    @ApiModelProperty(value = "报修图片")
    private String applyImg;

    /**
     * 受理说明
     */
    @ApiModelProperty(value = "受理说明")
    private String appContent;

    /**
     * 预约时间
     */
    @ApiModelProperty(value = "预约时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08")
    private Date appointmentTime;

    /**
     * 受理时间
     */
    @ApiModelProperty(value = "受理时间")
    private Date appTime;

    /**
     * 指派维修人员
     */
    @ApiModelProperty(value = "指派维修人员")
    private String repairUser;
    
    /**
     * 指派维修人员显示名
     */
    @ApiModelProperty(value = "指派维修人员显示名")
    private String repairUserName;

    /**
     * 维修材料费用
     */
    @ApiModelProperty(value = "维修材料费用")
    private BigDecimal repairMaterialsFee;

    /**
     * 维修服务费用
     */
    @ApiModelProperty(value = "维修服务费用")
    private BigDecimal repairServiceFee;

    /**
     * 修理备注
     */
    @ApiModelProperty(value = "修理备注")
    private String repairContent;
    
    /**
     * 维修状态（非持久化参数）
     */
    @ApiModelProperty(value = "维修状态")
    private String repairState;

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
