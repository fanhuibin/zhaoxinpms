package com.zhaoxinms.workflow;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;

import com.ruoyi.common.utils.StringUtils;

public class BPMNCreator {
    public void generateBpmn() {
        // 创建bpmn模型
        BpmnModel model = new BpmnModel();
        Process process = new Process();
        model.addProcess(process);
        process.setId("leave");
        process.setName("请假流程");

        // 创建bpmn元素
        process.addFlowElement(createStartEvent());
        process.addFlowElement(createUserTask("task1", "First task", "fred"));
        process.addFlowElement(createUserTask("task2", "Second task", "john"));
        process.addFlowElement(createEndEvent());
        
        //创建一个排他网关
        process.addFlowElement(createExclusiveGateway("paita", ""));
        process.addFlowElement(createUserTask("path1", "排他用户流程", "fred"));
        
        //创建一个会签
        process.addFlowElement(createCounterSign("huiqian","会签任务","admin,lisi","assignee"));
        
        //创建一个

        process.addFlowElement(createSequenceFlow("start", "task1"));
        process.addFlowElement(createSequenceFlow("task1", "task2"));
        process.addFlowElement(createSequenceFlow("task2", "paita"));
        process.addFlowElement(createSequenceFlow("paita","huiqian","张三申请需要会签","${apply == zhangsan}"));
        
        process.addFlowElement(createSequenceFlow("paita","path1","其他人的负责人审核","${apply != zhangsan}"));
        process.addFlowElement(createSequenceFlow("huiqian","end"));
        process.addFlowElement(createSequenceFlow("path1","end"));

        // 2.生成BPMN自动布局
        new BpmnAutoLayout(model).execute();

        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] convertToXML = bpmnXMLConverter.convertToXML(model);
        String bytes = new String(convertToXML);
        bytes = bytes.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        System.out.println(bytes);

    }
    

    // 创建task
    protected UserTask createUserTask(String id, String name, String assignee) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setAssignee(assignee);
        return userTask;
    }

    // 创建会签task
    protected UserTask createCounterSign(String id,String name,String assigneeList,String assignName) {
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
        userTask.setAssignee("${"+assignName+"}");
        // 设置多实例属性
        userTask.setLoopCharacteristics(multiInstanceLoopCharacteristics);
        // 设置监听器
        // userTask.setExecutionListeners(countersignTaskListener());
        return userTask;
    }

    // 创建箭头
    protected SequenceFlow createSequenceFlow(String from, String to) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        return flow;
    }

    //带条件的箭头
    protected SequenceFlow createSequenceFlow(String from, String to, String name, String conditionExpression) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        flow.setName(name);
        if (StringUtils.isNotEmpty(conditionExpression)) {
            flow.setConditionExpression(conditionExpression);
        }
        return flow;
    }

    protected StartEvent createStartEvent() {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("start");
        return startEvent;
    }

    // 排他网关
    protected ExclusiveGateway createExclusiveGateway(String id, String name) {
        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        exclusiveGateway.setId(id);
        exclusiveGateway.setName(name);
        return exclusiveGateway;
    }

    // 并行网关
    protected ParallelGateway createParallelGateway(String id, String name) {
        ParallelGateway gateway = new ParallelGateway();
        gateway.setId(id);
        gateway.setName(name);
        return gateway;
    }

    protected EndEvent createEndEvent() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");
        return endEvent;
    }
    
    public static void main(String args[]) {
        BPMNCreator creator = new BPMNCreator();
        creator.generateBpmn();
    }

}
