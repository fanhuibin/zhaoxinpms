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

public class FormOperate {
    private Integer formId;
    private boolean required;
    private String vModel;
    private String label;
    private boolean read;
    private boolean write;
    
    public Integer getFormId() {
        return formId;
    }
    public void setFormId(Integer formId) {
        this.formId = formId;
    }
    public boolean isRequired() {
        return required;
    }
    public void setRequired(boolean required) {
        this.required = required;
    }
    public String getvModel() {
        return vModel;
    }
    public void setvModel(String vModel) {
        this.vModel = vModel;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public boolean isRead() {
        return read;
    }
    public void setRead(boolean read) {
        this.read = read;
    }
    public boolean isWrite() {
        return write;
    }
    public void setWrite(boolean write) {
        this.write = write;
    }
}
