package com.zhaoxinms.workflow.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.workflow.business.entity.FlowComplaints;
import com.zhaoxinms.workflow.business.entity.bo.FlowComplaintsBo;
import com.zhaoxinms.workflow.business.entity.pagination.FlowComplaintsPagination;
import com.zhaoxinms.workflow.business.entity.vo.FlowComplaintsVo;
import com.zhaoxinms.workflow.business.service.IFlowComplaintsService;
import com.zhaoxinms.workflow.engine.service.IProcessService;
/**
 * 投诉工单Controller
 * 
 * @author ruoyi
 * @date 2022-01-24
 */
@RestController
@RequestMapping("/business/complaints")
public class FlowComplaintsController
{

	@Autowired
    private UserProvider userProvider;
    @Autowired
    private IFlowComplaintsService flowComplaintsService;
    @Autowired
    private IProcessService processService;
    
    /**
     * 查询投诉工单列表
     */
    @PreAuthorize("@ss.hasPermi('business:complaints:list')")
    @GetMapping("/list")
    public ActionResult list(FlowComplaintsPagination flowComplaintsPagination) {
        List<FlowComplaints> list = flowComplaintsService.getList(flowComplaintsPagination);
        List<FlowComplaintsVo> listVO = JsonUtil.getJsonToList(list, FlowComplaintsVo.class);
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
        PaginationVO page = JsonUtil.getJsonToBean(flowComplaintsPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 获取投诉工单详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:complaints:query')")
    @GetMapping(value = "/{id}")
 	public ActionResult<FlowComplaintsVo> info(@PathVariable("id") String id) {
        FlowComplaints entity = flowComplaintsService.getInfo(id);
        FlowComplaintsVo vo = JsonUtil.getJsonToBean(entity, FlowComplaintsVo.class);
        return ActionResult.success(vo);
    }
    
    /**
     * 获取投诉工单详细信息
     * @throws Exception 
     */
    @GetMapping(value = "/instanceId/{instanceId}")
    public ActionResult<FlowComplaintsVo> complaintsInfo(@PathVariable("instanceId") String instanceId) throws Exception {
        FlowComplaints entity = flowComplaintsService.getInfoByInstanceId(instanceId);
        FlowComplaintsVo vo = JsonUtil.getJsonToBean(entity, FlowComplaintsVo.class);
        processService.richProcessField(vo);
        return ActionResult.success(vo);
    }

    /**
     * 新增投诉工单
     */
    @PreAuthorize("@ss.hasPermi('business:complaints:add')")
    @Log(title = "投诉工单", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@Validated(AddGroup.class) @RequestBody FlowComplaintsBo bo) throws DataException {
        SysUser userInfo = userProvider.get();
        FlowComplaints entity = JsonUtil.getJsonToBean(bo, FlowComplaints.class);
        flowComplaintsService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 修改投诉工单
     */
    @Log(title = "投诉工单", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id, @Validated(EditGroup.class) @RequestBody FlowComplaintsBo bo)
        throws DataException {
        flowComplaintsService.update(id, bo);
        return ActionResult.success("更新成功");
    }

    /**
     * 删除投诉工单
     */
    @PreAuthorize("@ss.hasPermi('business:complaints:remove')")
    @Log(title = "投诉工单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
	@Transactional
    public ActionResult delete(@PathVariable("id") String id) {
        FlowComplaints entity = flowComplaintsService.getInfo(id);
        if (entity != null) {
            flowComplaintsService.delete(entity);
        }
        return ActionResult.success("删除成功");
    }
}
