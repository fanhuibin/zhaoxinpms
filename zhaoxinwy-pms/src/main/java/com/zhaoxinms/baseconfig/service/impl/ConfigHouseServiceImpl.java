package com.zhaoxinms.baseconfig.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.DynDicUtil;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigBuilding;
import com.zhaoxinms.baseconfig.entity.ConfigHouseBlockEntity;
import com.zhaoxinms.baseconfig.entity.ConfigHouseEntity;
import com.zhaoxinms.baseconfig.mapper.ConfigHouseMapper;
import com.zhaoxinms.baseconfig.model.confighouseblock.ConfigHouseBlockPagination;
import com.zhaoxinms.baseconfig.model.house.HouseContractListVO;
import com.zhaoxinms.baseconfig.model.house.HouseContractPagination;
import com.zhaoxinms.baseconfig.model.house.HouseCrForm;
import com.zhaoxinms.baseconfig.model.house.HousePagination;
import com.zhaoxinms.baseconfig.service.ConfigHouseBlockService;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.baseconfig.service.IConfigBuildingService;
import com.zhaoxinms.event.BlockEvent;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.InputCheckUtil;
import com.zhaoxinms.util.ValidateUtil;

/**
 * 商铺管理
 * 
 * @author fanhuibin
 * @date 2021/11/27
 */
@Service
public class ConfigHouseServiceImpl extends ServiceImpl<ConfigHouseMapper, ConfigHouseEntity> implements ConfigHouseService {

    @Autowired
    private UserProvider userProvider;
    @Autowired
    private DynDicUtil dynDicUtil;
    @Autowired
    private ConfigHouseBlockService configHouseBlockService;
    @Autowired
    private IConfigBuildingService configBuildingService;

