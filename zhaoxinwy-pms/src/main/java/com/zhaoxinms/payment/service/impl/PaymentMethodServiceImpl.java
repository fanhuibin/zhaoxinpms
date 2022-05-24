package com.zhaoxinms.payment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.common.utils.StringUtils;
import com.zhaoxinms.payment.entity.PaymentMethod;
import com.zhaoxinms.payment.entity.pagination.PaymentMethodPagination;
import com.zhaoxinms.payment.mapper.PaymentMethodMapper;
import com.zhaoxinms.payment.service.IPaymentMethodService;

/**
 * 支付方式Service业务层处理
 * 
 * @author fanhuibin
 * @date 2022-04-15
 */
@Service
public class PaymentMethodServiceImpl extends ServiceImpl<PaymentMethodMapper, PaymentMethod> implements IPaymentMethodService 
{
    
    @Override
    public List<PaymentMethod> getList(PaymentMethodPagination pagination) {
    	LambdaQueryWrapper<PaymentMethod> lqw = buildQueryWrapper(pagination);
    	lqw.orderByAsc(PaymentMethod::getSortField);
    	
        Page<PaymentMethod> page =
            new Page<>(pagination.getCurrentPage(), 40);
        IPage<PaymentMethod> userIPage = this.page(page, lqw);
        return pagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }
    
    @Override
    public PaymentMethod getInfo(String id) {
        QueryWrapper<PaymentMethod> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentMethod::getId, id);
        return this.getOne(queryWrapper);
    }
    
    @Override
    public void create(PaymentMethod entity) {
		validEntityBeforeSave(entity);
        this.save(entity);
    }
    
    @Override
    public boolean update(String id, PaymentMethod entity) {
      	entity.setId(id);
      	validEntityBeforeSave(entity);
        return this.updateById(entity);
    }
    
    @Override
    public void delete(PaymentMethod entity) {
        if (entity != null) {
        	 this.removeById(entity.getId());
        }
    }

	private LambdaQueryWrapper<PaymentMethod> buildQueryWrapper(PaymentMethodPagination pagination) {
        LambdaQueryWrapper<PaymentMethod> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(pagination.getCode()), PaymentMethod::getCode, pagination.getCode());
        lqw.like(StringUtils.isNotBlank(pagination.getName()), PaymentMethod::getName, pagination.getName());
        lqw.eq(StringUtils.isNotBlank(pagination.getClient()), PaymentMethod::getClient, pagination.getClient());
        return lqw;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(PaymentMethod entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public PaymentMethod getByCode(String code) {
        QueryWrapper<PaymentMethod> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentMethod::getCode, code);
        return this.getOne(queryWrapper);
    }
}
