package com.zhaoxinms.workflow.engine.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.task.IdentityLink;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.system.mapper.SysUserMapper;
import com.zhaoxinms.system.service.ISysRoleService;

@Component
public class GroupCounterSignListener implements ExecutionListener {

    private static final long serialVersionUID = 1L;
    private FixedValue roleList;
    private FixedValue userList;
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public void notify(DelegateExecution execution) {
        String eventName = execution.getEventName();
        if ("start".equals(eventName)) {
            List<String> assigneeList = new ArrayList<>();
            // 判断roleList
            if (roleList != null) {
                String roles = (String)roleList.getValue(execution);
                String[] rolesArray = roles.split(",");

                for (String s : rolesArray) {
                    List<SysUser> list = userMapper.selectUserListByRoleKey(s);
                    for (SysUser user : list) {
                        assigneeList.add(user.getUserName());
                    }
                }
            }
            // 判断userList
            if (userList != null) {
                String users = (String)userList.getValue(execution);
                String[] usersArray = users.split(",");
                for (String s : usersArray) {
                    assigneeList.add(s);
                }
            }
            execution.setVariable("assigneeList", assigneeList);
        }
    }

    public FixedValue getRoleList() {
        return roleList;
    }

    public void setRoleList(FixedValue roleList) {
        this.roleList = roleList;
    }

}
