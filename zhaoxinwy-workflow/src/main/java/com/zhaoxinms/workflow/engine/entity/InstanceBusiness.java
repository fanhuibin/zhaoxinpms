package com.zhaoxinms.workflow.engine.entity;

import lombok.Data;

/**
 * 流程实例业务关系表
 * @author 一只闲鹿
 */
@Data
public class InstanceBusiness {

    private Long id;

    private String instanceId;

    private String businessKey;

    private String module;

}
