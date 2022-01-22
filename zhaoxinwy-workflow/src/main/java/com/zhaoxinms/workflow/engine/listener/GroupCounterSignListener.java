package com.zhaoxinms.workflow.engine.listener;

import java.util.List;
import java.util.Set;

import org.activiti.engine.delegate.DelegateTask;
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
public class GroupCounterSignListener implements TaskListener {

    private static final long serialVersionUID = 1L;
    private FixedValue roleList;
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public void notify(DelegateTask delegateTask) {
        String roles = (String)roleList.getValue(delegateTask);
        StringBuffer assigneeList = new StringBuffer();
        //判断roleList
        if(StringUtils.isNotEmpty(roles)) {
            String[] rolesArray = roles.split(",");
            
            for (String s : rolesArray) {
                List<SysUser> list = userMapper.selectUserListByRoleKey(s);
                for (SysUser user : list) {
                    assigneeList.append(user.getUserName()).append(",");
                }
            }
        }
        assigneeList.deleteCharAt(assigneeList.length() - 1);
        delegateTask.setVariable("assigneeList", assigneeList);
    }

    public FixedValue getRoleList() {
        return roleList;
    }

    public void setRoleList(FixedValue roleList) {
        this.roleList = roleList;
    }
}
