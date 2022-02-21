package com.zhaoxinms.workflow.engine.designer.entity;

import java.util.List;

public class StartProperties {

    private String title;
    private String initiator;
    private List<FormOperate> formOperates;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getInitiator() {
        return initiator;
    }
    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }
    public List<FormOperate> getFormOperates() {
        return formOperates;
    }
    public void setFormOperates(List<FormOperate> formOperates) {
        this.formOperates = formOperates;
    }
}