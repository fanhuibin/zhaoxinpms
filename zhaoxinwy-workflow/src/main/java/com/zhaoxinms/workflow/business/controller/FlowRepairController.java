package com.zhaoxinms.workflow.business.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.annotation.RepeatSubmit;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.core.page.TableDataInfo;
import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;
import com.zhaoxinms.common.core.validate.QueryGroup;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.common.utils.poi.ExcelUtil;
import com.zhaoxinms.workflow.business.entity.FlowRepair;
import com.zhaoxinms.workflow.business.entity.vo.FlowRepairVo;
import com.zhaoxinms.workflow.business.entity.bo.FlowRepairBo;
import com.zhaoxinms.workflow.business.entity.pagination.FlowRepairPagination;
import com.zhaoxinms.workflow.business.service.IFlowRepairService;
import com.zhaoxinms.workflow.engine.service.IProcessService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;
/**
 * 报修工单Controller
 * 
 * @author ruoyi
 * @date 2022-01-06
 */
@RestController
@RequestMapping("/business/repair")
public class FlowRepairController
{

	@Autowired
    private UserProvider userProvider;
    @Autowired
    private IFlowRepairService flowRepairService;
    @Autowired
    private IProcessService processService;
    
    /**
     * 查询报修工单列表
     */
    @PreAuthorize("@ss.hasPermi('business:repair:list')")
    @GetMapping("/list")
    public ActionResult list(FlowRepairPagination flowRepairPagination) {
        List<FlowRepair> list = flowRepairService.getList(flowRepairPagination);
        List<FlowRepairVo> listVO = JsonUtil.getJsonToList(list, FlowRepairVo.class);
        
        //查询流程信息
        if (!CollectionUtils.isEmpty(listVO)) {
            listVO.forEach(item -> { 
                try {
                    processService.richProcessField(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(flowRepairPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 获取报修工单详细信息
     * @throws Exception 
     */
    @PreAuthorize("@ss.hasPermi('business:repair:query')")
    @GetMapping(value = "/{id}")
 	public ActionResult<FlowRepairVo> info(@PathVariable("id") String id) throws Exception {
        FlowRepair entity = flowRepairService.getInfo(id);
        FlowRepairVo vo = JsonUtil.getJsonToBean(entity, FlowRepairVo.class);
        processService.richProcessField(vo);
        return ActionResult.success(vo);
    }
    
    /**
     * 获取报修工单详细信息
     * @throws Exception 
     */
    @GetMapping(value = "/instanceId/{instanceId}")
    public ActionResult<FlowRepairVo> repairInfo(@PathVariable("instanceId") String instanceId) throws Exception {
        FlowRepair entity = flowRepairService.getInfoByInstanceId(instanceId);
        FlowRepairVo vo = JsonUtil.getJsonToBean(entity, FlowRepairVo.class);
        processService.richProcessField(vo);
        return ActionResult.success(vo);
    }

    /**
     * 新增报修工单
     */
    @PreAuthorize("@ss.hasPermi('business:repair:add')")
    @Log(title = "报修工单", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@Validated(AddGroup.class) @RequestBody FlowRepairBo bo) throws DataException {
        SysUser userInfo = userProvider.get();
        FlowRepair entity = JsonUtil.getJsonToBean(bo, FlowRepair.class);
        flowRepairService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 修改报修工单
     */
    @Log(title = "报修工单", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id, @Validated(EditGroup.class) @RequestBody FlowRepairBo bo)
        throws DataException {
        flowRepairService.update(id, bo);
        return ActionResult.success("更新成功");
    }

    /**
     * 删除报修工单
     */
    @PreAuthorize("@ss.hasPermi('business:repair:remove')")
    @Log(title = "报修工单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
	@Transactional
    public ActionResult delete(@PathVariable("id") String id) {
        FlowRepair entity = flowRepairService.getInfo(id);
        if (entity != null) {
            flowRepairService.delete(entity);
        }
        return ActionResult.success("删除成功");
    }
}
