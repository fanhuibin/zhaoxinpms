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

import com.zhaoxinms.workflow.engine.model.TreeSelectModel;

public class ChildProperties {

    private String title;
    private List<TreeSelectModel> approvers;
    private List<TreeSelectModel> approverRoles;
    private String assigneeType;
    private String optionalMultiUser;
    private String expression;
    private List<FormOperate> formOperates;
    private String rejectConfig; //是否可以驳回
    private String rejectNodeName; //驳回配置
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<TreeSelectModel> getApprovers() {
        return approvers;
    }
    public void setApprovers(List<TreeSelectModel> approvers) {
        this.approvers = approvers;
    }
    public List<TreeSelectModel> getApproverRoles() {
        return approverRoles;
    }
    public void setApproverRoles(List<TreeSelectModel> approverRoles) {
        this.approverRoles = approverRoles;
    }
    public String getAssigneeType() {
        return assigneeType;
    }
    public void setAssigneeType(String assigneeType) {
        this.assigneeType = assigneeType;
    }
    public String getOptionalMultiUser() {
        return optionalMultiUser;
    }
    public void setOptionalMultiUser(String optionalMultiUser) {
        this.optionalMultiUser = optionalMultiUser;
    }
    public String getExpression() {
        return expression;
    }
    public void setExpression(String expression) {
        this.expression = expression;
    }
    public List<FormOperate> getFormOperates() {
        return formOperates;
    }
    public void setFormOperates(List<FormOperate> formOperates) {
        this.formOperates = formOperates;
    }
    public String getRejectConfig() {
        return rejectConfig;
    }
    public void setRejectConfig(String rejectConfig) {
        this.rejectConfig = rejectConfig;
    }
    public String getRejectNodeName() {
        return rejectNodeName;
    }
    public void setRejectNodeName(String rejectNodeName) {
        this.rejectNodeName = rejectNodeName;
    }
}