package com.zhaoxinms.workflow.engine.entity;

import com.zhaoxinms.common.core.domain.BaseEntity;

import lombok.Data;

@Data
public class ProcessEntity extends BaseEntity {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;
    
    /**
     * 流程定义key
     */
    private String taskDefKey;

    /**
     * 实例状态 1 激活 2 挂起
     */
    private String suspendState;

    /**
     * 已激活/已挂起
     */
    private String suspendStateName;

}
