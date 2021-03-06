package com.zhaoxinms.workflow.engine.controller;

import java.awt.Color;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.common.core.controller.BaseController;
import com.zhaoxinms.common.core.domain.AjaxResult;
import com.zhaoxinms.common.core.page.TableDataInfo;
import com.zhaoxinms.common.utils.JsonUtil;
import com.zhaoxinms.common.utils.SecurityUtils;
import com.zhaoxinms.common.utils.StringUtils;
import com.zhaoxinms.workflow.engine.config.ICustomProcessDiagramGenerator;
import com.zhaoxinms.workflow.engine.config.WorkflowConstants;
import com.zhaoxinms.workflow.engine.designer.DesignerAdapterUtil;
import com.zhaoxinms.workflow.engine.designer.entity.ChildNode;
import com.zhaoxinms.workflow.engine.designer.entity.FlowDesignerModel;
import com.zhaoxinms.workflow.engine.designer.entity.FormOperate;
import com.zhaoxinms.workflow.engine.entity.FlowDesignerEntity;
import com.zhaoxinms.workflow.engine.entity.HistoricActivity;
import com.zhaoxinms.workflow.engine.entity.MyApplyVo;
import com.zhaoxinms.workflow.engine.entity.TaskVo;
import com.zhaoxinms.workflow.engine.event.WorkflowEvent;
import com.zhaoxinms.workflow.engine.service.FlowDesignerService;
import com.zhaoxinms.workflow.engine.service.IProcessService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/activiti/process")
@AllArgsConstructor
public class ActivitiProcessController extends BaseController {

    private RepositoryService repositoryService;
    private HistoryService historyService;
    private ProcessEngine processEngine;
    private IProcessService processService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private ApplicationEventPublisher applicationEventPublisher;
    private FlowDesignerService flowDesignerService;

    /**
     * ??????????????????
     */
    @PostMapping("/listHistory")
    @ResponseBody
    public TableDataInfo listHistory(@RequestBody HistoricActivity historicActivity) {
        startPage();
        List<HistoricActivity> list = processService.selectHistoryList(historicActivity);
        return getDataTable(list);
    }

    /**
     * ???????????????????????????????????????????????????
     */
    @GetMapping("/deisnger/startFormOperates/{processDefKey}")
    public ActionResult getStartFlowFormOperate(@PathVariable(name = "processDefKey") String processDefKey) {
        FlowDesignerEntity designer = flowDesignerService.getInfoByEnCode(processDefKey);
        FlowDesignerModel model = JsonUtil.getJsonToBean(designer.getJson(), FlowDesignerModel.class);
        List<FormOperate> formOperates = model.getProcessData().getProperties().getFormOperates();

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("formOperates", formOperates);
        return ActionResult.success(result);
    }

    /**
     * ?????????????????????????????????????????????
     */
    @GetMapping("/designer/formOperates/{processInstanceId}/{taskId}")
    public ActionResult getFormOperate(@PathVariable(name = "processInstanceId") String processInstanceId, @PathVariable(name = "taskId") String taskId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = "";
        if (processInstance == null) {
            HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            processDefinitionId = historicProcessInstance.getProcessDefinitionId();
        } else {
            processDefinitionId = processInstance.getProcessDefinitionId();
        }
        BpmnModel bpmn = repositoryService.getBpmnModel(processDefinitionId);
        String json = bpmn.getMainProcess().getAttributeValue("http://activiti.org/bpmn", "designerJSON");
        FlowDesignerModel model = JsonUtil.getJsonToBean(json, FlowDesignerModel.class);

        String taskDefKey = "";
        // ??????taskDefKey
        org.activiti.engine.task.Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (task == null) {
            HistoricTaskInstance oldTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
            taskDefKey = oldTask.getTaskDefinitionKey();
        } else {
            taskDefKey = task.getTaskDefinitionKey();
        }

        // ?????????????????????????????????
        ChildNode node = DesignerAdapterUtil.getChildNode(json, taskDefKey);
        List<FormOperate> formOperates = node.getProperties().getFormOperates();
        Map<String, Object> result = new HashMap<String, Object>();
        // result.put("task", task);
        result.put("formOperates", formOperates);
        return ActionResult.success(result);
    }

    /**
     * ???????????????????????????????????????
     */
    @GetMapping("/designer/{instanceId}")
    public ActionResult getDesignerImg(@PathVariable(name = "instanceId") String pProcessInstanceId, HttpServletResponse response) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(pProcessInstanceId).singleResult();
        String processDefinitionId = "";
        if (processInstance == null) {
            HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(pProcessInstanceId).singleResult();
            processDefinitionId = historicProcessInstance.getProcessDefinitionId();
        } else {
            processDefinitionId = processInstance.getProcessDefinitionId();
        }
        BpmnModel bpmn = repositoryService.getBpmnModel(processDefinitionId);
        String json = bpmn.getMainProcess().getAttributeValue("http://activiti.org/bpmn", "designerJSON");
        FlowDesignerModel model = JsonUtil.getJsonToBean(json, FlowDesignerModel.class);

        // ????????????????????????
        List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(pProcessInstanceId).list();
        List<String> completeTasks = historicTasks.stream().map(HistoricTaskInstance::getTaskDefinitionKey).collect(Collectors.toList());

        // ??????????????????
        List<org.activiti.engine.task.Task> tasks = taskService.createTaskQuery().processInstanceId(pProcessInstanceId).list();
        List<String> currentTasks = tasks.stream().map(Task::getTaskDefinitionKey).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("model", model);
        result.put("completeTasks", completeTasks);
        result.put("tasks", currentTasks);// ??????????????????????????????????????????????????????task
        return ActionResult.success(result);
    }

    /**
     * ??????
     */
    @PostMapping("/delegate")
    @ResponseBody
    public AjaxResult delegate(String taskId, String delegateToUser) {
        processService.delegate(taskId, SecurityUtils.getUsername(), delegateToUser);
        return success();
    }

    /**
     * ??????????????????
     */
    @PostMapping("/cancelApply")
    @ResponseBody
    @Transactional
    public AjaxResult cancelApply(String instanceId) {
        // ??????instanceId??????
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();

        // ??????????????????
        applicationEventPublisher.publishEvent(new WorkflowEvent(this, processInstance, WorkflowEvent.EVENT_CANCEL_APPLY));
        processService.cancelApply(instanceId, "????????????");
        return success("????????????");
    }

    /**
     * ??????/??????????????????
     */
    @PostMapping("/suspendOrActiveApply")
    @ResponseBody
    public AjaxResult suspendOrActiveApply(String instanceId, String suspendState) {
        processService.suspendOrActiveApply(instanceId, suspendState);
        return success();
    }

    /**
     * ??????????????????
     */
    @GetMapping("/taskList")
    @ResponseBody
    public TableDataInfo taskList(TaskVo taskVo) {
        return processService.findTodoTasks(taskVo);
    }

    /**
     * ????????????
     */
    @PostMapping("/complete")
    @ResponseBody
    public AjaxResult complete(String taskId, String instanceId, String variables) {
        processService.complete(taskId, instanceId, variables);
        return success();
    }

    /**
     * ??????????????????
     */
    @GetMapping("/taskDoneList")
    @ResponseBody
    public TableDataInfo taskDoneList(TaskVo taskVo) {
        return processService.findDoneTasks(taskVo);
    }

    /**
     * ??????????????????
     */
    @GetMapping("/taskApplyList")
    @ResponseBody
    public TableDataInfo taskApplyList(MyApplyVo myApplyVo) {
        return processService.findTaskApplyedByMe(myApplyVo);
    }

}
