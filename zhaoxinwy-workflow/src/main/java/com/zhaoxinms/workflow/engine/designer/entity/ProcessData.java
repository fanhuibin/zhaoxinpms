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

public class ProcessData extends Node{

    private StartProperties properties;

    public StartProperties getProperties() {
        return properties;
    }

    public void setProperties(StartProperties properties) {
        this.properties = properties;
    }
}