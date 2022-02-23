/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms
 * website：http://pms.zhaoxinms.com  wx： zhaoxinms
 *
 */
package com.zhaoxinms.workflow.engine.designer.entity;

import java.util.List;

public class Node {
    private String type;
    private String content;
    private String nodeId;
    private String prevId;
    private ChildNode childNode;
    private List<ConditionNode> conditionNodes;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getPrevId() {
        return prevId;
    }

    public void setPrevId(String prevId) {
        this.prevId = prevId;
    }

    public ChildNode getChildNode() {
        return childNode;
    }

    public void setChildNode(ChildNode childNode) {
        this.childNode = childNode;
    }

    public List<ConditionNode> getConditionNodes() {
        return conditionNodes;
    }

    public void setConditionNodes(List<ConditionNode> conditionNodes) {
        this.conditionNodes = conditionNodes;
    }
}
