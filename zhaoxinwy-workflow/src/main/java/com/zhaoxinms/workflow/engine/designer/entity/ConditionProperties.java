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

public class ConditionProperties {

    private String title;
    private List<Conditions> conditions;
    private String initiator;
    private int priority;
    private String condition;
    private String conditionLabel;
    private boolean isDefault;
    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setConditions(List<Conditions> conditions) {
         this.conditions = conditions;
     }
     public List<Conditions> getConditions() {
         return conditions;
     }

    public void setInitiator(String initiator) {
         this.initiator = initiator;
     }
     public String getInitiator() {
         return initiator;
     }

    public void setPriority(int priority) {
         this.priority = priority;
     }
     public int getPriority() {
         return priority;
     }

    public void setCondition(String condition) {
         this.condition = condition;
     }
     public String getCondition() {
         return condition;
     }

    public void setConditionLabel(String conditionLabel) {
         this.conditionLabel = conditionLabel;
     }
     public String getConditionLabel() {
         return conditionLabel;
     }

    public void setIsDefault(boolean isDefault) {
         this.isDefault = isDefault;
     }
     public boolean getIsDefault() {
         return isDefault;
     }

}