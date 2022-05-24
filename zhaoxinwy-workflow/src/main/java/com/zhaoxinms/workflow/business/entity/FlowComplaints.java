package com.zhaoxinms.workflow.business.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhaoxinms.common.core.mybatisplus.BaseEntity;

/**
 * 投诉工单对象 flow_complaints
 *
 * @author ruoyi
 * @date 2022-01-24
 */
@Data
@Accessors(chain = true)
@TableName("flow_complaints")
public class FlowComplaints extends BaseEntity {

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
     * 单号
     */
    private String no;
    /**
     * 标题
     */
    private String title;
    /**
     * 申请人电话
     */
    private String applyPhone;
    /**
     * 申请人姓名
     */
    private String applyName;
    /**
     * 分类
     */
    private String applyCategory;
    /**
     * 投诉内容
     */
    private String applyContent;
    /**
     * 投诉人的诉求
     */
    private String applyRequirements;
    /**
     * 申请的商户编号
     */
    private String applyHouse;
    /**
     * 申请时间
     */
    private Date applyTime;
    /**
     * 图片
     */
    private String applyImg;
    /**
     * 受理说明
     */
    private String appContent;
    /**
     * 受理时间
     */
    private Date appTime;
    /**
     * 问题确认备注
     */
    private String confirmContent;
    /**
     * 领单人员
     */
    private String assigneeUser;
    /**
     * 领单人
     */
    private String assigneeUserName;
    /**
     * 处理备注
     */
    private String assigneeContent;
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
