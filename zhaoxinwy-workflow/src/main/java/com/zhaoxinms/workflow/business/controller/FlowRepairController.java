package com.zhaoxinms.workflow.business.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.zhaoxinms.workflow.business.entity.FlowRepair;
import com.zhaoxinms.workflow.business.entity.vo.FlowRepairVo;
import com.zhaoxinms.workflow.business.entity.bo.FlowRepairBo;
import com.zhaoxinms.workflow.business.entity.pagination.FlowRepairPagination;
import com.zhaoxinms.workflow.business.service.IFlowRepairService;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;
/**
 * 报事工单Controller
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

    /**
     * 查询报事工单列表
     */
    @PreAuthorize("@ss.hasPermi('business:repair:list')")
    @GetMapping("/list")
    public ActionResult list(FlowRepairPagination flowRepairPagination) {
        List<FlowRepair> list = flowRepairService.getList(flowRepairPagination);
        List<FlowRepairVo> listVO = JsonUtil.getJsonToList(list, FlowRepairVo.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(flowRepairPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 获取报事工单详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:repair:query')")
    @GetMapping(value = "/{id}")
 	public ActionResult<FlowRepairVo> info(@PathVariable("id") String id) {
        FlowRepair entity = flowRepairService.getInfo(id);
        FlowRepairVo vo = JsonUtil.getJsonToBean(entity, FlowRepairVo.class);
        return ActionResult.success(vo);
    }

    /**
     * 新增报事工单
     */
    @PreAuthorize("@ss.hasPermi('business:repair:add')")
    @Log(title = "报事工单", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@Validated(AddGroup.class) @RequestBody FlowRepairBo bo) throws DataException {
        SysUser userInfo = userProvider.get();
        FlowRepair entity = JsonUtil.getJsonToBean(bo, FlowRepair.class);
        flowRepairService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 修改报事工单
     */
    @PreAuthorize("@ss.hasPermi('business:repair:edit')")
    @Log(title = "报事工单", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id, @Validated(EditGroup.class) @RequestBody FlowRepairBo bo)
        throws DataException {
        FlowRepair entity = JsonUtil.getJsonToBean(bo, FlowRepair.class);
        flowRepairService.update(id, entity);
        return ActionResult.success("更新成功");
    }

    /**
     * 删除报事工单
     */
    @PreAuthorize("@ss.hasPermi('business:repair:remove')")
    @Log(title = "报事工单", businessType = BusinessType.DELETE)
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
