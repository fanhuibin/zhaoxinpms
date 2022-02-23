package com.zhaoxinms.owner.controller;

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
import com.zhaoxinms.owner.entity.OwnerUser;
import com.zhaoxinms.owner.entity.vo.OwnerUserVo;
import com.zhaoxinms.owner.entity.bo.OwnerUserBo;
import com.zhaoxinms.owner.entity.pagination.OwnerUserPagination;
import com.zhaoxinms.owner.service.IOwnerUserService;
import com.zhaoxinms.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;
/**
 * 业主信息Controller
 * 
 * @author cycberform
 * @date 2022-02-23
 */
@RestController
@RequestMapping("/owner/ownerUser")
public class OwnerUserController
{

	@Autowired
    private UserProvider userProvider;

    @Autowired
    private IOwnerUserService ownerUserService;

    /**
     * 查询业主信息列表
     */
    @PreAuthorize("@ss.hasPermi('owner:ownerUser:list')")
    @GetMapping("/list")
    public ActionResult list(OwnerUserPagination ownerUserPagination) {
        List<OwnerUser> list = ownerUserService.getList(ownerUserPagination);
        List<OwnerUserVo> listVO = JsonUtil.getJsonToList(list, OwnerUserVo.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(ownerUserPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 获取业主信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('owner:ownerUser:query')")
    @GetMapping(value = "/{id}")
 	public ActionResult<OwnerUserVo> info(@PathVariable("id") Long id) {
        OwnerUser entity = ownerUserService.getInfo(id);
        OwnerUserVo vo = JsonUtil.getJsonToBean(entity, OwnerUserVo.class);
        return ActionResult.success(vo);
    }

    /**
     * 新增业主信息
     */
    @PreAuthorize("@ss.hasPermi('owner:ownerUser:add')")
    @Log(title = "业主信息", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@Validated(AddGroup.class) @RequestBody OwnerUserBo bo) throws DataException {
        SysUser userInfo = userProvider.get();
        OwnerUser entity = JsonUtil.getJsonToBean(bo, OwnerUser.class);
        ownerUserService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 修改业主信息
     */
    @PreAuthorize("@ss.hasPermi('owner:ownerUser:edit')")
    @Log(title = "业主信息", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") Long id, @Validated(EditGroup.class) @RequestBody OwnerUserBo bo)
        throws DataException {
        OwnerUser entity = JsonUtil.getJsonToBean(bo, OwnerUser.class);
        ownerUserService.update(id, entity);
        return ActionResult.success("更新成功");
    }

    /**
     * 删除业主信息
     */
    @PreAuthorize("@ss.hasPermi('owner:ownerUser:remove')")
    @Log(title = "业主信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
	@Transactional
    public ActionResult delete(@PathVariable("id") Long id) {
        OwnerUser entity = ownerUserService.getInfo(id);
        if (entity != null) {
            ownerUserService.delete(entity);
        }
        return ActionResult.success("删除成功");
    }
}
