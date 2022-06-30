package com.zhaoxinms.payment.controller;

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
import com.zhaoxinms.common.annotation.RepeatSubmit;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;
import com.zhaoxinms.common.core.validate.QueryGroup;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.common.utils.poi.ExcelUtil;
import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.payment.entity.SysSms;
import com.zhaoxinms.payment.entity.vo.SysSmsVo;
import com.zhaoxinms.payment.entity.bo.SysSmsBo;
import com.zhaoxinms.payment.entity.pagination.SysSmsPagination;
import com.zhaoxinms.payment.service.ISysSmsService;
import com.zhaoxinms.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;
/**
 * 消息推送记录Controller
 * 
 * @author fanhuibin
 * @date 2022-06-28
 */
@RestController
@RequestMapping("/payment/sms")
public class SysSmsController
{

	@Autowired
    private UserProvider userProvider;

    @Autowired
    private ISysSmsService sysSmsService;

    /**
     * 查询消息推送记录列表
     */
    @PreAuthorize("@ss.hasPermi('payment:sms:list')")
    @GetMapping("/list")
    public ActionResult list(SysSmsPagination sysSmsPagination) {
        List<SysSms> list = sysSmsService.getList(sysSmsPagination);
        List<SysSmsVo> listVO = JsonUtil.getJsonToList(list, SysSmsVo.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(sysSmsPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 获取消息推送记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('payment:sms:query')")
    @GetMapping(value = "/{id}")
 	public ActionResult<SysSmsVo> info(@PathVariable("id") String id) {
        SysSms entity = sysSmsService.getInfo(id);
        SysSmsVo vo = JsonUtil.getJsonToBean(entity, SysSmsVo.class);
        return ActionResult.success(vo);
    }

    /**
     * 新增消息推送记录
     */
    @PreAuthorize("@ss.hasPermi('payment:sms:add')")
    @Log(title = "消息推送记录", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@Validated(AddGroup.class) @RequestBody SysSmsBo bo) throws DataException {
        SysUser userInfo = userProvider.get();
        SysSms entity = JsonUtil.getJsonToBean(bo, SysSms.class);
        sysSmsService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 修改消息推送记录
     */
    @PreAuthorize("@ss.hasPermi('payment:sms:edit')")
    @Log(title = "消息推送记录", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id, @Validated(EditGroup.class) @RequestBody SysSmsBo bo)
        throws DataException {
        SysSms entity = JsonUtil.getJsonToBean(bo, SysSms.class);
        sysSmsService.update(id, entity);
        return ActionResult.success("更新成功");
    }

    /**
     * 删除消息推送记录
     */
    @PreAuthorize("@ss.hasPermi('payment:sms:remove')")
    @Log(title = "消息推送记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
	@Transactional
    public ActionResult delete(@PathVariable("id") String id) {
        SysSms entity = sysSmsService.getInfo(id);
        if (entity != null) {
            sysSmsService.delete(entity);
        }
        return ActionResult.success("删除成功");
    }
}
