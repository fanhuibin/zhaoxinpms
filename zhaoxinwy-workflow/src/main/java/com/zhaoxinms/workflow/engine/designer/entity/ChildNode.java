package com.zhaoxinms.workflow.engine.designer.entity;

import java.util.List;

public class ChildNode extends Node{

    private ChildProperties properties;
    //外层id
    private String outerNodeId;

    public ChildProperties getProperties() {
        return properties;
    }

    public void setProperties(ChildProperties properties) {
        this.properties = properties;
    }

    public String getOuterNodeId() {
        return outerNodeId;
    }

    public void setOuterNodeId(String outerNodeId) {
        this.outerNodeId = outerNodeId;
    }
}