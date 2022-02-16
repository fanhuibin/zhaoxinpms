package com.zhaoxinms.workflow.engine.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class MyApplyVo {
    
    private String name;
    
    private String procInstId;
    
    private String processDefinitionKey;
    
    private String businessKey; //业务编号
    
    private String procInstName; //流程实例名

    private String instanceId;

    private String userId;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer offset;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTimeBegin;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTimeEnd;
}
