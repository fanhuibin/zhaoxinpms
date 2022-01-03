package com.zhaoxinms.workflow.engine.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.IdentityLink;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CreateTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        /**
         * 对应 activiti:assignee="xxx" 配置的 xxx
         * act_ru_identitylink 表中 TYPE=participant，并且PROC_INST_ID_不为null（表示是该流程实例的参与人，确切的事情）
         * act_ru_task 表中ASSIGNEE_不为null
         */
        String assignee = delegateTask.getAssignee();

        /**
         * 对应 activiti:candidateUsers="xxx" 或 activiti:candidateGroups="xxx" 配置的 xxx
         * act_ru_identitylink 表中 TYPE=candidate，并且PROC_INST_ID_为null（表示不确定，因为只是候选用户和组，不一定真的参与）
         * 但是 TASK_ID_指向了具体act_ru_task（表示可能会参与该任务）
         */
        Set<IdentityLink> candidates = delegateTask.getCandidates();

        System.out.println("assignee: " + assignee);
        System.out.println("candidates: " + candidates);
    }
}
