package com.zhaoxinms.workflow.engine.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class ServiceTaskExecListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) {
        //TODO 开发抄送功能，暂未实现
        System.out.println("执行了");
    }
}
