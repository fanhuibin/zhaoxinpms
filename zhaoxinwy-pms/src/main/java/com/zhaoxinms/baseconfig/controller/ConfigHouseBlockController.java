/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms
 * website：http://pms.zhaoxinms.com  wx： zhaoxinms
 *
 */
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
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.baseconfig.entity.ConfigHouseBlockEntity;
import com.zhaoxinms.baseconfig.model.confighouseblock.ConfigHouseBlockCrForm;
import com.zhaoxinms.baseconfig.model.confighouseblock.ConfigHouseBlockInfoVO;
import com.zhaoxinms.baseconfig.model.confighouseblock.ConfigHouseBlockListVO;
import com.zhaoxinms.baseconfig.model.confighouseblock.ConfigHouseBlockPagination;
import com.zhaoxinms.baseconfig.model.confighouseblock.ConfigHouseBlockUpForm;
import com.zhaoxinms.baseconfig.service.ConfigHouseBlockService;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.enums.BusinessType;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "商业区管理" , description = "")
@RequestMapping("/baseconfig/ConfigHouseBlock")
public class ConfigHouseBlockController {
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private ConfigHouseBlockService configHouseBlockService;

    /**
     * 列表
     *
     * @param configHouseBlockPagination
     * @return
     */
    @GetMapping
    public ActionResult list(ConfigHouseBlockPagination configHouseBlockPagination)throws IOException{
        List<ConfigHouseBlockEntity> list= configHouseBlockService.getList(configHouseBlockPagination);
        List<ConfigHouseBlockListVO> listVO=JsonUtil.getJsonToList(list,ConfigHouseBlockListVO.class);
        PageListVO vo=new PageListVO();
        vo.setList(listVO);
        PaginationVO page=JsonUtil.getJsonToBean(configHouseBlockPagination,PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 创建
     *
     * @param configHouseBlockCrForm
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "商业区创建", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@RequestBody @Valid ConfigHouseBlockCrForm configHouseBlockCrForm) throws DataException {
        SysUser userInfo=userProvider.get();
        ConfigHouseBlockEntity entity=JsonUtil.getJsonToBean(configHouseBlockCrForm, ConfigHouseBlockEntity.class);
        configHouseBlockService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
    * 信息
    *
    * @param id
    * @return
    */
    @GetMapping("/{id}")
    public ActionResult<ConfigHouseBlockInfoVO> info(@PathVariable("id") String id){
        ConfigHouseBlockEntity entity= configHouseBlockService.getInfo(id);
        ConfigHouseBlockInfoVO vo=JsonUtil.getJsonToBean(entity, ConfigHouseBlockInfoVO.class);
        return ActionResult.success(vo);
    }

   /**
    * 更新
    *
    * @param id
    * @return
    */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "商业区更新", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id,@RequestBody @Valid ConfigHouseBlockUpForm configHouseBlockUpForm) throws DataException {
        ConfigHouseBlockEntity entity= configHouseBlockService.getInfo(id);
        if(entity!=null){
            SysUser userInfo = userProvider.get();
            entity=JsonUtil.getJsonToBean(configHouseBlockUpForm, ConfigHouseBlockEntity.class);
            entity.setId(id);
            configHouseBlockService.update(id,entity);
            return ActionResult.success("更新成功");
        }else{
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
    @Log(title = "商业区删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @Transactional
    public ActionResult delete(@PathVariable("id") String id){
        ConfigHouseBlockEntity entity= configHouseBlockService.getInfo(id);
        if(entity!=null){
            configHouseBlockService.delete(entity);
        }
        return ActionResult.success("删除成功");
    }
    
    /**
     * 查询所有的商业区
     *
     * @param configHouseBlockPagination
     * @return
     */
    @GetMapping("selectList")
    public ActionResult selectList(ConfigHouseBlockPagination configHouseBlockPagination)throws IOException{
        List<ConfigHouseBlockEntity> list= configHouseBlockService.getTypeList(configHouseBlockPagination,"1");
        List<ConfigHouseBlockListVO> listVO=JsonUtil.getJsonToList(list,ConfigHouseBlockListVO.class);
        return ActionResult.success(listVO);
    }

}
