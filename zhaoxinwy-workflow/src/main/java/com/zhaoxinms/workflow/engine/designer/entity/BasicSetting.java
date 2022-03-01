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

public class BasicSetting {

    private String flowName;
    private String flowIcon;
    private String flowIconBackground;
    private int flowGroup;
    private String flowRemark;
    private String flowCode;
    private String id;

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowName() {
        return flowName;
    }

    public String getFlowIcon() {
        return flowIcon;
    }

    public void setFlowIcon(String flowIcon) {
        this.flowIcon = flowIcon;
    }

    public String getFlowIconBackground() {
        return flowIconBackground;
    }

    public void setFlowIconBackground(String flowIconBackground) {
        this.flowIconBackground = flowIconBackground;
    }

    public void setFlowGroup(int flowGroup) {
        this.flowGroup = flowGroup;
    }

    public int getFlowGroup() {
        return flowGroup;
    }

    public void setFlowRemark(String flowRemark) {
        this.flowRemark = flowRemark;
    }

    public String getFlowRemark() {
        return flowRemark;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public String getFlowCode() {
        return flowCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}