package com.zhaoxinms.workflow.business.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhaoxinms.common.core.mybatisplus.BaseEntity;

/**
 * 报修工单对象 flow_repair
 *
 * @author ruoyi
 * @date 2022-01-06
 */
@Data
@Accessors(chain = true)
@TableName("flow_repair")
public class FlowRepair extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private String id;
    /**
     * 流程实例
     */
    private String instanceId;
    /**
     * 业主id
     */
    private String ownerId;
    /**
     * 报修单号
     */
    private String no;
    /**
     * 报修标题
     */
    private String title;
    /**
     * 报修电话
     */
    private String applyPhone;
    /**
     * 报修者姓名
     */
    private String applyName;
    /**
     * 维修对象分类
     */
    private String applyCategory;
    /**
     * 报修的内容
     */
    private String applyContent;
    /**
     * 报修商户编号
     */
    private String applyHouse;
    /**
     * 报修时间
     */
    private Date applyTime;
    /**
     * 报修图片
     */
    private String applyImg;
    /**
     * 受理说明
     */
    private String appContent;
    /**
     * 预约时间
     */
    private Date appointmentTime;
    /**
     * 受理时间
     */
    private Date appTime;
    /**
     * 指派维修人员
     */
    private String repairUser;
    /**
     * 指派维修人员显示名
     */
    private String repairUserName;
    /**
     * 维修的状态
     */
    private String repairState;
    /**
     * 维修材料费用
     */
    private BigDecimal repairMaterialsFee;
    /**
     * 维修服务费用
     */
    private BigDecimal repairServiceFee;
    /**
     * 修理备注
     */
    private String repairContent;
    /**
     * 回访状态
     */
    private String returnState;
    /**
     * 回访结果
     */
    private String returnResult;
    /**
     * 回访意见
     */
    private String returnRemark;
    /**
     * 流程结束时间
     */
    private Date endTime;
    /**
     * 优先级
     */
    private Integer priority;
    /**
     * 流程状态
     */
    private String state;
    /**
     * 数据来源
     */
    private String client;

}
