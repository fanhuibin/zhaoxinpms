package com.zhaoxinms.workflow.engine.entity;

import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityImpl;

/**
 * @author 一只闲鹿
 */
public class HistoricActivity extends HistoricActivityInstanceEntityImpl {

    /** 审批批注 */
    private String comment;

    /** 办理人姓名 */
    private String assigneeName;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

}
