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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import com.zhaoxinms.base.util.DynDicUtil;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.RandomUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.baseconfig.entity.ConfigBuilding;
import com.zhaoxinms.baseconfig.entity.ConfigHouseBlockEntity;
import com.zhaoxinms.baseconfig.entity.ConfigHouseEntity;
import com.zhaoxinms.baseconfig.model.confighouseblock.ConfigHouseBlockPagination;
import com.zhaoxinms.baseconfig.model.house.HouseContractPagination;
import com.zhaoxinms.baseconfig.model.house.HouseCrForm;
import com.zhaoxinms.baseconfig.model.house.HouseInfoVO;
import com.zhaoxinms.baseconfig.model.house.HouseListVO;
import com.zhaoxinms.baseconfig.model.house.HousePagination;
import com.zhaoxinms.baseconfig.model.house.HouseStateVO;
import com.zhaoxinms.baseconfig.model.house.HouseUpForm;
import com.zhaoxinms.baseconfig.service.ConfigHouseBlockService;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.baseconfig.service.IConfigBuildingService;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.payment.entity.PaymentDepositEntity;
import com.zhaoxinms.payment.entity.PaymentMethod;
import com.zhaoxinms.payment.entity.pagination.PaymentMethodPagination;
import com.zhaoxinms.payment.service.IPaymentMethodService;
import com.zhaoxinms.payment.service.PaymentContractService;
import com.zhaoxinms.util.ConstantsUtil;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "商铺管理", description = "")
@RequestMapping("/baseconfig/House")
public class ConfigHouseController {
    @Autowired
    private DynDicUtil dynDicUtil;
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private ConfigHouseService configHouseService;
    @Autowired
    private IConfigBuildingService configBuildingService;
    @Autowired
    private ConfigHouseBlockService configHouseBlockService;

    /**
     * 列表
     *
     * @param housePagination
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @GetMapping
    public ActionResult list(HousePagination housePagination) throws IOException {
        List<ConfigHouseEntity> list = configHouseService.getList(housePagination);

        List<HouseListVO> listVO = JsonUtil.getJsonToList(list, HouseListVO.class);
        
        List<ConfigHouseBlockEntity> blockList= configHouseBlockService.getTypeList(new ConfigHouseBlockPagination(),"1");
        List<ConfigBuilding> buildings = configBuildingService.list();
        Map<String, ConfigHouseBlockEntity> blockMap = blockList.stream().collect(Collectors.toMap(ConfigHouseBlockEntity::getCode, ConfigHouseBlockEntity -> ConfigHouseBlockEntity));
        Map<Long, ConfigBuilding> buildingsMap = buildings.stream().collect(Collectors.toMap(ConfigBuilding::getId, ConfigBuilding -> ConfigBuilding));
        
        for(HouseListVO vo:listVO) {
            vo.setBlockName(blockMap.get(vo.getBlock()).getName());
            vo.setBuildingName(buildingsMap.get(Long.valueOf(vo.getBuilding())).getName());
        }
        
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(housePagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }
    
    /**
     * 列表
     *
     * @param housePagination
     * @return
     */
    @GetMapping("/tips/{resourceName}")
    public ActionResult tips(@PathVariable("resourceName")String resourceName) throws IOException {
        List<ConfigHouseEntity> houses = configHouseService.getByResourceNameTips(resourceName);
        List<HouseListVO> listVO = JsonUtil.getJsonToList(houses, HouseListVO.class);
        return ActionResult.success(listVO);
    }

    /**
     * 状态字典
     *
     * @param housePagination
     * @return
     */
    @GetMapping("stateOptions")
    public ActionResult stateOptions(HouseContractPagination houseContractPagination) throws IOException {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("id", ConstantsUtil.HOUSE_STATE_EMPTY);
        map1.put("fullName", "空置");
        list.add(map1);

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("id", ConstantsUtil.HOUSE_STATE_RENTED);
        map2.put("fullName", "已出租");
        list.add(map2);

        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("id", ConstantsUtil.HOUSE_STATE_SELLED);
        map3.put("fullName", "已出售");
        list.add(map3);
        return ActionResult.success(list);
    }

    /**
     * 创建
     *
     * @param houseCrForm
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "新增商铺", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@RequestBody @Valid HouseCrForm houseCrForm) throws DataException {
        SysUser userInfo = userProvider.get();
        ConfigHouseEntity entity = JsonUtil.getJsonToBean(houseCrForm, ConfigHouseEntity.class);
        configHouseService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 信息
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @GetMapping("/{id}")
    public ActionResult<HouseInfoVO> info(@PathVariable("id") String id) {
        ConfigHouseEntity entity = configHouseService.getInfo(id);
        HouseInfoVO vo = JsonUtil.getJsonToBean(entity, HouseInfoVO.class);
        return ActionResult.success(vo);
    }

    /**
     * 信息
     *
     * @param name
     * @return
     */
    @GetMapping("/name/{name}")
    public ActionResult<HouseInfoVO> getByName(@PathVariable("name") String name) {
        ConfigHouseEntity entity = configHouseService.getByName(name);
        HouseInfoVO vo = JsonUtil.getJsonToBean(entity, HouseInfoVO.class);
        return ActionResult.success(vo);
    }

    /**
     * 更新
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "修改商铺", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id, @RequestBody @Valid HouseUpForm houseUpForm)
        throws DataException {
        ConfigHouseEntity entity = configHouseService.getInfo(id);
        if (entity != null) {
            entity = JsonUtil.getJsonToBean(houseUpForm, ConfigHouseEntity.class);
            entity.setId(id);
            configHouseService.update(id, entity);
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
     * @throws DataException
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "删除商铺", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @Transactional
    public ActionResult delete(@PathVariable("id") String id) throws DataException {
        ConfigHouseEntity entity = configHouseService.getInfo(id);
        if (entity != null) {
            configHouseService.delete(entity);
        }else {
            throw new DataException("商铺不存在，删除失败");
        }
        return ActionResult.success("删除成功");
    }
    
    
    /**
     * 租控图
     *
     * @param id
     * @return
     * @throws DataException
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @GetMapping("/rentControl/{buildingId}")
    @Transactional
    public ActionResult rentControl(@PathVariable("buildingId") String buildingId) throws DataException {
        HousePagination  page = new HousePagination();
        page.setBuilding(buildingId);
        List<ConfigHouseEntity> houses= configHouseService.getTypeList(page,"1");
        List<HouseStateVO> stateEntitys = JsonUtil.getJsonToList(houses, HouseStateVO.class);
        
        //按照楼层封装数据
        Map<String, List<HouseStateVO>> resultList = stateEntitys.stream().collect(Collectors.groupingBy(HouseStateVO::getFloor));
        
        //获取楼层list，并且按照大小排序
        List<String> floorList = new ArrayList<String>(resultList.keySet());
        Collections.sort(floorList, new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                return Integer.valueOf(arg0)-Integer.valueOf(arg1);
            }
        });
        
        Map result = new HashMap();
        result.put("key", floorList);
        result.put("list", resultList);
        
        return ActionResult.success(result);
    }
}