    @Override
    public List<ConfigHouseEntity> getList(HousePagination housePagination) {
        String userId = "" + userProvider.get().getUserId();
        QueryWrapper<ConfigHouseEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(housePagination.getBlock())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigHouseEntity::getBlock, housePagination.getBlock()));
        }

        if (InputCheckUtil.isNotEmpty(housePagination.getCode())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigHouseEntity::getCode, housePagination.getCode()));
        }

        if (InputCheckUtil.isNotEmpty(housePagination.getState())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigHouseEntity::getState, housePagination.getState()));
        }
        if (InputCheckUtil.isNotEmpty(housePagination.getName())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigHouseEntity::getName, housePagination.getName()));
        }
        if (InputCheckUtil.isNotEmpty(housePagination.getBuilding())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigHouseEntity::getBuilding, housePagination.getBuilding()));
        }
        queryWrapper.lambda().and(t -> t.eq(ConfigHouseEntity::getEnabledMark, 1));
        queryWrapper.lambda().orderByAsc(ConfigHouseEntity::getId);
        Page<ConfigHouseEntity> page = new Page<>(housePagination.getCurrentPage(), housePagination.getPageSize());
        IPage<ConfigHouseEntity> userIPage = this.page(page, queryWrapper);
        return housePagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public List<ConfigHouseEntity> getTypeList(HousePagination housePagination, String dataType) {
        QueryWrapper<ConfigHouseEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(housePagination.getBlock())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigHouseEntity::getBlock, housePagination.getBlock()));
        }

        if (InputCheckUtil.isNotEmpty(housePagination.getCode())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigHouseEntity::getCode, housePagination.getCode()));
        }

        if (InputCheckUtil.isNotEmpty(housePagination.getState())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigHouseEntity::getState, housePagination.getState()));
        }
        if (InputCheckUtil.isNotEmpty(housePagination.getName())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigHouseEntity::getName, housePagination.getName()));
        }
        if (InputCheckUtil.isNotEmpty(housePagination.getBuilding())) {
            queryWrapper.lambda().and(t -> t.eq(ConfigHouseEntity::getBuilding, housePagination.getBuilding()));
        }
        
        queryWrapper.lambda().and(t -> t.eq(ConfigHouseEntity::getEnabledMark, 1));
        
        if ("0".equals(dataType)) {
            queryWrapper.lambda().orderByAsc(ConfigHouseEntity::getId);
            Page<ConfigHouseEntity> page = new Page<>(housePagination.getCurrentPage(), housePagination.getPageSize());
            IPage<ConfigHouseEntity> userIPage = this.page(page, queryWrapper);
            return housePagination.setData(userIPage.getRecords(), userIPage.getTotal());
        } else {
            queryWrapper.lambda().orderByAsc(ConfigHouseEntity::getId);
            return this.list(queryWrapper);
        }
    }

    @Override
    public List<ConfigHouseEntity> getByNames(String[] ids) {
        QueryWrapper<ConfigHouseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.in(ConfigHouseEntity::getName, ids));
        queryWrapper.lambda().orderByDesc(ConfigHouseEntity::getCreatorTime);
        return this.list(queryWrapper);
    }
    
    @Override
    public List<ConfigHouseEntity> getByResourceNameTips(String resourceName) {
        QueryWrapper<ConfigHouseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(ConfigHouseEntity::getName, resourceName);
        queryWrapper.lambda().eq(ConfigHouseEntity::getEnabledMark, "1");
        queryWrapper.last("limit 10");
        return this.list(queryWrapper);
    }

    @Override
    public ConfigHouseEntity getInfo(String id) {
        QueryWrapper<ConfigHouseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ConfigHouseEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public ConfigHouseEntity getByName(String name) {
        QueryWrapper<ConfigHouseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ConfigHouseEntity::getName, name);
        return this.getOne(queryWrapper);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void create(ConfigHouseEntity entity) throws DataException {
        if (StringUtils.isEmpty(entity.getBlock()) || StringUtils.isEmpty(entity.getCode()) || StringUtils.isEmpty(entity.getBuilding())) {
            throw new DataException("请求的数据不完整");
        }
        
        if (!ValidateUtil.Z_index(entity.getFloor())) {
            throw new DataException("楼层格式不正确");
        }

        if (!ValidateUtil.PositiveFloatOrNum(entity.getBuildingSquare())) {
            throw new DataException("占地面积的格式不正确");
        }

        if (!ValidateUtil.PositiveFloatOrNum(entity.getUseSquare())) {
            throw new DataException("使用面积的格式不正确");
        }

        // 同一个栋楼同一层的房间号不能重复
        QueryWrapper<ConfigHouseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ConfigHouseEntity::getBuilding, entity.getBuilding());
        queryWrapper.lambda().eq(ConfigHouseEntity::getCode, entity.getCode());
        queryWrapper.lambda().eq(ConfigHouseEntity::getFloor, entity.getFloor());
        ConfigHouseEntity result = this.getOne(queryWrapper);
        if (result != null && StringUtils.isNotEmpty(result.getCode())) {
            throw new DataException("已经存在房号是" + entity.getCode() + "的商铺，创建失败");
        }

        String userId = "" + userProvider.get().getUserId();
        entity.setCreatorTime(new Date());
        entity.setCreatorUserId(userId);
        ConfigBuilding building = configBuildingService.getById(entity.getBuilding());
        entity.setName(this.getName(entity.getBlock(), building.getNumber(), entity.getFloor(), entity.getCode()));
        this.save(entity);
    }

    @Override
    public boolean update(String id, ConfigHouseEntity entity) throws DataException {
        // 非空置的商铺不能修改
        if (!entity.getState().equals(ConstantsUtil.HOUSE_STATE_EMPTY)) {
            throw new DataException("非空置中的商铺不允许修改");
        }
        
        if (!ValidateUtil.Z_index(entity.getFloor())) {
            throw new DataException("楼层格式不正确");
        }

        if (!ValidateUtil.Posttive_float(entity.getBuildingSquare())) {
            throw new DataException("占地面积的格式不正确");
        }

        if (!ValidateUtil.Posttive_float(entity.getUseSquare())) {
            throw new DataException("使用面积的格式不正确");
        }

        // 商铺的小区不允许修改
        ConfigHouseEntity oldEntity = this.getById(id);
        if (!oldEntity.getBlock().equals(entity.getBlock())) {
            throw new DataException("商铺的小区不允许修改");
        }
        
        // 商铺的楼栋不允许修改
        if (!oldEntity.getBuilding().equals(entity.getBuilding())) {
            throw new DataException("商铺的楼栋信息不允许修改");
        }

        if (StringUtils.isEmpty(entity.getBlock()) || StringUtils.isEmpty(entity.getCode()) || StringUtils.isEmpty(entity.getBuilding())) {
            throw new DataException("请求的数据不完整");
        }

        // 同一个楼，同一层的商铺号不能重复
        QueryWrapper<ConfigHouseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ConfigHouseEntity::getBuilding, entity.getBuilding());
        queryWrapper.lambda().eq(ConfigHouseEntity::getCode, entity.getCode());
        queryWrapper.lambda().eq(ConfigHouseEntity::getFloor, entity.getFloor());
        ConfigHouseEntity result = this.getOne(queryWrapper);
        if (result != null && !result.getId().equals(entity.getId())) {
            throw new DataException("已经存在房号是" + entity.getCode() + "的商铺，修改失败");
        }

        String userId = "" + userProvider.get().getUserId();
        entity.setLastModifyTime(new Date());
        entity.setLastModifyUserId(userId);
        entity.setId(id);
        ConfigBuilding building = configBuildingService.getById(entity.getBuilding());
        entity.setName(this.getName(entity.getBlock(), building.getNumber(), entity.getFloor(), entity.getCode()));
        return this.updateById(entity);
    }
    
    /**
     * 生成编码
     * @param blockCode
     * @param buildingCode
     * @param floor
     * @param houseCode
     * @return
     */
    private String getName(String blockCode, String buildingCode, String floor, String houseCode) {
        return blockCode+"-"+buildingCode+"-"+floor+"-"+houseCode;
    }

    @Override
    public void delete(ConfigHouseEntity entity) {
        String userId = "" + userProvider.get().getUserId();
        // 非空置的商铺不能修改
        if (!entity.getState().equals(ConstantsUtil.HOUSE_STATE_EMPTY)) {
            throw new DataException("编号'" + entity.getName() + "'的商铺非空置状态，不允许删除");
        }

        if (entity != null) {
            entity.setEnabledMark(0);
            entity.setDeleteTime(new Date());
            entity.setDeleteUserId(userId);
        }
        this.update(entity.getId(), entity);
    }

    @Override
    public int importHouse(List<HouseCrForm> importHouseList) {
        String userId = "" + userProvider.get().getUserId();
        // 查询所有的商业区信息
        List<ConfigHouseBlockEntity> list = configHouseBlockService.getTypeList(new ConfigHouseBlockPagination(), "1");
        List<ConfigBuilding> buildings = configBuildingService.list();
        List<ConfigHouseEntity> houseList = this.getTypeList(new HousePagination(), "1");

        List<ConfigHouseEntity> importList = new ArrayList<ConfigHouseEntity>();
        // 验证数据格式是否合法
        for (int i = 0; i < importHouseList.size(); i++) {
            ConfigHouseEntity entity = JsonUtil.getJsonToBean(importHouseList.get(i), ConfigHouseEntity.class);
            boolean blockSucc = false;
            boolean buildingSucc = false;
            boolean houseSucc = true;
            String buildingCode = "";

            // 如果所有的项目都是空，则忽略当前行
            if (StringUtils.isEmpty(entity.getBlock()) && StringUtils.isEmpty(entity.getCode()) && StringUtils.isEmpty(entity.getFloor())
                && StringUtils.isEmpty(entity.getBuildingSquare()) && StringUtils.isEmpty(entity.getUseSquare())) {
                continue;
            }
            
            Set<ConstraintViolation<Object>> validResult = Validation.buildDefaultValidatorFactory().getValidator().validate(importHouseList.get(i));
            if(validResult.size() > 0) {
                throw new DataException("导入数据失败：第" + (i + 1) + "条数据格式不正确");
            }
            
            if (StringUtils.isEmpty(entity.getBlock()) || StringUtils.isEmpty(entity.getCode()) || StringUtils.isEmpty(entity.getFloor())
                || StringUtils.isEmpty(entity.getBuildingSquare()) || StringUtils.isEmpty(entity.getUseSquare()) || StringUtils.isEmpty(entity.getBuilding())) {
                throw new DataException("导入数据失败：第" + (i + 1) + "条数据不完整.");
            }

            /**
             * 验证商业区的编码是否存在
             */
            for (ConfigHouseBlockEntity block : list) {
                if (block.getCode().equals(entity.getBlock())) {
                    blockSucc = true;
                }
            }
            if (!blockSucc) {
                throw new DataException("导入数据失败：第" + (i + 1) + "条数据商业区编号不存在.");
            }
            
            /**
             * 验证楼栋编号是否正确 
             */
            for (ConfigBuilding building : buildings) {
                if (building.getNumber().equals(entity.getBuilding()) && building.getBlock().equals(entity.getBlock())) {
                    buildingSucc = true;
                    buildingCode = entity.getBuilding();
                    entity.setBuilding(""+building.getId()); //楼栋存储的是id，楼栋编号会有重复的情况
                    break;
                }
            }
            if (!buildingSucc) {
                throw new DataException("导入数据失败：第" + (i + 1) + "条数据楼栋编号不存在.");
            }
            
            /**
             * 验证楼层
             */
            if (!ValidateUtil.Z_index(entity.getFloor())) {
                throw new DataException("导入数据失败：第" + (i + 1) + "条数据楼层格式不正确.");
            }
            
            String name = getName(entity.getBlock(), buildingCode, entity.getFloor(), entity.getCode());
            entity.setName(name);

            /**
             * 验证商铺的编号是否已经存在
             */
            for (ConfigHouseEntity house : houseList) {
                if (house.getCode().equals(entity.getCode()) && house.getBlock().equals(entity.getBlock())) {
                    houseSucc = false;
                }
            }
            if (!houseSucc) {
                throw new DataException("导入数据失败：第" + (i + 1) + "条数据商铺编号已经存在.");
            }

            if (!ValidateUtil.PositiveFloatOrNum(entity.getBuildingSquare())) {
                throw new DataException("导入数据失败：第" + (i + 1) + "条数据占地面积的格式不正确，最多支持两位小数.");
            }

            if (!ValidateUtil.PositiveFloatOrNum(entity.getUseSquare())) {
                throw new DataException("导入数据失败：第" + (i + 1) + "条数据使用面积的格式不正确，最多支持两位小数.");
            }

            if (!StringUtils.isEmpty(entity.getRentFee())) {
                if (!ValidateUtil.PositiveFloatOrNum(entity.getRentFee())) {
                    throw new DataException("导入数据失败：第" + (i + 1) + "条数据租金的格式不正确，最多支持两位小数.");
                }
            }

            entity.setState(ConstantsUtil.HOUSE_STATE_EMPTY);
            entity.setCreatorTime(new Date());
            entity.setCreatorUserId(userId);
            importList.add(entity);
        }
        this.saveBatch(importList);
        return importList.size();
    }

    public List<HouseContractListVO> getHouseContractList(HouseContractPagination page) {
        return this.baseMapper.getHouseContractList(page);
    }

    public long getHouseContractCount(HouseContractPagination page) {
        return this.baseMapper.getHouseContractCount(page);
    }

    @EventListener
    public void deleteBlock(BlockEvent event) {
        if (event.getState().equals(BlockEvent.STATE_DELETE)) {
            // 同步删除
            String code = event.getBlock().getCode();
            QueryWrapper<ConfigHouseEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().and(t -> t.eq(ConfigHouseEntity::getBlock, code));
            List<ConfigHouseEntity> houses = this.list(queryWrapper);
            try {
                for (ConfigHouseEntity house : houses) {
                    this.delete(house);
                }
            }catch(DataException e) {
                throw new DataException("该商业区已经在使用，删除失败");
            }
        }
    }
}