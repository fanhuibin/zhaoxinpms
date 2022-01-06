package com.zhaoxinms.workflow.business.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.workflow.business.mapper.FlowRepairMapper;
import com.zhaoxinms.workflow.business.entity.FlowRepair;
import com.zhaoxinms.workflow.business.service.IFlowRepairService;
import com.zhaoxinms.workflow.business.entity.pagination.FlowRepairPagination;

/**
 * 报事工单Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-06
 */
@Service
public class FlowRepairServiceImpl extends ServiceImpl<FlowRepairMapper, FlowRepair> implements IFlowRepairService
{
    @Autowired
    private FlowRepairMapper flowRepairMapper;
    @Autowired
    private UserProvider userProvider;
    
    @Override
    public List<FlowRepair> getList(FlowRepairPagination pagination) {
    	LambdaQueryWrapper<FlowRepair> lqw = buildQueryWrapper(pagination);
    	lqw.orderByDesc(FlowRepair::getCreateTime);
    	
        Page<FlowRepair> page =
            new Page<>(pagination.getCurrentPage(), pagination.getPageSize());
        IPage<FlowRepair> userIPage = this.page(page, lqw);
        return pagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }
    
    @Override
    public FlowRepair getInfo(String id) {
        QueryWrapper<FlowRepair> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FlowRepair::getId, id);
        return this.getOne(queryWrapper);
    }
    
    @Override
    public void create(FlowRepair entity) {
		validEntityBeforeSave(entity);
        this.save(entity);
    }
    
    @Override
    public boolean update(String id, FlowRepair entity) {
      	entity.setId(id);
      	validEntityBeforeSave(entity);
        return this.updateById(entity);
    }
    
    @Override
    public void delete(FlowRepair entity) {
        if (entity != null) {
        	 this.delete(entity);
        }
    }

	private LambdaQueryWrapper<FlowRepair> buildQueryWrapper(FlowRepairPagination pagination) {
        LambdaQueryWrapper<FlowRepair> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(pagination.getInstanceId()), FlowRepair::getInstanceId, pagination.getInstanceId());
        lqw.eq(StringUtils.isNotBlank(pagination.getNo()), FlowRepair::getNo, pagination.getNo());
        lqw.eq(StringUtils.isNotBlank(pagination.getTitle()), FlowRepair::getTitle, pagination.getTitle());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyPhone()), FlowRepair::getApplyPhone, pagination.getApplyPhone());
        lqw.like(StringUtils.isNotBlank(pagination.getApplyName()), FlowRepair::getApplyName, pagination.getApplyName());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyCategory()), FlowRepair::getApplyCategory, pagination.getApplyCategory());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyContent()), FlowRepair::getApplyContent, pagination.getApplyContent());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyHouse()), FlowRepair::getApplyHouse, pagination.getApplyHouse());
        lqw.eq(pagination.getApplyTime() != null, FlowRepair::getApplyTime, pagination.getApplyTime());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyImg()), FlowRepair::getApplyImg, pagination.getApplyImg());
        lqw.eq(StringUtils.isNotBlank(pagination.getAppContent()), FlowRepair::getAppContent, pagination.getAppContent());
        lqw.eq(pagination.getAppointmentTime() != null, FlowRepair::getAppointmentTime, pagination.getAppointmentTime());
        lqw.eq(pagination.getAppTime() != null, FlowRepair::getAppTime, pagination.getAppTime());
        lqw.eq(StringUtils.isNotBlank(pagination.getRepairUser()), FlowRepair::getRepairUser, pagination.getRepairUser());
        lqw.eq(pagination.getRepairMaterialsFee() != null, FlowRepair::getRepairMaterialsFee, pagination.getRepairMaterialsFee());
        lqw.eq(pagination.getRepairServiceFee() != null, FlowRepair::getRepairServiceFee, pagination.getRepairServiceFee());
        lqw.eq(StringUtils.isNotBlank(pagination.getRepairContent()), FlowRepair::getRepairContent, pagination.getRepairContent());
        lqw.eq(StringUtils.isNotBlank(pagination.getReturnState()), FlowRepair::getReturnState, pagination.getReturnState());
        lqw.eq(StringUtils.isNotBlank(pagination.getReturnResult()), FlowRepair::getReturnResult, pagination.getReturnResult());
        lqw.eq(StringUtils.isNotBlank(pagination.getReturnRemark()), FlowRepair::getReturnRemark, pagination.getReturnRemark());
        lqw.eq(pagination.getEndTime() != null, FlowRepair::getEndTime, pagination.getEndTime());
        lqw.eq(pagination.getPriority() != null, FlowRepair::getPriority, pagination.getPriority());
        lqw.eq(StringUtils.isNotBlank(pagination.getState()), FlowRepair::getState, pagination.getState());
        lqw.eq(StringUtils.isNotBlank(pagination.getClient()), FlowRepair::getClient, pagination.getClient());
        return lqw;
    }


    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(FlowRepair entity){
        //TODO 做一些数据校验,如唯一约束
    }
}
