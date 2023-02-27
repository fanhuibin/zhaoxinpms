package com.zhaoxinms.workflow.engine.cmd;

import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.impl.history.HistoryManager;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;

import com.zhaoxinms.common.utils.SecurityUtils;

public class JumpAnyWhereCmd implements Command {
    private String taskId;
    private String targetNodeId;
    private String comment;  //跳转的说明
    
    public JumpAnyWhereCmd(String taskId, String targetNodeId, String comment) {
        this.taskId = taskId;
        this.targetNodeId = targetNodeId;
        String username = SecurityUtils.getLoginUser().getUser().getNickName();
        this.comment = "【由" + username + "驳回】" + comment;
    }

    public Object execute(CommandContext commandContext) {
        // 获取任务实例管理类
        TaskEntityManager taskEntityManager = commandContext.getTaskEntityManager();
        // 获取当前任务实例
        TaskEntity currentTask = taskEntityManager.findById(taskId);

        // 获取当前节点的执行实例
        ExecutionEntity execution = currentTask.getExecution();

        // 获取流程定义id
        String processDefinitionId = execution.getProcessDefinitionId();
        // 获取目标节点
        org.activiti.bpmn.model.Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);
        FlowElement flowElement = process.getFlowElement(targetNodeId);

        // 获取历史管理
        HistoryManager historyManager = commandContext.getHistoryManager();

        // 通知当前活动结束(更新act_hi_actinst)
        historyManager.recordActivityEnd(execution, comment);
        // 通知任务节点结束(更新act_hi_taskinst)
        historyManager.recordTaskEnd(taskId, comment);
        
        IdentityLinkEntityManager identityLinkEntityManager = commandContext.getIdentityLinkEntityManager();
        identityLinkEntityManager.deleteIdentityLinksByTaskId(taskId);

        // 删除正在执行的当前任务
        taskEntityManager.delete(taskId);

        // 此时设置执行实例的当前活动节点为目标节点
        execution.setCurrentFlowElement(flowElement);

        // 向operations中压入继续流程的操作类
        commandContext.getAgenda().planContinueProcessOperation(execution);

        return null;
    }
}
