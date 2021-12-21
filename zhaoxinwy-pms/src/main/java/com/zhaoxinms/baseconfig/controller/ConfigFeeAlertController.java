package com.zhaoxinms.baseconfig.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

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

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.RandomUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.baseconfig.entity.ConfigFeeAlertEntity;
import com.zhaoxinms.baseconfig.model.configfeealert.ConfigFeeAlertCrForm;
import com.zhaoxinms.baseconfig.model.configfeealert.ConfigFeeAlertInfoVO;
import com.zhaoxinms.baseconfig.model.configfeealert.ConfigFeeAlertListVO;
import com.zhaoxinms.baseconfig.model.configfeealert.ConfigFeeAlertPagination;
import com.zhaoxinms.baseconfig.model.configfeealert.ConfigFeeAlertUpForm;
import com.zhaoxinms.baseconfig.service.ConfigFeeAlertService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "config_fee_alert", description = "")
@RequestMapping("/baseconfig/ConfigFeeAlert")
public class ConfigFeeAlertController {
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private ConfigFeeAlertService configFeeAlertService;

    /**
     * 列表
     *
     * @param configFeeAlertPagination
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @GetMapping
    public ActionResult list(ConfigFeeAlertPagination configFeeAlertPagination) throws IOException {
        List<ConfigFeeAlertEntity> list = configFeeAlertService.getList(configFeeAlertPagination);
        List<ConfigFeeAlertListVO> listVO = JsonUtil.getJsonToList(list, ConfigFeeAlertListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(configFeeAlertPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 创建
     *
     * @param configFeeAlertCrForm
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @PostMapping
    @Transactional
    public ActionResult create(@RequestBody @Valid ConfigFeeAlertCrForm configFeeAlertCrForm) throws DataException {
        ConfigFeeAlertEntity entity = JsonUtil.getJsonToBean(configFeeAlertCrForm, ConfigFeeAlertEntity.class);
        entity.setId(RandomUtil.uuId());
        configFeeAlertService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ActionResult<ConfigFeeAlertInfoVO> info(@PathVariable("id") String id) {
        ConfigFeeAlertEntity entity = configFeeAlertService.getInfo(id);
        ConfigFeeAlertInfoVO vo = JsonUtil.getJsonToBean(entity, ConfigFeeAlertInfoVO.class);
        return ActionResult.success(vo);
    }

    /**
     * 更新
     *
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id,
        @RequestBody @Valid ConfigFeeAlertUpForm configFeeAlertUpForm) throws DataException {
        ConfigFeeAlertEntity entity = configFeeAlertService.getInfo(id);
        if (entity != null) {
            configFeeAlertService.delete(entity);
            entity = JsonUtil.getJsonToBean(configFeeAlertUpForm, ConfigFeeAlertEntity.class);
            entity.setId(id);
            configFeeAlertService.create(entity);
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
    @DeleteMapping("/{id}")
    @Transactional
    public ActionResult delete(@PathVariable("id") String id) {
        ConfigFeeAlertEntity entity = configFeeAlertService.getInfo(id);
        if (entity != null) {
            configFeeAlertService.delete(entity);
        }
        return ActionResult.success("删除成功");
    }

}
