package com.zhaoxinms.workflow.engine.entity;

import java.util.Date;

import lombok.Data;

@Data
public class TaskComment {

    private String taskId; 
    
    private String taskName;
    
    private String userId;
    
    private String userName;
    
    private Date time;

    private String type;
    
    private String fullMsg;
}
