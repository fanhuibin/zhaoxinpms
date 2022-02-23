/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms
 * website：http://pms.zhaoxinms.com  wx： zhaoxinms
 *
 */
package com.zhaoxinms.workflow.engine.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.common.core.domain.AjaxResult;
import com.zhaoxinms.common.core.domain.entity.SysDept;
import com.zhaoxinms.common.core.domain.entity.SysRole;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.system.service.ISysDeptService;
import com.zhaoxinms.system.service.ISysRoleService;
import com.zhaoxinms.system.service.ISysUserService;
import com.zhaoxinms.workflow.engine.model.TreeSelectModel;

@RestController
@RequestMapping("/workflow/treeSelect")
public class WorkflowSelectorController {

    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    public static final String DEPT_PREFIX = "dept";

    @RequestMapping("/userSelect")
    public AjaxResult userSelect() {
        List<TreeSelectModel> resultList = new ArrayList<TreeSelectModel>();
        List<TreeSelectModel> resultTree = new ArrayList<TreeSelectModel>();

        SysDept sysDept = new SysDept();
        List<SysDept> depts = deptService.selectDeptList(sysDept);
        for (SysDept dept : depts) {
            TreeSelectModel model = new TreeSelectModel();
            model.setId(WorkflowSelectorController.DEPT_PREFIX + dept.getDeptId());
            model.setName(dept.getDeptName());
            if (dept.getParentId() == 0) {
                model.setParentId("-1");
            } else {
                model.setParentId(WorkflowSelectorController.DEPT_PREFIX + dept.getParentId());
            }
            model.setType("dept");
            model.setIcon("icon-ym icon-ym-tree-organization3");
            resultList.add(model);
        }

        // 查询用户
        SysUser sysUser = new SysUser();
        List<SysUser> list = userService.selectUserList(sysUser);
        for (SysUser user : list) {
            TreeSelectModel tree = new TreeSelectModel();
            tree.setType("user");
            tree.setParentId(WorkflowSelectorController.DEPT_PREFIX + user.getDeptId());
            tree.setIcon("icon-ym icon-ym-tree-user2");
            tree.setId("" + user.getUserName());
            tree.setName(user.getNickName());
            resultList.add(tree);
        }

        // list转tree
        Map<String, List<TreeSelectModel>> ParentIdMap = resultList.stream().collect(Collectors.groupingBy(TreeSelectModel::getParentId));
        resultList.forEach(tree -> tree.setChildren(ParentIdMap.get(tree.getId())));
        List<TreeSelectModel> result = resultList.stream().filter(v -> v.getParentId().equals("-1")).collect(Collectors.toList());

        return AjaxResult.success(result);
    }

    @RequestMapping("/roleSelect")
    public AjaxResult roleSelect() {
        List<TreeSelectModel> result = new ArrayList<TreeSelectModel>();

        List<SysRole> roles = roleService.selectRoleAll();
        for (SysRole role : roles) {
            TreeSelectModel tree = new TreeSelectModel();
            tree.setType("role");
            tree.setParentId("-1");
            tree.setIcon("icon-ym icon-ym-tree-user2");
            tree.setId("" + role.getRoleKey());
            tree.setName(role.getRoleName());
            result.add(tree);
        }

        return AjaxResult.success(result);
    }
}
