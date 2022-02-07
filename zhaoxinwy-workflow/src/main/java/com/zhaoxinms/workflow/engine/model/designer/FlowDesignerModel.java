package com.zhaoxinms.workflow.engine.model.designer;

public class FlowDesignerModel {

    private BasicSetting basicSetting;
    private ProcessData processData;

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

}