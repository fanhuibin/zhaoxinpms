/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms
 * website：http://pms.zhaoxinms.com  wx： zhaoxinms
 *
 */
package com.zhaoxinms.workflow.engine.designer;

import java.util.ArrayList;
import java.util.List;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CustomProperty;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.ParallelGateway;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.bpmn.model.Process;

import com.zhaoxinms.common.utils.JsonUtil;
import com.zhaoxinms.common.utils.StringUtils;
import com.zhaoxinms.workflow.engine.designer.entity.FlowDesignerModel;

public class BPMNCreator {

    // 创建task
    public static UserTask createUserTask(String id, String name, String assignee) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setAssignee(assignee);
        return userTask;
    }

    // 创建会签task
    public static UserTask createCounterSign(String id, String name, String assigneeList, String assignName) {
        // 用户节点
        UserTask userTask = new UserTask();
        userTask.setId(id);
        userTask.setName(name);
        MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = new MultiInstanceLoopCharacteristics();
        // 审批人集合参数
        multiInstanceLoopCharacteristics.setInputDataItem(assigneeList);
        // 迭代集合
        multiInstanceLoopCharacteristics.setElementVariable(assignName);
        // 完成条件 已完成数等于实例数
        multiInstanceLoopCharacteristics.setCompletionCondition("${nrOfActiveInstances == nrOfInstances}");
        // 并行
        multiInstanceLoopCharacteristics.setSequential(false);
        userTask.setAssignee("${" + assignName + "}");
        // 设置多实例属性
        userTask.setLoopCharacteristics(multiInstanceLoopCharacteristics);
        // 设置监听器
        // userTask.setExecutionListeners(countersignTaskListener());
        return userTask;
    }

    // 创建箭头
    public static SequenceFlow createSequenceFlow(String from, String to) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        return flow;
    }

    // 带条件的箭头
    public static SequenceFlow createSequenceFlow(String from, String to, String name, String conditionExpression) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        flow.setName(name);
        if (StringUtils.isNotEmpty(conditionExpression)) {
            flow.setConditionExpression(conditionExpression);
        }
        return flow;
    }

    public static StartEvent createStartEvent(String id) {
        StartEvent startEvent = new StartEvent();
        startEvent.setId(id);
        return startEvent;
    }

    // 排他网关
    public static ExclusiveGateway createExclusiveGateway(String id, String name) {
        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        exclusiveGateway.setId(id);
        exclusiveGateway.setName(name);
        return exclusiveGateway;
    }

    // 并行网关
    public static ParallelGateway createParallelGateway(String id, String name) {
        ParallelGateway gateway = new ParallelGateway();
        gateway.setId(id);
        gateway.setName(name);
        return gateway;
    }

    public static ServiceTask createServiceTask(String id, String name, List<CustomProperty> properties) {
        ServiceTask service = new ServiceTask();
        service.setId(id);
        service.setName(name);
        service.setCustomProperties(properties);
        return service;
    }

    public static EndEvent createEndEvent() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");
        return endEvent;
    }
    
    private static List<ActivitiListener> getGlobalListener() {
        ArrayList<ActivitiListener> listeners = new ArrayList<>();
        ActivitiListener activitiListener = new ActivitiListener();
        // 事件类型,
        activitiListener.setEvent(ExecutionListener.EVENTNAME_START);
        // 监听器类型
        activitiListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
        // 设置实现了，这里设置监听器的类型是delegateExpression，这样可以在实现类注入Spring bean.
        activitiListener.setImplementation("${serviceTaskListener}");
        listeners.add(activitiListener);
        return listeners;
    }

    public static String createXML(String json, List<UserTask> userTasks, List<ExclusiveGateway> gateways, List<SequenceFlow> taskFlows) {
        FlowDesignerModel model = JsonUtil.getJsonToBean(json, FlowDesignerModel.class);
        BpmnModel bpmn = new BpmnModel();
        
        ExtensionAttribute a = new ExtensionAttribute();
        a.setName("designerJSON");
        a.setNamespace("http://activiti.org/bpmn");
        json.replaceAll(">", "&gt;");
        json.replaceAll("<", "&lt;");
        a.setValue(json);
        
        Process process = new Process();
        bpmn.addProcess(process);
        process.addAttribute(a);
        process.setId(model.getBasicSetting().getFlowCode());
        process.setName(model.getBasicSetting().getFlowName());

        //添加监听器
        process.setExecutionListeners(getGlobalListener());
        
        // 1.创建节点
        StartEvent start = BPMNCreator.createStartEvent(model.getProcessData().getNodeId());
        EndEvent end = BPMNCreator.createEndEvent();
        process.addFlowElement(start);
        process.addFlowElement(end);

        for (UserTask task : userTasks) {
            process.addFlowElement(task);
        }
        
        for(ExclusiveGateway gateway : gateways) {
            process.addFlowElement(gateway);
        }
        
        for(SequenceFlow flow : taskFlows) {
            process.addFlowElement(flow);
        }

        // 2.生成BPMN自动布局
        new BpmnAutoLayout(bpmn).execute();

        // 3.输出xml
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] convertToXML = bpmnXMLConverter.convertToXML(bpmn);
        String bytes = new String(convertToXML);
        return bytes;
    }
}
