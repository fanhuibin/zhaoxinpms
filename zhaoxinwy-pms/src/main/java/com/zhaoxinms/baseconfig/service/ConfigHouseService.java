package com.zhaoxinms.baseconfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.baseconfig.entity.ConfigHouseEntity;
import com.zhaoxinms.baseconfig.model.house.HouseContractListVO;
import com.zhaoxinms.baseconfig.model.house.HouseContractPagination;
import com.zhaoxinms.baseconfig.model.house.HouseCrForm;
import com.zhaoxinms.baseconfig.model.house.HousePagination;

import java.util.*;
/**
 *
 * house
 * 版本： V3.1.0
 * 日期： 2021-08-17 17:28:01
 */
public interface ConfigHouseService extends IService<ConfigHouseEntity> {

    List<ConfigHouseEntity> getList(HousePagination housePagination);

    List<ConfigHouseEntity> getTypeList(HousePagination housePagination,String dataType);

    ConfigHouseEntity getInfo(String id);

    void delete(ConfigHouseEntity entity);

    void create(ConfigHouseEntity entity) throws DataException;

    boolean update( String id, ConfigHouseEntity entity) throws DataException;

	int importHouse(List<HouseCrForm> houseList);
	
	public List<HouseContractListVO> getHouseContractList(HouseContractPagination page);
		
	public long getHouseContractCount(HouseContractPagination page);

	ConfigHouseEntity getByName(String name);

	List<ConfigHouseEntity> getByNames(String[] ids);

    List<ConfigHouseEntity> getByResourceNameTips(String resourceName);
}
