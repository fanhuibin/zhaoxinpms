package com.zhaoxinms.workflow.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.domain.AjaxResult;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/workflow/designer")
@Api
public class WorkflowDesignerController {


    //新增流程
    @RequestMapping("/add")
    public AjaxResult add(@RequestBody String json) {
        System.out.println(json);
        return AjaxResult.success();
    }
}
