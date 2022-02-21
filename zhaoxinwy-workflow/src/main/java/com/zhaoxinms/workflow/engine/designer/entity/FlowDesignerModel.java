package com.zhaoxinms.workflow.engine.designer.entity;

import com.alibaba.fastjson.JSONObject;

public class FlowDesignerModel {

    private BasicSetting basicSetting;
    private ProcessData processData;
    private JSONObject formData;

    public void setBasicSetting(BasicSetting basicSetting) {
        this.basicSetting = basicSetting;
    }

    public BasicSetting getBasicSetting() {
        return basicSetting;
    }

    public void setProcessData(ProcessData processData) {
        this.processData = processData;
    }

    public ProcessData getProcessData() {
        return processData;
    }

    public JSONObject getFormData() {
        return formData;
    }

    public void setFormData(JSONObject formData) {
        this.formData = formData;
    }
}