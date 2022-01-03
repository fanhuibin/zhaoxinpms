package com.zhaoxinms.workflow.engine.entity;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @author 一只闲鹿
 */
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
     * 实例状态 1 激活 2 挂起
     */
    private String suspendState;

    /**
     * 已激活/已挂起
     */
    private String suspendStateName;

}
