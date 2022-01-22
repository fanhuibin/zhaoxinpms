package com.zhaoxinms.baseconfig.controller;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.entity.ConfigFeeSettingEntity;
import com.zhaoxinms.baseconfig.model.configfeesetting.ConfigFeeSettingCrForm;
import com.zhaoxinms.baseconfig.model.configfeesetting.ConfigFeeSettingListVO;
import com.zhaoxinms.baseconfig.model.configfeesetting.ConfigFeeSettingPagination;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.baseconfig.service.ConfigFeeSettingService;
import com.zhaoxinms.common.core.domain.entity.SysUser;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "收费项快捷操作" , description = "")
@RequestMapping("/baseconfig/ConfigFeeSetting")
public class ConfigFeeSettingController {
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private ConfigFeeSettingService configFeeSettingService;
    @Autowired
    private ConfigFeeItemService configFeeItemService;

    /**
     * 列表
     *
     * @param configFeeSettingPagination
     * @return
     */
    @GetMapping
    public ActionResult list(ConfigFeeSettingPagination configFeeSettingPagination)throws IOException{
        List<ConfigFeeSettingEntity> list= configFeeSettingService.getList(configFeeSettingPagination);
        List<ConfigFeeSettingListVO> listVO=JsonUtil.getJsonToList(list,ConfigFeeSettingListVO.class);
        //查询所有收费项，完善列表信息
        QueryWrapper<ConfigFeeItemEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().and(t->t.eq(ConfigFeeItemEntity::getType, "house"));
        List<ConfigFeeItemEntity> fees = configFeeItemService.list(queryWrapper);
        
        for(ConfigFeeSettingListVO entity:listVO){
        	for(ConfigFeeItemEntity fee:fees) {
        		if(fee.getId().equals(entity.getFeeItemId())) {
        			entity.setFeeItemName(fee.getName());
        			entity.setPrice(fee.getPrice());
        			entity.setNumType(fee.getNumType());
        		}
        	}
        }
        return ActionResult.success(listVO);
    }

    /**
     * 更新
     *
     * @param configFeeSettingCrForm
     * @return
     */
    @PostMapping
    @Transactional
    public ActionResult update(@RequestBody @Valid ConfigFeeSettingCrForm configFeeSettingCrForm) throws DataException {
        SysUser userInfo=userProvider.get();
        ConfigFeeSettingEntity entity=JsonUtil.getJsonToBean(configFeeSettingCrForm, ConfigFeeSettingEntity.class);
        configFeeSettingService.update(configFeeSettingCrForm.getType(), configFeeSettingCrForm.getFeeList());
        return ActionResult.success("保存成功");
    }
}
