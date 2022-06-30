package com.zhaoxinms.baseconfig.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigBuilding;
import com.zhaoxinms.baseconfig.entity.ConfigHouseEntity;
import com.zhaoxinms.baseconfig.entity.pagination.ConfigBuildingPagination;
import com.zhaoxinms.baseconfig.mapper.ConfigBuildingMapper;
import com.zhaoxinms.baseconfig.mapper.ConfigHouseMapper;
import com.zhaoxinms.baseconfig.service.IConfigBuildingService;
import com.zhaoxinms.common.utils.StringUtils;

/**
 * 楼栋管理Service业务层处理
 * 
 * @author fanhuibin
 * @date 2022-06-22
 */
@Service
public class ConfigBuildingServiceImpl extends ServiceImpl<ConfigBuildingMapper, ConfigBuilding> implements IConfigBuildingService 
{
    @Autowired
    private ConfigBuildingMapper configBuildingMapper;
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private ConfigHouseMapper configHouseMapper;
    
    @Override
    public List<ConfigBuilding> getList(ConfigBuildingPagination pagination) {
    	LambdaQueryWrapper<ConfigBuilding> lqw = buildQueryWrapper(pagination);
    	lqw.orderByDesc(ConfigBuilding::getCreateTime);
    	
        Page<ConfigBuilding> page =
            new Page<>(pagination.getCurrentPage(), pagination.getPageSize());
        IPage<ConfigBuilding> userIPage = this.page(page, lqw);
        return pagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }
    
    @Override
    public ConfigBuilding getInfo(String id) {
        QueryWrapper<ConfigBuilding> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ConfigBuilding::getId, id);
        return this.getOne(queryWrapper);
    }
    
    @Override
    public void create(ConfigBuilding entity) {
		validEntityBeforeSave(entity);
        this.save(entity);
    }
    
    @Override
    public boolean update(String id, ConfigBuilding entity) {
      	entity.setId(Long.valueOf(id));
      	validEntityBeforeSave(entity);
        //楼栋只能修改名称
        ConfigBuilding oldEntity = this.getById(entity.getId());
        oldEntity.setName(entity.getName());
        return this.updateById(oldEntity);
    }
    
    @Override
    public void delete(ConfigBuilding entity) {
        //判断楼栋下是否有商铺，有则不允许删除
        LambdaQueryWrapper<ConfigHouseEntity> lqw = Wrappers.lambdaQuery();
        lqw.eq(ConfigHouseEntity::getBlock, entity.getBlock());
        lqw.eq(ConfigHouseEntity::getBuilding, entity.getNumber());
        long count = configHouseMapper.selectCount(lqw);
        if(count>0) {
            throw new DataException("该楼栋下有商铺，不允许删除");
        }
        this.delete(entity);
    }

	private LambdaQueryWrapper<ConfigBuilding> buildQueryWrapper(ConfigBuildingPagination pagination) {
        LambdaQueryWrapper<ConfigBuilding> lqw = Wrappers.lambdaQuery();
        lqw.eq(pagination.getBlock() != null, ConfigBuilding::getBlock, pagination.getBlock());
        lqw.like(StringUtils.isNotBlank(pagination.getName()), ConfigBuilding::getName, pagination.getName());
        lqw.eq(StringUtils.isNotBlank(pagination.getNumber()), ConfigBuilding::getNumber, pagination.getNumber());
        return lqw;
    }


    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(ConfigBuilding entity){
        
        //同一个商业区下楼栋编号要唯一
        ConfigBuildingPagination pagination = new ConfigBuildingPagination();
        pagination.setBlock(entity.getBlock());
        pagination.setNumber(entity.getNumber());
        ConfigBuilding searchBuilding = this.getOne(this.buildQueryWrapper(pagination));
        if(searchBuilding != null) {
            if(entity.getId() != null && entity.getId() != 0l) {
                if(!searchBuilding.getId().equals(entity.getId())) {
                    throw new DataException("该楼栋编号已经存在");
                }
            }else {
                throw new DataException("该楼栋编号已经存在");
            }
        }
    }
}
