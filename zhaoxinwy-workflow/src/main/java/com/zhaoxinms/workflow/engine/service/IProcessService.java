package com.zhaoxinms.workflow.engine.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Comment;

import com.zhaoxinms.common.core.page.TableDataInfo;
import com.zhaoxinms.workflow.engine.entity.HistoricActivity;
import com.zhaoxinms.workflow.engine.entity.MyApplyVo;
import com.zhaoxinms.workflow.engine.entity.TaskComment;
import com.zhaoxinms.workflow.engine.entity.TaskVo;

public interface IProcessService {

    /**
     * 提交申请
     */
    <T> void submitApply(T entity, String key, String title, String businessNo) throws Exception;

    <T> void submitApply(T entity, String key, String title, String businessNo, Map<String, Object> variables) throws Exception;

    /**
     * 填充流程相关字段
     */
    <T> void richProcessField(T entity) throws Exception;
    
    /**
     * 通过instanceId查询流程记录
     */
    List<TaskComment> selectHistoryByInstanceId(String instanceId);

    /**
     * 查询审批历史列表
     */
    List<HistoricActivity> selectHistoryList(HistoricActivity historicActivity);

    /**
     * 我的待办
     */
    TableDataInfo findTodoTasks(TaskVo taskVo);

    /**
     * 我的已办
     */
    TableDataInfo findDoneTasks(TaskVo taskVo);
    
    /**
     * 我发起的流程
     */
    TableDataInfo findTaskApplyedByMe(MyApplyVo myApplyVo);

    /**
     * 办理任务
     */
    void complete(String taskId, String instanceId, String variables);

    /**
     * 转办任务
     */
    void delegate(String taskId, String fromUser, String delegateToUser);

    void cancelApply(String instanceId, String deleteReason);

    /**
     * 激活/挂起流程实例
     */
    void suspendOrActiveApply(String instanceId, String suspendState);

}
