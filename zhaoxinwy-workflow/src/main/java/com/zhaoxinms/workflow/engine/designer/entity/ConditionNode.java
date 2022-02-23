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

public class ConditionNode extends Node {

    private ConditionProperties properties;

    public ConditionProperties getProperties() {
        return properties;
    }

    public void setProperties(ConditionProperties properties) {
        this.properties = properties;
    }
}