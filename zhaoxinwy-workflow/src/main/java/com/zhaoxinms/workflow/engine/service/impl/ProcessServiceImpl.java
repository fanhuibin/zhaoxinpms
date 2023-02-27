package com.zhaoxinms.workflow.engine.service.impl;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.zhaoxinms.common.constant.HttpStatus;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.core.page.TableDataInfo;
import com.zhaoxinms.common.exception.ServiceException;
import com.zhaoxinms.common.utils.SecurityUtils;
import com.zhaoxinms.common.utils.StringUtils;
import com.zhaoxinms.system.mapper.SysUserMapper;
import com.zhaoxinms.system.service.ISysUserService;
import com.zhaoxinms.util.DateUtils;
import com.zhaoxinms.workflow.engine.entity.HistoricActivity;
import com.zhaoxinms.workflow.engine.entity.MyApplyVo;
import com.zhaoxinms.workflow.engine.entity.TaskComment;
import com.zhaoxinms.workflow.engine.entity.TaskVo;
import com.zhaoxinms.workflow.engine.event.WorkflowEvent;
import com.zhaoxinms.workflow.engine.mapper.TaskMapper;
import com.zhaoxinms.workflow.engine.service.IProcessService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ProcessServiceImpl implements IProcessService {

    protected final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

    private IdentityService identityService;
    private TaskService taskService;
    private HistoryService historyService;
    private RuntimeService runtimeService;
    private SysUserMapper userMapper;
    private TaskMapper taskMapper;
    private ISysUserService userService;
    
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private static final String INSTANCE_TITLE = "INSTANCE_TITLE";
    private static final String BUSINESS_NO = "BUSINESS_NO";

    /**
     * 提交申请
     */
    @Override
    public <T> void submitApply(T entity, String key, String title, String businessNo) throws Exception {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(INSTANCE_TITLE, title);
        variables.put(BUSINESS_NO, businessNo);
        this.submitApply(entity, title, key, variables);
    }

    @Override
    public <T> void submitApply(T entity, String key, String title, String businessNo, Map<String, Object> variables) throws Exception {
        variables.put(INSTANCE_TITLE, title);
        variables.put(BUSINESS_NO, businessNo);
        this.submitApply(entity, title, key, variables);
    }

    private <T> void submitApply(T entity, String name, String key, Map<String, Object> variables) throws Exception {

        Class clazz = entity.getClass();

        Method getId = clazz.getDeclaredMethod("getId");
        Long id = Long.valueOf((String)getId.invoke(entity));

        Method setInstanceId = clazz.getDeclaredMethod("setInstanceId", String.class);

        String username = SecurityUtils.getUsername();

        // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(username);
        // 启动流程时设置业务 key
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(key, id + "", variables);
        //设置流程实例的name
        runtimeService.setProcessInstanceName(instance.getId(),name);
        // 更新业务表流程实例id字段
        setInstanceId.invoke(entity, instance.getId());
        
        //添加发起记录
        Authentication.setAuthenticatedUserId(SecurityUtils.getUsername());
        taskService.addComment(null, instance.getId(), "发起流程");
        
    }

    /** 驼峰转下划线 */
    private String humpToLine(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /** 下划线转驼峰 */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Pattern linePattern = Pattern.compile("_(\\w)");
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 填充流程相关字段
     */
    @Override
    public <T> void richProcessField(T entity) throws Exception {
        Class clazz = entity.getClass();
        Method getInstanceId = clazz.getDeclaredMethod("getInstanceId");
        String instanceId = (String)getInstanceId.invoke(entity);

        Method setTaskId = clazz.getSuperclass().getDeclaredMethod("setTaskId", String.class);
        Method setTaskName = clazz.getSuperclass().getDeclaredMethod("setTaskName", String.class);
        Method setTaskDefKey = clazz.getSuperclass().getDeclaredMethod("setTaskDefKey", String.class);
        Method setSuspendState = clazz.getSuperclass().getDeclaredMethod("setSuspendState", String.class);
        Method setSuspendStateName = clazz.getSuperclass().getDeclaredMethod("setSuspendStateName", String.class);

        // 当前环节
        if (StringUtils.isNotBlank(instanceId)) {
            List<Task> taskList = taskService.createTaskQuery().processInstanceId(instanceId).list(); // 例如请假会签，会同时拥有多个任务
            if (!CollectionUtils.isEmpty(taskList)) {
                TaskEntityImpl task = (TaskEntityImpl)taskList.get(0);

                setTaskId.invoke(entity, task.getId());
                setTaskDefKey.invoke(entity, task.getTaskDefinitionKey());
                if (task.getSuspensionState() == 2) {
                    setTaskName.invoke(entity, "已挂起");
                    setSuspendState.invoke(entity, "2");
                    setSuspendStateName.invoke(entity, "已挂起");
                } else {
                    setTaskName.invoke(entity, task.getName());
                    setSuspendState.invoke(entity, "1");
                    setSuspendStateName.invoke(entity, "已激活");
                }
            } else {
                // 已办结或者已撤销
                List<HistoricTaskInstance> list =
                    historyService.createHistoricTaskInstanceQuery().processInstanceId(instanceId).orderByTaskCreateTime().desc().list();
                if (!CollectionUtils.isEmpty(list)) {
                    HistoricTaskInstance lastTask = list.get(0); // 该流程实例最后一个任务
                    if (StringUtils.isNotBlank(lastTask.getDeleteReason())) {
                        setTaskName.invoke(entity, "已撤销");
                    } else {
                        setTaskName.invoke(entity, "已结束");
                    }
                    setTaskId.invoke(entity, "-1"); // 已撤销或已结束，任务id不妨设置成-1
                } else {
                    // 这种情况是流程表被删除，业务表的instanceId找不到对应记录
                    setTaskName.invoke(entity, "流程已删除");
                    setTaskId.invoke(entity, "-2"); // 流程已删除，前端不能查看审批历史和进度
                }
            }
        } else {
            setTaskName.invoke(entity, "未启动");
        }
    }

    @Override
    public TableDataInfo findTodoTasks(TaskVo taskVo) {
        taskVo.setUserId(SecurityUtils.getUsername());
        taskVo.setOffset((taskVo.getPageNum() - 1) * taskVo.getPageSize());
        
        if(StringUtils.isNotEmpty(taskVo.getBusinessNo())) {
            ProcessInstance instance = runtimeService.createProcessInstanceQuery().variableValueEquals(BUSINESS_NO, taskVo.getBusinessNo()).singleResult();
            if(instance != null) {
                taskVo.setInstanceId(instance.getId());
            }else {
                taskVo.setInstanceId("undefined");
            }
         }
        
        List<Map> tasks = taskMapper.findTodoList(taskVo);
        Integer count = taskMapper.findTodoCount(taskVo);

        List<TaskVo> taskVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(tasks)) {
            tasks.forEach(task -> {
                TaskVo newTaskVo = new TaskVo();
                newTaskVo.setType("todo");
                newTaskVo.setUserId(SecurityUtils.getUsername());
                newTaskVo.setTaskId(task.get("ID_").toString());
                newTaskVo.setTaskName(task.get("NAME_").toString());
                newTaskVo.setInstanceId(task.get("PROC_INST_ID_").toString());
                newTaskVo.setSuspendState(task.get("SUSPENSION_STATE_").toString());
                newTaskVo.setCreateTime((Date)task.get("CREATE_TIME_"));
                newTaskVo.setTaskDefKey(task.get("TASK_DEF_KEY_").toString());
                if ("2".equals(newTaskVo.getSuspendState())) {
                    newTaskVo.setSuspendStateName("已挂起");
                } else {
                    newTaskVo.setSuspendStateName("已激活");
                }
                newTaskVo.setAssigneeName(userMapper.selectUserByUserName(newTaskVo.getUserId()).getNickName());

                // 添加流程title
                String title = (String)taskService.getVariable(newTaskVo.getTaskId(), INSTANCE_TITLE);
                newTaskVo.setInstanceTitle(title);
                
                // 添加流程编号
                String no = (String)taskService.getVariable(newTaskVo.getTaskId(), BUSINESS_NO);
                newTaskVo.setBusinessNo(no);

                // 添加流程key
                ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(newTaskVo.getInstanceId()).singleResult();
                String key = processInstance.getProcessDefinitionKey();
                newTaskVo.setProcessDefinitionKey(key);

                taskVos.add(newTaskVo);
            });
        }

        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(taskVos);
        rspData.setTotal(count);

        return rspData;
    }

    private Map<String, Object> getLine2HumpMap(Map<String, Object> map) {
        Map<String, Object> newMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            // key 格式转换，如 apply_user_id 转换成 applyUserId
            key = lineToHump(key).substring(0, 1).toLowerCase() + lineToHump(key).substring(1);
            newMap.put(key, value);
        }
        return newMap;
    }

    @Override
    public TableDataInfo findDoneTasks(TaskVo taskVo) {
        taskVo.setUserId(SecurityUtils.getUsername());
        taskVo.setOffset((taskVo.getPageNum() - 1) * taskVo.getPageSize());
        
        if(StringUtils.isNotEmpty(taskVo.getBusinessNo())) {
           HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery().variableValueEquals(BUSINESS_NO, taskVo.getBusinessNo()).singleResult();
           if(instance != null) {
               taskVo.setInstanceId(instance.getId());
           }else {
               taskVo.setInstanceId("undefined");
           }
        }
        
        List<Map> tasks = taskMapper.findDoneList(taskVo);
        Integer count = taskMapper.findDoneCount(taskVo);

        List<TaskVo> taskVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(tasks)) {
            tasks.forEach(task -> {
                TaskVo newTaskVo = new TaskVo();
                newTaskVo.setType("done");
                newTaskVo.setUserId(SecurityUtils.getUsername());
                newTaskVo.setTaskId(task.get("ID_").toString());
                newTaskVo.setTaskName(task.get("NAME_").toString());
                newTaskVo.setInstanceId(task.get("PROC_INST_ID_").toString());
                newTaskVo.setAssignee(task.get("ASSIGNEE_").toString());
                LocalDateTime startTime = (LocalDateTime)task.get("START_TIME_");
                LocalDateTime endTime = (LocalDateTime)task.get("END_TIME_");
                newTaskVo.setStartTime(DateUtils.localDateTimeToDate(startTime));
                newTaskVo.setEndTime(DateUtils.localDateTimeToDate(endTime));
                newTaskVo.setAssigneeName(userMapper.selectUserByUserName(newTaskVo.getAssignee()).getNickName());

                // 添加流程title
                String title = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(newTaskVo.getInstanceId())
                    .variableName(INSTANCE_TITLE)
                    .excludeTaskVariables().singleResult().getValue().toString();
                newTaskVo.setInstanceTitle(title);
                
                // 添加流程编码
                String no = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(newTaskVo.getInstanceId())
                    .variableName(BUSINESS_NO)
                    .excludeTaskVariables().singleResult().getValue().toString();
                newTaskVo.setBusinessNo(no);
                
                // 添加流程key
                ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(newTaskVo.getInstanceId()).singleResult();
                String key = "";
                if(processInstance == null) {
                    HistoricProcessInstance historicProcessInstance =
                        historyService.createHistoricProcessInstanceQuery().processInstanceId(newTaskVo.getInstanceId()).singleResult();
                    key = historicProcessInstance.getProcessDefinitionKey();
                }else {
                    key = processInstance.getProcessDefinitionKey();
                }
                newTaskVo.setProcessDefinitionKey(key);
                taskVos.add(newTaskVo);
            });
        }

        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(taskVos);
        rspData.setTotal(count);

        return rspData;
    }

    @Override
    public void complete(String taskId, String instanceId, String variablesStr) {
        Map<String, Object> variables = (Map<String, Object>)JSON.parse(variablesStr);
        String comment = variables.get("comment").toString();
        String pass = variables.get("pass").toString();
        try {
            variables.put("pass", "true".equals(pass));
            // 被委派人处理完成任务
            // p.s. 被委托的流程需要先 resolved 这个任务再提交。
            // 所以在 complete 之前需要先 resolved

            // 判断该任务是否是委托任务（转办）
            TaskEntityImpl task = (TaskEntityImpl)taskService.createTaskQuery().taskId(taskId).singleResult();
            // DELEGATION_ 为 PENDING 表示该任务是转办任务
            if (task.getDelegationState() != null && task.getDelegationState().equals(DelegationState.PENDING)) {
                taskService.resolveTask(taskId, variables);
                // 批注说明是转办
                String delegateUserName = userMapper.selectUserByUserName(SecurityUtils.getUsername()).getNickName();
                comment += "【由" + delegateUserName + "转办】";

                // 如果是 OWNER_ 为 null 的转办任务（候选组的待办），暂且用转办人来签收该任务
                if (StringUtils.isBlank(task.getOwner())) {
                    taskService.claim(taskId, SecurityUtils.getUsername());
                }
            } else {
                // 只有签收任务，act_hi_taskinst 表的 assignee 字段才不为 null
                taskService.claim(taskId, SecurityUtils.getUsername());
            }

            if (StringUtils.isNotEmpty(comment)) {
                identityService.setAuthenticatedUserId(SecurityUtils.getUsername());
                taskService.addComment(taskId, instanceId, comment);
            }

            taskService.complete(taskId, variables);
        } catch (Exception e) {
            logger.error("error on complete task {}, variables={}", new Object[] {taskId, variables, e});
        }
    }

    @Override
    public List<HistoricActivity> selectHistoryList(HistoricActivity historicActivity) {
        // 说明：以下实现方案是手动封装 开始节点 和 结束节点 的数据，因此不考虑分页功能
        // PageDomain pageDomain = TableSupport.buildPageRequest();
        // Integer pageNum = pageDomain.getPageNum();
        // Integer pageSize = pageDomain.getPageSize();
        List<HistoricActivity> activityList = new ArrayList<>();
        HistoricActivityInstanceQuery query = historyService.createHistoricActivityInstanceQuery();
        if (StringUtils.isNotBlank(historicActivity.getAssignee())) {
            query.taskAssignee(historicActivity.getAssignee());
        }
        if (StringUtils.isNotBlank(historicActivity.getActivityName())) {
            query.activityName(historicActivity.getActivityName());
        }
        List<HistoricActivityInstance> list = query.processInstanceId(historicActivity.getProcessInstanceId()).activityType("userTask").finished()
            .orderByHistoricActivityInstanceStartTime().asc().list();
        // .listPage((pageNum - 1) * pageSize, pageNum * pageSize);
        list.forEach(instance -> {
            HistoricActivity activity = new HistoricActivity();
            BeanUtils.copyProperties(instance, activity);
            String taskId = instance.getTaskId();
            List<Comment> comment = taskService.getTaskComments(taskId, "comment");
            if (!CollectionUtils.isEmpty(comment)) {
                activity.setComment(comment.get(0).getFullMessage());
            }
            // 如果是撤销（deleteReason 不为 null），写入审批意见栏
            if (StringUtils.isNotBlank(activity.getDeleteReason())) {
                activity.setComment(activity.getDeleteReason());
            }
            SysUser sysUser = userMapper.selectUserByUserName(instance.getAssignee());
            if (sysUser != null) {
                activity.setAssigneeName(sysUser.getNickName());
            }
            activityList.add(activity);
        });
        
        // 封装当前节点的comment    
        Task task = taskService.createTaskQuery().processInstanceId(historicActivity.getProcessInstanceId()).active().singleResult();
        if (task != null) {
            HistoricActivity activity = new HistoricActivity();
            String taskId = task.getId();
            List<Comment> comment = taskService.getTaskComments(taskId, "comment");
            if (!CollectionUtils.isEmpty(comment)) {
                activity.setComment(comment.get(0).getFullMessage());
            }
            SysUser sysUser = userMapper.selectUserByUserName(task.getAssignee());
            if (sysUser != null) {
                activity.setAssigneeName(sysUser.getNickName());
            }
            activityList.add(activity);
        }

        // 以下手动封装发起人节点的数据
        HistoricActivity startActivity = new HistoricActivity();
        query = historyService.createHistoricActivityInstanceQuery();
        HistoricActivityInstance startActivityInstance =
            query.processInstanceId(historicActivity.getProcessInstanceId()).activityType("startEvent").singleResult();
        BeanUtils.copyProperties(startActivityInstance, startActivity);
        HistoricProcessInstance historicProcessInstance =
            historyService.createHistoricProcessInstanceQuery().processInstanceId(historicActivity.getProcessInstanceId()).singleResult();
        startActivity.setAssignee(historicProcessInstance.getStartUserId());
        SysUser sysUser = userMapper.selectUserByUserName(historicProcessInstance.getStartUserId());
        if (sysUser != null) {
            startActivity.setAssigneeName(sysUser.getNickName());
        }
        startActivity.setComment("提交申请");

        // 手动过滤该条发起人数据
        boolean necessaryAdd = true;
        if ((StringUtils.isNotBlank(historicActivity.getActivityName()) && !startActivity.getActivityName().equals(historicActivity.getActivityName()))
            || (StringUtils.isNotBlank(historicActivity.getAssignee()) && !startActivity.getAssignee().equals(historicActivity.getAssignee()))) {
            necessaryAdd = false;
        }
        if (necessaryAdd) {
            activityList.add(0, startActivity);
        }

        // 以下手动封装结束节点的数据
        HistoricActivity endActivity = new HistoricActivity();
        query = historyService.createHistoricActivityInstanceQuery();
        HistoricActivityInstance endActivityInstance = query.processInstanceId(historicActivity.getProcessInstanceId()).activityType("endEvent").singleResult();
        if (null != endActivityInstance) {
            BeanUtils.copyProperties(endActivityInstance, endActivity);
            endActivity.setAssignee("admin");
            sysUser = userMapper.selectUserByUserName("admin");
            if (sysUser != null) {
                endActivity.setAssigneeName(sysUser.getNickName());
            }
            endActivity.setComment("自动结束");

            // 手动过滤该条发起人数据
            necessaryAdd = true;
            if ((StringUtils.isNotBlank(historicActivity.getActivityName()) && !endActivity.getActivityName().equals(historicActivity.getActivityName()))
                || (StringUtils.isNotBlank(historicActivity.getAssignee()) && !endActivity.getAssignee().equals(historicActivity.getAssignee()))) {
                necessaryAdd = false;
            }
            if (necessaryAdd) {
                activityList.add(endActivity);
            }
        }

        return activityList;
    }

    @Override
    public void delegate(String taskId, String fromUser, String delegateToUser) {
        taskService.delegateTask(taskId, delegateToUser);
    }

    @Override
    @Transactional
    public void cancelApply(String instanceId, String deleteReason) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        applicationEventPublisher.publishEvent(new WorkflowEvent(this, processInstance, WorkflowEvent.EVENT_CANCEL_APPLY));
        // 执行此方法后未审批的任务 act_ru_task 会被删除，流程历史 act_hi_taskinst 不会被删除，并且流程历史的状态为finished完成
        runtimeService.deleteProcessInstance(instanceId, deleteReason);
    }

    @Override
    public void suspendOrActiveApply(String instanceId, String suspendState) {
        if ("1".equals(suspendState)) {
            // 当流程实例被挂起时，无法通过下一个节点对应的任务id来继续这个流程实例。
            // 通过挂起某一特定的流程实例，可以终止当前的流程实例，而不影响到该流程定义的其他流程实例。
            // 激活之后可以继续该流程实例，不会对后续任务造成影响。
            // 直观变化：act_ru_task 的 SUSPENSION_STATE_ 为 2
            runtimeService.suspendProcessInstanceById(instanceId);
        } else if ("2".equals(suspendState)) {
            runtimeService.activateProcessInstanceById(instanceId);
        }
    }

    @Override
    public TableDataInfo findTaskApplyedByMe(MyApplyVo myApplyVo) {
        String username = SecurityUtils.getUsername();
        myApplyVo.setOffset((myApplyVo.getPageNum() - 1) * myApplyVo.getPageSize());
        HistoricProcessInstanceQuery  query = historyService.createHistoricProcessInstanceQuery().startedBy(username);
        
        if(StringUtils.isNotEmpty(myApplyVo.getProcInstName())) {
            query.processInstanceNameLike("%"+myApplyVo.getProcInstName()+"%");
        }
        if(myApplyVo.getStartTimeBegin() != null && myApplyVo.getStartTimeEnd() != null) {
            query.startedBefore(myApplyVo.getStartTimeEnd());
            query.startedAfter(myApplyVo.getStartTimeBegin());
        }
        
        List<HistoricProcessInstance> process = query.listPage(myApplyVo.getOffset(), myApplyVo.getPageSize());
        Long count = query.count();
        
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(process);
        rspData.setTotal(count);

        return rspData;
    }

    @Override
    public List<TaskComment> selectHistoryByInstanceId(String instanceId) {
        if( StringUtils.isEmpty(instanceId) ) {
            throw new ServiceException("流程实例id不能为空");
        }
        List<TaskComment> list = taskMapper.getComments(instanceId);
        return list;
    }

}
