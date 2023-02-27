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
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.UserTask;

import com.zhaoxinms.common.exception.ServiceException;
import com.zhaoxinms.common.utils.JsonUtil;
import com.zhaoxinms.common.utils.StringUtils;
import com.zhaoxinms.workflow.engine.designer.entity.ChildNode;
import com.zhaoxinms.workflow.engine.designer.entity.ConditionNode;
import com.zhaoxinms.workflow.engine.designer.entity.FlowDesignerModel;
import com.zhaoxinms.workflow.engine.designer.entity.Node;
import com.zhaoxinms.workflow.engine.designer.entity.ProcessData;
import com.zhaoxinms.workflow.engine.designer.node.ApproverNodeCreator;
import com.zhaoxinms.workflow.engine.designer.node.ConditionNodeCreator;

public class DesignerAdapterUtil {

    public static String JSONToBPMN(String json) {
        FlowDesignerModel model = JsonUtil.getJsonToBean(json, FlowDesignerModel.class);
        ProcessData process = model.getProcessData();

        // 解析json
        List<ChildNode> allChildNodes = new ArrayList<ChildNode>();
        List<ConditionNode> conditions = new ArrayList<ConditionNode>();

        if (process.getChildNode() != null) {
            DesignerAdapterUtil.getChildNode("", process.getChildNode(), allChildNodes, conditions);
        }
        if (process.getConditionNodes() != null) {
            DesignerAdapterUtil.getConditionNode(process.getConditionNodes(), allChildNodes, conditions);
        }

        // 抽取nodeId
        List<ChildNode> approverNodes = allChildNodes.stream().filter(childNode -> "approver".equals(childNode.getType())).collect(Collectors.toList());
        Map<String, ChildNode> approverMap = approverNodes.stream().collect(Collectors.toMap(ChildNode::getNodeId, ChildNode -> ChildNode));
        Map<String, ConditionNode> conditionMap = conditions.stream().collect(Collectors.toMap(ConditionNode::getNodeId, ConditionNode -> ConditionNode));

        // 创建抄送service
        // TODO 暂未开发

        // 创建userTask
        List<UserTask> userTasks = ApproverNodeCreator.createUserTask(approverNodes);

        // 创建排他网关
        List<ExclusiveGateway> gateways = ConditionNodeCreator.createGateWay(conditions);

        // 创建箭头
        List<SequenceFlow> taskFlows = new ArrayList<SequenceFlow>();
        DesignerAdapterUtil.createUserTaskFlow(process, approverNodes, allChildNodes, conditions, taskFlows);
        DesignerAdapterUtil.createConditionFlow(process, allChildNodes, conditions, taskFlows);

        // 创建start节点的箭头
        String startNextId = DesignerAdapterUtil.nextNodeId(process, process, allChildNodes, conditions);
        SequenceFlow startFlow = new SequenceFlow();
        startFlow.setSourceRef(process.getNodeId());
        startFlow.setTargetRef(startNextId);
        taskFlows.add(startFlow);

        return BPMNCreator.createXML(json, userTasks, gateways, taskFlows);
    }

    /**
     * 查询指定的childNode
     * 
     * @param json
     * @param taskDefKey
     * @return
     */
    public static ChildNode getNodeByName(String json, String taskName) {
        FlowDesignerModel model = JsonUtil.getJsonToBean(json, FlowDesignerModel.class);
        ProcessData process = model.getProcessData();
        List<ChildNode> allChildNodes = new ArrayList<ChildNode>();
        List<ConditionNode> conditions = new ArrayList<ConditionNode>();
        DesignerAdapterUtil.getChildNode("", process.getChildNode(), allChildNodes, conditions);
        for (ChildNode node : allChildNodes) {
            if (node.getProperties().getTitle().equals(taskName)) {
                return node;
            }
        }
        return null;
    }
    
    /**
     *通过nodeName查询 taskDefKey
     * 
     * @param json
     * @param taskDefKey
     * @return
     */
    public static ChildNode getChildNode(String json, String taskDefKey) {
        FlowDesignerModel model = JsonUtil.getJsonToBean(json, FlowDesignerModel.class);
        ProcessData process = model.getProcessData();
        List<ChildNode> allChildNodes = new ArrayList<ChildNode>();
        List<ConditionNode> conditions = new ArrayList<ConditionNode>();
        DesignerAdapterUtil.getChildNode("", process.getChildNode(), allChildNodes, conditions);
        for (ChildNode node : allChildNodes) {
            if (node.getNodeId().equals(taskDefKey)) {
                return node;
            }
        }
        return null;
    }

    private static void createConditionFlow(ProcessData process, List<ChildNode> allChildNodes, List<ConditionNode> conditions, List<SequenceFlow> taskFlows) {
        for (ConditionNode condition : conditions) {
            SequenceFlow flow = new SequenceFlow();
            flow.setSourceRef(condition.getPrevId() + ConditionNodeCreator.GATEWAY_SUFFIX);
            flow.setTargetRef(DesignerAdapterUtil.nextNodeId(condition, process, allChildNodes, conditions));
            flow.setName(condition.getProperties().getConditionLabel());
            flow.setConditionExpression(condition.getProperties().getCondition());
            taskFlows.add(flow);
        }
    }

