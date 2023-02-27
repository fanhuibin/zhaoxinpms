package com.zhaoxinms.baseconfig.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
import com.zhaoxinms.baseconfig.entity.ConfigBuilding;
import com.zhaoxinms.baseconfig.entity.ConfigHouseBlockEntity;
import com.zhaoxinms.baseconfig.entity.bo.ConfigBuildingBo;
import com.zhaoxinms.baseconfig.entity.pagination.ConfigBuildingPagination;
import com.zhaoxinms.baseconfig.entity.vo.ConfigBuildingVo;
import com.zhaoxinms.baseconfig.model.confighouseblock.ConfigHouseBlockPagination;
import com.zhaoxinms.baseconfig.service.ConfigHouseBlockService;
import com.zhaoxinms.baseconfig.service.IConfigBuildingService;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.core.domain.TreeSelect;
import com.zhaoxinms.common.core.domain.TreeSelect2;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;
import com.zhaoxinms.common.enums.BusinessType;
/**
 * 楼栋管理Controller
 * 
 * @author fanhuibin
 * @date 2022-06-22
 */
@RestController
@RequestMapping("/pms/building")
public class ConfigBuildingController
{

	@Autowired
    private UserProvider userProvider;

    @Autowired
    private IConfigBuildingService configBuildingService;
    @Autowired
    private ConfigHouseBlockService configHouseBlockService;

    /**
     * 查询楼栋管理列表
     */
    @PreAuthorize("@ss.hasPermi('pms:building:list')")
    @GetMapping("/list")
    public ActionResult list(ConfigBuildingPagination configBuildingPagination) {
        List<ConfigHouseBlockEntity> blockList= configHouseBlockService.getTypeList(new ConfigHouseBlockPagination(),"1");
        Map<String, ConfigHouseBlockEntity> blockMap = blockList.stream().collect(Collectors.toMap(ConfigHouseBlockEntity::getCode, ConfigHouseBlockEntity -> ConfigHouseBlockEntity));
        
        List<ConfigBuilding> list = configBuildingService.getList(configBuildingPagination);
        List<ConfigBuildingVo> listVO = JsonUtil.getJsonToList(list, ConfigBuildingVo.class);
        for(ConfigBuildingVo vo:listVO) {
            if(blockMap.containsKey(vo.getBlock())) {
                vo.setBlockName(blockMap.get(vo.getBlock()).getName());
            }
        }
        
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(configBuildingPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 获取楼栋管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('pms:building:query')")
    @GetMapping(value = "/{id}")
 	public ActionResult<ConfigBuildingVo> info(@PathVariable("id") String id) {
        ConfigBuilding entity = configBuildingService.getInfo(id);
        ConfigBuildingVo vo = JsonUtil.getJsonToBean(entity, ConfigBuildingVo.class);
        return ActionResult.success(vo);
    }

    /**
     * 新增楼栋管理
     */
    @PreAuthorize("@ss.hasPermi('pms:building:add')")
    @Log(title = "楼栋管理", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@Validated(AddGroup.class) @RequestBody ConfigBuildingBo bo) throws DataException {
        SysUser userInfo = userProvider.get();
        ConfigBuilding entity = JsonUtil.getJsonToBean(bo, ConfigBuilding.class);
        configBuildingService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 修改楼栋管理
     */
    @PreAuthorize("@ss.hasPermi('pms:building:edit')")
    @Log(title = "楼栋管理", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id, @Validated(EditGroup.class) @RequestBody ConfigBuildingBo bo)
        throws DataException {
        ConfigBuilding entity = JsonUtil.getJsonToBean(bo, ConfigBuilding.class);
        configBuildingService.update(id, entity);
        return ActionResult.success("更新成功");
    }

    /**
     * 删除楼栋管理
     */
    @PreAuthorize("@ss.hasPermi('pms:building:remove')")
    @Log(title = "楼栋管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
	@Transactional
    public ActionResult delete(@PathVariable("id") String id) {
        ConfigBuilding entity = configBuildingService.getInfo(id);
        if (entity != null) {
            configBuildingService.delete(entity);
        }
        return ActionResult.success("删除成功");
    }
    
    
    /**
     * 选择楼栋管理
     */
    @GetMapping("/select")
    @Transactional
    public ActionResult treeSelect() { 
        List<TreeSelect2> resultList = new ArrayList<TreeSelect2>();
        List<ConfigHouseBlockEntity> blockList= configHouseBlockService.getTypeList(new ConfigHouseBlockPagination(),"1");
        List<ConfigBuilding> buildings = configBuildingService.list();
        
        for (ConfigHouseBlockEntity block : blockList) {
            TreeSelect2 model = new TreeSelect2();
            model.setValue(block.getCode());
            model.setLabel(block.getName());
            model.setFullCode(block.getCode()+"-");
            model.setType("block");
            List<TreeSelect2> children = new ArrayList<TreeSelect2>();
            
            for(ConfigBuilding building : buildings) {
                if(building.getBlock().equals(block.getCode())) {
                    TreeSelect2 child = new TreeSelect2();
                    child.setValue(""+building.getId());
                    child.setLabel(building.getName());
                    child.setType("building");
                    child.setFullCode(building.getBlock()+"-"+building.getNumber()+"-");
                    children.add(child);
                }
            }
            model.setChildren(children);
            if(children.size() == 0) {
                model.setDisabled(true);
            }
            resultList.add(model);
        }
        
        return ActionResult.success(resultList);
    }
}
