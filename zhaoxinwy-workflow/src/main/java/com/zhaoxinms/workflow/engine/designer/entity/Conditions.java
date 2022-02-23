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

public class Conditions {

    private String condition;
    private String conditonLabel;

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public void setConditonLabel(String conditonLabel) {
        this.conditonLabel = conditonLabel;
    }

    public String getConditonLabel() {
        return conditonLabel;
    }

}