    private static void createUserTaskFlow(ProcessData process, List<ChildNode> approverNodes, List<ChildNode> allChildNodes, List<ConditionNode> conditions,
        List<SequenceFlow> taskFlows) {
        for (ChildNode node : approverNodes) {
            SequenceFlow flow = new SequenceFlow();
            flow.setSourceRef(node.getNodeId());
            flow.setTargetRef(DesignerAdapterUtil.nextNodeId(node, process, allChildNodes, conditions));
            taskFlows.add(flow);
        }
    }

    /**
     * 获取node的下一个节点
     **/
    private static String nextNodeId(Node currentNode, ProcessData process, List<ChildNode> allChildNodes, List<ConditionNode> conditions) {
        String nextId = "";

        // 当前节点有条件分流，则优先指向条件分流
        if (currentNode.getConditionNodes() != null && currentNode.getConditionNodes().size() > 0) {
            nextId = currentNode.getNodeId() + ConditionNodeCreator.GATEWAY_SUFFIX;
        } else if (currentNode.getChildNode() != null) {
            // 当前节点没有条件分流，有childNode
            ChildNode nextNode = currentNode.getChildNode();
            if (nextNode.getType().equals("approver")) {
                nextId = nextNode.getNodeId();
            } else if (nextNode.getType().equals("empty")) {
                throw new ServiceException("json数据格式错误，父节点没有condition，这个不该是empty");
            } else {
                throw new ServiceException("暂未实现");
            }
        } else {
            // 既没有childNode，也没有conditionNode.跳转到外层
            String outerNodeId = "";
            if (currentNode instanceof ChildNode) {
                outerNodeId = ((ChildNode)currentNode).getOuterNodeId();
            } else if (currentNode instanceof ConditionNode) {
                outerNodeId = ((ConditionNode)currentNode).getPrevId();
            }
            return getOuterNextNode(outerNodeId, process, allChildNodes, conditions);
        }

        return nextId;
    }

    /**
     * outer的下一个节点就是当前跳出的conditons, 所以这个next应该是找下下一个节点
     * 
     * @return
     */
    private static String getOuterNextNode(String outerId, ProcessData process, List<ChildNode> allChildNodes, List<ConditionNode> conditions) {
        // 如果没有条件分支，也没有childNode,查询outerNextNode
        Map<String, ChildNode> childNodeMap = allChildNodes.stream().collect(Collectors.toMap(ChildNode::getNodeId, ChildNode -> ChildNode));
        Map<String, ConditionNode> conditionNodeMap = conditions.stream().collect(Collectors.toMap(ConditionNode::getNodeId, ConditionNode -> ConditionNode));
        if (StringUtils.isEmpty(outerId)) {
            // 当前节点是最外层，直接指向end
            return "end";
        } else {
            ChildNode outerNode = childNodeMap.get(outerId);
            ConditionNode condition = conditionNodeMap.get(outerId);
            ChildNode outerChildNode = null;
            String outerOuterNodeId = "";
            if (outerNode != null) {
                outerChildNode = outerNode.getChildNode();
                outerOuterNodeId = outerNode.getOuterNodeId();
            } else if (condition != null) {
                outerChildNode = condition.getChildNode();
                outerOuterNodeId = condition.getPrevId();
            } else {
                // 既非conditionNode ,又非childNode. 那只能是startNode
                if (process.getChildNode() != null) {
                    outerChildNode = process.getChildNode();
                    outerOuterNodeId = "";
                }
            }

            if (outerChildNode != null) {
                if (outerChildNode.getType().equals("empty")) {
                    // emptyNode本身不存在，所以需要查找下一个nextNodeId
                    return nextNodeId(outerChildNode, process, allChildNodes, conditions);
                } else {
                    // 非emptyNode，则返回nodeId
                    return outerChildNode.getNodeId();
                }
            } else {
                // 跳转到外层的外层
                return getOuterNextNode(outerOuterNodeId, process, allChildNodes, conditions);
            }
        }
    }

    private static void getChildNode(String outerNodeId, ChildNode child, List<ChildNode> allChildNodes, List<ConditionNode> conditions) {

        if (child.getType().equals("copy")) {
            throw new ServiceException("抄送功能暂未实现，请不要选择抄送");
        }
        allChildNodes.add(child);
        child.setOuterNodeId(outerNodeId);

        if (child.getChildNode() != null) {
            getChildNode(outerNodeId, child.getChildNode(), allChildNodes, conditions);
        }
        if (child.getConditionNodes() != null && child.getConditionNodes().size() > 0) {
            getConditionNode(child.getConditionNodes(), allChildNodes, conditions);
        }
    }

    private static void getConditionNode(List<ConditionNode> conditionNodes, List<ChildNode> allChildNodes, List<ConditionNode> conditions) {
        for (ConditionNode c : conditionNodes) {
            conditions.add(c);
            if (c.getChildNode() != null) {
                DesignerAdapterUtil.getChildNode(c.getPrevId(), c.getChildNode(), allChildNodes, conditions);
            }
            if (c.getConditionNodes() != null) {
                getConditionNode(c.getConditionNodes(), allChildNodes, conditions);
            }
        }
    }
}
