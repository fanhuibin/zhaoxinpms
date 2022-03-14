/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms website：http://pms.zhaoxinms.com wx： zhaoxinms
 *
 */
package com.zhaoxinms.workflow.engine.designer.node;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.commons.lang3.StringUtils;

import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.workflow.engine.designer.entity.ChildNode;
import com.zhaoxinms.workflow.engine.model.TreeSelectModel;

public class ApproverNodeCreator {
    public static final String OR_SIGN = "orSign";
    public static final String COUNTER_SIGN = "counterSign";

    public static final String ASSIGNEE_TYPE_USER = "user";
    public static final String ASSIGNEE_TYPE_ROLE = "role";
    public static final String ASSIGNEE_TYPE_INPUT = "input";
    public static final String ASSIGNEE_TYPE_MYSELF = "myself";

    public static List<UserTask> createUserTask(List<ChildNode> approverNodes) {
        List<UserTask> tasks = new ArrayList<UserTask>();
        for (ChildNode node : approverNodes) {
            if (node.getProperties().getOptionalMultiUser().equals(ApproverNodeCreator.COUNTER_SIGN)) {
                // 会签
                // 用户节点
                UserTask userTask = new UserTask();
                userTask.setId(node.getNodeId());
                userTask.setName(node.getProperties().getTitle());
                MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = new MultiInstanceLoopCharacteristics();
                // 审批人集合参数
                multiInstanceLoopCharacteristics.setInputDataItem("${assigneeList}");
                // 迭代集合
                multiInstanceLoopCharacteristics.setElementVariable("assigneeName");
                // 完成条件 已完成数等于实例数
                multiInstanceLoopCharacteristics.setCompletionCondition("${nrOfActiveInstances == nrOfInstances}");
                // 并行
                multiInstanceLoopCharacteristics.setSequential(false);
                userTask.setAssignee("${assigneeName}");
                // 设置多实例属性
                userTask.setLoopCharacteristics(multiInstanceLoopCharacteristics);

                if (node.getProperties().getAssigneeType().equals(ApproverNodeCreator.ASSIGNEE_TYPE_USER)) {
                    List<TreeSelectModel> users = node.getProperties().getApprovers();
                    List<String> userIds = users.stream().map(TreeSelectModel::getId).collect(Collectors.toList());
                    userTask.setCandidateUsers(userIds);
                    multiInstanceLoopCharacteristics.setInputDataItem("${assigneeList}");
                    userTask.setExecutionListeners(getGroupCounterSignListener(StringUtils.join(userIds, ","), ApproverNodeCreator.ASSIGNEE_TYPE_USER));
                } else if (node.getProperties().getAssigneeType().equals(ApproverNodeCreator.ASSIGNEE_TYPE_ROLE)) {
                    List<TreeSelectModel> groups = node.getProperties().getApproverRoles();
                    List<String> roleIds = groups.stream().map(TreeSelectModel::getId).collect(Collectors.toList());
                    StringBuffer sb = new StringBuffer();
                    roleIds.stream().forEach(str -> {
                        sb.append(str).append(",");
                    });
                    sb.deleteCharAt(sb.length() - 1);

                    userTask.setExecutionListeners(getGroupCounterSignListener(sb.toString(), ApproverNodeCreator.ASSIGNEE_TYPE_ROLE));
                } else if (node.getProperties().getAssigneeType().equals(ApproverNodeCreator.ASSIGNEE_TYPE_INPUT)) {
                    userTask.setAssignee(node.getProperties().getExpression());
                    multiInstanceLoopCharacteristics.setInputDataItem(node.getProperties().getExpression());
                } else if (node.getProperties().getAssigneeType().equals(ApproverNodeCreator.ASSIGNEE_TYPE_MYSELF)) {
                    userTask.setAssignee("${apply}");
                    multiInstanceLoopCharacteristics.setInputDataItem("${apply}");
                }
                tasks.add(userTask);
            } else {
                // 或签

                UserTask userTask = new UserTask();
                userTask.setName(node.getProperties().getTitle());
                userTask.setId(node.getNodeId());

                if (node.getProperties().getAssigneeType().equals(ApproverNodeCreator.ASSIGNEE_TYPE_USER)) {
                    List<TreeSelectModel> users = node.getProperties().getApprovers();
                    List<String> userIds = users.stream().map(TreeSelectModel::getId).collect(Collectors.toList());
                    userTask.setCandidateUsers(userIds);
                } else if (node.getProperties().getAssigneeType().equals(ApproverNodeCreator.ASSIGNEE_TYPE_ROLE)) {
                    List<TreeSelectModel> groups = node.getProperties().getApproverRoles();
                    List<String> roleIds = groups.stream().map(TreeSelectModel::getId).collect(Collectors.toList());
                    userTask.setCandidateGroups(roleIds);
                } else if (node.getProperties().getAssigneeType().equals(ApproverNodeCreator.ASSIGNEE_TYPE_INPUT)) {
                    List<String> userList = new ArrayList<String>();
                    userList.add(node.getProperties().getExpression());
                    userTask.setCandidateUsers(userList);
                } else if (node.getProperties().getAssigneeType().equals(ApproverNodeCreator.ASSIGNEE_TYPE_MYSELF)) {
                    userTask.setAssignee("${apply}");
                }
                tasks.add(userTask);
            }
        }

        return tasks;
    }

    private static List<ActivitiListener> getGroupCounterSignListener(String list, String listType) {
        ArrayList<ActivitiListener> listeners = new ArrayList<>();
        ActivitiListener activitiListener = new ActivitiListener();
        // 事件类型,
        activitiListener.setEvent(ExecutionListener.EVENTNAME_START);
        // 监听器类型
        activitiListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
        // 设置实现了，这里设置监听器的类型是delegateExpression，这样可以在实现类注入Spring bean.
        activitiListener.setImplementation("${groupCounterSignListener}");

        List<FieldExtension> fieldExtensions = new ArrayList<FieldExtension>();

        if (listType.equals(ApproverNodeCreator.ASSIGNEE_TYPE_USER)) {
            FieldExtension userListField = new FieldExtension();
            userListField.setFieldName("userList");
            userListField.setStringValue(list);
            fieldExtensions.add(userListField);
        } else if (listType.equals(ApproverNodeCreator.ASSIGNEE_TYPE_ROLE)) {
            FieldExtension roleListField = new FieldExtension();
            roleListField.setFieldName("roleList");
            roleListField.setStringValue(list);
            fieldExtensions.add(roleListField);
        } else {
            throw new DataException("请求的参数不合法");
        }
        

        activitiListener.setFieldExtensions(fieldExtensions);
        listeners.add(activitiListener);
        return listeners;
    }
}
