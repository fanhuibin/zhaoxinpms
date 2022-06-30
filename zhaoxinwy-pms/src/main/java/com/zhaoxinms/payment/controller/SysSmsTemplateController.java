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
import com.zhaoxinms.payment.entity.SysSmsTemplate;
import com.zhaoxinms.payment.entity.vo.SysSmsTemplateVo;
import com.zhaoxinms.payment.entity.bo.SysSmsTemplateBo;
import com.zhaoxinms.payment.entity.pagination.SysSmsTemplatePagination;
import com.zhaoxinms.payment.service.ISysSmsTemplateService;
import com.zhaoxinms.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;
/**
 * 短信模板Controller
 * 
 * @author fanhuibin
 * @date 2022-06-28
 */
@RestController
@RequestMapping("/payment/smsTemplate")
public class SysSmsTemplateController
{

	@Autowired
    private UserProvider userProvider;

    @Autowired
    private ISysSmsTemplateService sysSmsTemplateService;

    /**
     * 查询短信模板列表
     */
    @PreAuthorize("@ss.hasPermi('payment:smsTemplate:list')")
    @GetMapping("/list")
    public ActionResult list(SysSmsTemplatePagination sysSmsTemplatePagination) {
        List<SysSmsTemplate> list = sysSmsTemplateService.getList(sysSmsTemplatePagination);
        List<SysSmsTemplateVo> listVO = JsonUtil.getJsonToList(list, SysSmsTemplateVo.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(sysSmsTemplatePagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 获取短信模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('payment:smsTemplate:query')")
    @GetMapping(value = "/{id}")
 	public ActionResult<SysSmsTemplateVo> info(@PathVariable("id") String id) {
        SysSmsTemplate entity = sysSmsTemplateService.getInfo(id);
        SysSmsTemplateVo vo = JsonUtil.getJsonToBean(entity, SysSmsTemplateVo.class);
        return ActionResult.success(vo);
    }

    /**
     * 新增短信模板
     */
    @PreAuthorize("@ss.hasPermi('payment:smsTemplate:add')")
    @Log(title = "短信模板", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@Validated(AddGroup.class) @RequestBody SysSmsTemplateBo bo) throws DataException {
        SysUser userInfo = userProvider.get();
        SysSmsTemplate entity = JsonUtil.getJsonToBean(bo, SysSmsTemplate.class);
        sysSmsTemplateService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 修改短信模板
     */
    @PreAuthorize("@ss.hasPermi('payment:smsTemplate:edit')")
    @Log(title = "短信模板", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id, @Validated(EditGroup.class) @RequestBody SysSmsTemplateBo bo)
        throws DataException {
        SysSmsTemplate entity = JsonUtil.getJsonToBean(bo, SysSmsTemplate.class);
        sysSmsTemplateService.update(id, entity);
        return ActionResult.success("更新成功");
    }

    /**
     * 删除短信模板
     */
    @PreAuthorize("@ss.hasPermi('payment:smsTemplate:remove')")
    @Log(title = "短信模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
	@Transactional
    public ActionResult delete(@PathVariable("id") String id) {
        SysSmsTemplate entity = sysSmsTemplateService.getInfo(id);
        if (entity != null) {
            sysSmsTemplateService.delete(entity);
        }
        return ActionResult.success("删除成功");
    }
}
