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

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.vo.ListVO;
import com.zhaoxinms.common.config.RuoYiConfig;
import com.zhaoxinms.common.core.domain.AjaxResult;
import com.zhaoxinms.common.utils.JsonUtil;
import com.zhaoxinms.workflow.engine.designer.DesignerAdapterUtil;
import com.zhaoxinms.workflow.engine.designer.entity.FlowDesignerModel;
import com.zhaoxinms.workflow.engine.designer.entity.ProcessData;
import com.zhaoxinms.workflow.engine.entity.FlowDesignerEntity;
import com.zhaoxinms.workflow.engine.model.flowDesigner.PaginationFlowDesigner;
import com.zhaoxinms.workflow.engine.service.FlowDesignerService;
import com.zhaoxinms.workflow.engine.service.IProcessDefinitionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/workflow/designer")
@Api
public class WorkflowDesignerController {

    @Autowired
    private IProcessDefinitionService processDefinitionService;
    @Autowired
    private FlowDesignerService flowDesignerService;
    @Autowired
    private RuoYiConfig ruoYiConfig;

    // 部署流程
    @GetMapping("/deploy/{id}")
    public AjaxResult add(@PathVariable(name = "id") String id) {
        FlowDesignerEntity flowDesignerEntity = flowDesignerService.getById(id);
        String xml = DesignerAdapterUtil.JSONToBPMN(flowDesignerEntity.getJson());
        FlowDesignerModel model = JsonUtil.getJsonToBean(flowDesignerEntity.getJson(), FlowDesignerModel.class);
        processDefinitionService.deployXMLProcessDefinition(model.getBasicSetting().getFlowName(), xml);
        return AjaxResult.success("流程部署成功");
    }

    /**
     * 获取流程设计列表
     *
     * @return
     */
    @ApiOperation("获取流程设计列表")
    @GetMapping
    public ActionResult list(PaginationFlowDesigner pagination) {
        List<FlowDesignerEntity> list = flowDesignerService.getList(pagination);
        ListVO vo = new ListVO();
        vo.setList(list);
        return ActionResult.success(vo);
    }

    /**
     * 获取流程设计信息
     *
     * @param id
     *            主键值
     * @return
     */
    @ApiOperation("获取流程设计信息")
    @GetMapping("/{id}")
    public ActionResult info(@PathVariable("id") String id) throws DataException {
        FlowDesignerEntity flowDesignerEntity = flowDesignerService.getInfo(id);
        FlowDesignerModel model = JsonUtil.getJsonToBean(flowDesignerEntity.getJson(), FlowDesignerModel.class);
        model.getBasicSetting().setId(flowDesignerEntity.getId());
        flowDesignerEntity.setJson(JsonUtil.getObjectToString(model));
        return ActionResult.success(flowDesignerEntity);
    }

    /**
     * 新建流程设计
     *
     * @return
     */
    @ApiOperation("新建流程设计")
    @PostMapping
    public ActionResult create(@RequestBody String json) {

        FlowDesignerModel model = JsonUtil.getJsonToBean(json, FlowDesignerModel.class);
        String flowCode = model.getBasicSetting().getFlowCode();
        if (flowDesignerService.isExistByEnCode(flowCode, null)) {
            throw new DataException("流程编码已经存在，创建失败");
        }
        FlowDesignerEntity designer = new FlowDesignerEntity();
        designer.setJson(json);
        designer.setFullName(model.getBasicSetting().getFlowName());
        designer.setEnCode(model.getBasicSetting().getFlowCode());
        designer.setSortCode(0l);
        // designer.setCategory(model.getBasicSetting().getFlowGroup());
        designer.setType(model.getBasicSetting().getFlowGroup());
        designer.setIcon(model.getBasicSetting().getFlowIcon());
        designer.setIconBackground(model.getBasicSetting().getFlowIconBackground());
        flowDesignerService.create(designer);
        return ActionResult.success("新建成功");
    }

    /**
     * 更新流程设计
     *
     * @param id
     *            主键值
     * @return
     */
    @ApiOperation("更新流程设计")
    @PutMapping("/{id}")
    public ActionResult update(@RequestBody String json) {
        if(ruoYiConfig.isDemoEnabled()) {
            return ActionResult.fail("演示模式不允许修改流程");
        }
        
        FlowDesignerModel model = JsonUtil.getJsonToBean(json, FlowDesignerModel.class);
        ProcessData process = model.getProcessData();
        FlowDesignerEntity flowDesignerEntity = flowDesignerService.getById(model.getBasicSetting().getId());
        flowDesignerEntity.setJson(json);
        flowDesignerEntity.setIcon(model.getBasicSetting().getFlowIcon());
        flowDesignerEntity.setIconBackground(model.getBasicSetting().getFlowIconBackground());
        flowDesignerEntity.setFullName(model.getBasicSetting().getFlowName());
        boolean flag = flowDesignerService.update(model.getBasicSetting().getId(), flowDesignerEntity);
        if (flag == false) {
            return ActionResult.success("更新失败，数据不存在");
        }
        return ActionResult.success("更新成功");
    }
    
    
}
