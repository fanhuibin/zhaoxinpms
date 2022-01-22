package com.zhaoxinms.baseconfig.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.model.configfeeitem.ConfigFeeItemCrForm;
import com.zhaoxinms.baseconfig.model.configfeeitem.ConfigFeeItemInfoVO;
import com.zhaoxinms.baseconfig.model.configfeeitem.ConfigFeeItemListVO;
import com.zhaoxinms.baseconfig.model.configfeeitem.ConfigFeeItemPagination;
import com.zhaoxinms.baseconfig.model.configfeeitem.ConfigFeeItemUpForm;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.enums.BusinessType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "收费项管理", description = "")
@RequestMapping("/baseconfig/ConfigFeeItem")
public class ConfigFeeItemController {
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private ConfigFeeItemService configFeeItemService;

    /**
     * 列表
     *
     * @param configFeeItemPagination
     * @return
     */
    @GetMapping
    @ApiOperation("获取收费项列表")
    public ActionResult list(ConfigFeeItemPagination configFeeItemPagination) throws IOException {
        List<ConfigFeeItemEntity> list = configFeeItemService.getList(configFeeItemPagination);
        List<ConfigFeeItemListVO> listVO = JsonUtil.getJsonToList(list, ConfigFeeItemListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(configFeeItemPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 创建
     *
     * @param configFeeItemCrForm
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "新增收费项", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@RequestBody @Valid ConfigFeeItemCrForm configFeeItemCrForm) throws DataException {
        SysUser userInfo = userProvider.get();
        ConfigFeeItemEntity entity = JsonUtil.getJsonToBean(configFeeItemCrForm, ConfigFeeItemEntity.class);
        configFeeItemService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ActionResult<ConfigFeeItemInfoVO> info(@PathVariable("id") String id) {
        ConfigFeeItemEntity entity = configFeeItemService.getInfo(id);
        ConfigFeeItemInfoVO vo = JsonUtil.getJsonToBean(entity, ConfigFeeItemInfoVO.class);
        return ActionResult.success(vo);
    }

    /**
     * 更新
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "更新收费项", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id,
        @RequestBody @Valid ConfigFeeItemUpForm configFeeItemUpForm) throws DataException {
        ConfigFeeItemEntity entity = configFeeItemService.getInfo(id);
        if (entity != null) {
            SysUser userInfo = userProvider.get();
            entity = JsonUtil.getJsonToBean(configFeeItemUpForm, ConfigFeeItemEntity.class);
            configFeeItemService.update(id, entity);
            return ActionResult.success("更新成功");
        } else {
            return ActionResult.fail("更新失败，数据不存在");
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "删除收费项", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @Transactional
    public ActionResult delete(@PathVariable("id") String id) {
        ConfigFeeItemEntity entity = configFeeItemService.getInfo(id);
        if (entity != null) {
            configFeeItemService.delete(entity);
        }
        return ActionResult.success("删除成功");
    }

    /**
     * 通过分类查询收费项
     * 
     * @param id
     * @return
     */
    @GetMapping("/select")
    @Transactional
    public ActionResult select(ConfigFeeItemPagination fee) {
        QueryWrapper<ConfigFeeItemEntity> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isEmpty(fee.getType())) {
            throw new DataException("请选择要查询的收费项类型");
        }
        queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getType, fee.getType()));
        if (StringUtils.isNotEmpty(fee.getName())) {
            queryWrapper.lambda().and(t -> t.like(ConfigFeeItemEntity::getName, fee.getName()));
        }
        if (StringUtils.isNotEmpty(fee.getNumType())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getNumType, fee.getNumType()));
        }
        if (StringUtils.isNotEmpty(fee.getGenerateType())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getGenerateType, fee.getGenerateType()));
        }
        queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getEnabledMark, "1"));
        queryWrapper.lambda().orderByDesc(ConfigFeeItemEntity::getCreatorTime);
        List<ConfigFeeItemEntity> fees = configFeeItemService.list(queryWrapper);

        ActionResult jsonData = new ActionResult();
        jsonData.setData(fees);
        jsonData.setCode(200);
        jsonData.setMsg("Success");
        return jsonData;
    }
}
