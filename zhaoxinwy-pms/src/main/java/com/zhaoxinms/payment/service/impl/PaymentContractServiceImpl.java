/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms
 * website：http://pms.zhaoxinms.com  wx： zhaoxinms
 *
 */
package com.zhaoxinms.payment.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.DynDicUtil;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigHouseEntity;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.common.exception.ServiceException;
import com.zhaoxinms.event.ContractEvent;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.payment.entity.PaymentContractFeeEntity;
import com.zhaoxinms.payment.mapper.PaymentContractMapper;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractCrForm;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractFeeForm;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractPagination;
import com.zhaoxinms.payment.service.PaymentBillService;
import com.zhaoxinms.payment.service.PaymentContractFeeService;
import com.zhaoxinms.payment.service.PaymentContractService;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.DateUtils;
import com.zhaoxinms.util.InputCheckUtil;
import com.zhaoxinms.util.ValidateUtil;

@Service
public class PaymentContractServiceImpl extends ServiceImpl<PaymentContractMapper, PaymentContractEntity>
    implements PaymentContractService {

    @Autowired
    private UserProvider userProvider;
    @Autowired
    private ConfigHouseService houseService;
    @Autowired
    private DynDicUtil dynDicUtil;
    @Autowired
    private PaymentContractFeeService paymentContractFeeService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<PaymentContractEntity> getList(PaymentContractPagination paymentContractPagination) {
        String userId = "" + userProvider.get().getUserId();
        QueryWrapper<PaymentContractEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(paymentContractPagination.getUserName())) {
            queryWrapper.lambda()
                .and(t -> t.like(PaymentContractEntity::getUserName, paymentContractPagination.getUserName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentContractPagination.getUserPhone())) {
            queryWrapper.lambda()
                .and(t -> t.like(PaymentContractEntity::getUserPhone, paymentContractPagination.getUserPhone()));
        }

        if (InputCheckUtil.isNotEmpty(paymentContractPagination.getUserTrade())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentContractEntity::getUserTrade, paymentContractPagination.getUserTrade()));
        }

        queryWrapper.lambda().and(t -> t.eq(PaymentContractEntity::getEnabledMark, 1));
        queryWrapper.lambda().orderByDesc(PaymentContractEntity::getCreatorTime);
        Page<PaymentContractEntity> page =
            new Page<>(paymentContractPagination.getCurrentPage(), paymentContractPagination.getPageSize());
        IPage<PaymentContractEntity> userIPage = this.page(page, queryWrapper);
        return paymentContractPagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public List<PaymentContractEntity> getTypeList(PaymentContractPagination paymentContractPagination,
        String dataType) {
        QueryWrapper<PaymentContractEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(paymentContractPagination.getUserName())) {
            queryWrapper.lambda()
                .and(t -> t.like(PaymentContractEntity::getUserName, paymentContractPagination.getUserName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentContractPagination.getUserPhone())) {
            queryWrapper.lambda()
                .and(t -> t.like(PaymentContractEntity::getUserPhone, paymentContractPagination.getUserPhone()));
        }

        if (InputCheckUtil.isNotEmpty(paymentContractPagination.getUserTrade())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentContractEntity::getUserTrade, paymentContractPagination.getUserTrade()));
        }

        queryWrapper.lambda().and(t -> t.eq(PaymentContractEntity::getEnabledMark, 1));
        queryWrapper.lambda().orderByDesc(PaymentContractEntity::getCreatorTime);
        if ("0".equals(dataType)) {
            Page<PaymentContractEntity> page =
                new Page<>(paymentContractPagination.getCurrentPage(), paymentContractPagination.getPageSize());
            IPage<PaymentContractEntity> userIPage = this.page(page, queryWrapper);
            return paymentContractPagination.setData(userIPage.getRecords(), userIPage.getTotal());
        } else {
            return this.list(queryWrapper);
        }
    }

    @Override
    public PaymentContractEntity getInfo(String id) {
        QueryWrapper<PaymentContractEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentContractEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public PaymentContractEntity getByResourceName(String resourceName) {
        QueryWrapper<PaymentContractEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentContractEntity::getResourceName, resourceName);
        queryWrapper.lambda().eq(PaymentContractEntity::getEnabledMark, "1");
        return this.getOne(queryWrapper);
    }
    
    @Override
    public List<PaymentContractEntity> getByResourceNameTips(String resourceName) {
        QueryWrapper<PaymentContractEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(PaymentContractEntity::getResourceName, resourceName);
        queryWrapper.lambda().eq(PaymentContractEntity::getEnabledMark, "1");
        queryWrapper.last("limit 20");
        return this.list(queryWrapper);
    }
    
    @Override
    public List<PaymentContractEntity> getByOwnerId(String ownerId) {
        QueryWrapper<PaymentContractEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentContractEntity::getOwnerId, ownerId);
        queryWrapper.lambda().eq(PaymentContractEntity::getEnabledMark, "1");
        return this.list(queryWrapper);
    }
    
    @Override
    public List<PaymentContractEntity> getDisabledByOwnerId(String ownerId) {
        QueryWrapper<PaymentContractEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentContractEntity::getOwnerId, ownerId);
        queryWrapper.lambda().eq(PaymentContractEntity::getEnabledMark, "0");
        return this.list(queryWrapper);
    }

    @Override
    public synchronized void create(PaymentContractCrForm form) throws DataException {
        PaymentContractEntity entity = JsonUtil.getJsonToBean(form, PaymentContractEntity.class);
        if (entity.getResourceType().equals("house")) {
            ConfigHouseEntity house = houseService.getById(entity.getResourceId());
            if(StringUtils.isEmpty(entity.getCompany())) {
                throw new ServiceException("公司名不能为空");
            }
            
            if(StringUtils.isEmpty(entity.getOwnerId())) {
                throw new ServiceException("业主信息不能为空");
            }
            
            if (!house.getState().equals(ConstantsUtil.HOUSE_STATE_EMPTY)) {
                throw new DataException("当前商铺已经被占用，不能出租或出售");
            }

            if (!StringUtils.isEmpty(form.getRentFee())) {
                if (ValidateUtil.PositiveFloatOrNum(form.getRentFee())) {
                    house.setRentFee(form.getRentFee());
                } else {
                    throw new DataException("租金格式不正确");
                }
            }

            if (entity.getContractType().equals(ConstantsUtil.HOUSE_STATE_RENTED)) {
                if (StringUtils.isEmpty(house.getRentFee())
                    || new BigDecimal(house.getRentFee()).compareTo(BigDecimal.ZERO) <= 0) {
                    throw new DataException("请完善商铺的租金信息");
                }

                house.setState(ConstantsUtil.HOUSE_STATE_RENTED);
                if (form.getPeriod() <= 0) {
                    throw new DataException("出租的时间不能小于等于0个月");
                }

                Date endDate = DateUtils.addMonths(entity.getBeginDate(), form.getPeriod());
                endDate = DateUtils.addSeconds(endDate, -1);
                house.setStateEndTime(endDate);
                entity.setEndDate(endDate);
            } else if (entity.getContractType().equals(ConstantsUtil.HOUSE_STATE_SELLED)) {
                house.setState(ConstantsUtil.HOUSE_STATE_SELLED);
                Date endDate = null;
                entity.setEndDate(endDate);
                house.setStateEndTime(endDate);
            }
            house.setStateCompany(entity.getCompany());
            houseService.updateById(house);

            // 更新合同表的信息
            entity.setResourceName(house.getName());
            this.save(entity);
            List<PaymentContractFeeForm> fees = form.getContractFees();
            List<PaymentContractFeeEntity> feeEntitys = JsonUtil.getJsonToList(fees, PaymentContractFeeEntity.class);
            addOrUpdateFees(entity, feeEntitys);
            
            // 广播新增
            applicationEventPublisher.publishEvent(new ContractEvent(this, entity, ContractEvent.STATE_ADD));
        } else {
            throw new DataException("资源类型不支持");
        }

    }

    private void addOrUpdateFees(PaymentContractEntity entity, List<PaymentContractFeeEntity> contractFees) {
        if (contractFees.size() == 0) {
            throw new DataException("收费项不能为空，请选择收费项");
        } else {
            for (PaymentContractFeeEntity fee : contractFees) {

                if (fee.getBeginDate() == null) {
                    fee.setBeginDate(entity.getBeginDate());
                }

                // 费用生效的时间只能小于合约生效的时间
                if (entity.getEndDate() != null && fee.getEndDate() == null) {
                    fee.setEndDate(entity.getEndDate());
                }

                if (entity.getEndDate() != null && fee.getEndDate() != null
                    && fee.getEndDate().getTime() > entity.getEndDate().getTime()) {
                    throw new DataException("费用的生效时间不能大于商铺的租赁时间");
                }
                if (fee.getEndDate() != null && fee.getEndDate().getTime() < fee.getBeginDate().getTime()) {
                    throw new DataException("费用的截止时间不能小于开始时间");
                }

                if (StringUtils.isEmpty(fee.getId())) {
                    fee.setContractId(entity.getId());
                    fee.setNextBillDate(fee.getBeginDate());
                    paymentContractFeeService.save(fee);
                } else {
                    PaymentContractFeeEntity oldFee = paymentContractFeeService.getById(fee.getId());
                    oldFee.setBeginDate(fee.getBeginDate());
                    oldFee.setEndDate(fee.getEndDate());
                    // 待缴费的日期不能小于开始日期
                    if (fee.getNextBillDate() != null
                        && fee.getNextBillDate().getTime() < fee.getBeginDate().getTime()) {
                        oldFee.setNextBillDate(oldFee.getBeginDate());
                    }
                    paymentContractFeeService.updateById(oldFee);
                }
            }
        }
    }

    @Override
    @Transactional
    public void cancelContract(String houseId) throws DataException {
        String userId = "" + userProvider.get().getUserId();
        ConfigHouseEntity house = houseService.getById(houseId);
        if (house.getState().equals(ConstantsUtil.HOUSE_STATE_EMPTY)) {
            throw new DataException("当前商铺是空置状态，操作失败");
        }
        house.setState(ConstantsUtil.HOUSE_STATE_EMPTY);
        house.setStateEndTime(new Date());
        houseService.updateById(house);

        // 禁用该商铺下的所有的租售合同记录
        QueryWrapper<PaymentContractEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentContractEntity::getResourceId, houseId))
            .and(t -> t.eq(PaymentContractEntity::getEnabledMark, 1));
        List<PaymentContractEntity> result = this.list(queryWrapper);
        for (PaymentContractEntity a : result) {
            a.setEnabledMark(0);
            a.setDeleteTime(new Date());
            a.setDeleteUserId(userId);
            this.updateById(a);

            // 广播删除事件
            applicationEventPublisher.publishEvent(new ContractEvent(this, a, ContractEvent.STATE_CANCEL));
        }
    }

    @Override
    public List<PaymentContractEntity> getListByResourceIds(List<String> ids) {
        QueryWrapper<PaymentContractEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(PaymentContractEntity::getResourceId, ids);
        queryWrapper.lambda().eq(PaymentContractEntity::getEnabledMark, "1");
        return this.list(queryWrapper);
    }

    @Override
    public void update(String id, PaymentContractCrForm form) throws DataException {
        // 查询旧数据
        PaymentContractEntity oldEntity = this.getById(id);

        PaymentContractEntity entity = JsonUtil.getJsonToBean(form, PaymentContractEntity.class);
        entity.setId(id);
        // 判断资源类型，分为商铺和停车场，有需要可以做扩展
        if (entity.getResourceType().equals("house")) {
            ConfigHouseEntity house = houseService.getById(entity.getResourceId());
            if (house.getState().equals(ConstantsUtil.HOUSE_STATE_EMPTY)) {
                throw new DataException("当前商铺是空置的，不能修改信息");
            }

            if (!StringUtils.isEmpty(form.getRentFee())) {
                if (ValidateUtil.PositiveFloatOrNum(form.getRentFee())) {
                    house.setRentFee(form.getRentFee());
                } else {
                    throw new DataException("租金格式不正确");
                }
            }

            if (entity.getContractType().equals("rented")) {
                house.setState(ConstantsUtil.HOUSE_STATE_RENTED);
                if (form.getPeriod() <= 0) {
                    throw new DataException("出租的时间不能小于等于0个月");
                }
                Date endDate = DateUtils.addMonths(entity.getBeginDate(), form.getPeriod());
                endDate = DateUtils.addSeconds(endDate, -1);
                house.setStateEndTime(endDate);
                entity.setEndDate(endDate);
            } else if (entity.getContractType().equals("selled")) {
                house.setState(ConstantsUtil.HOUSE_STATE_SELLED);
                Date endDate = null;
                entity.setEndDate(endDate);
                house.setStateEndTime(endDate);
            }
            houseService.updateById(house);

            // 更新合同表的信息
            entity.setResourceName(house.getName());

            List<PaymentContractFeeForm> fees = form.getContractFees();
            List<PaymentContractFeeEntity> feeEntitys = JsonUtil.getJsonToList(fees, PaymentContractFeeEntity.class);
            addOrUpdateFees(entity, feeEntitys);
        } else {
            throw new DataException("资源类型不支持");
        }

        this.updateById(entity);
        // 广播修改
        applicationEventPublisher.publishEvent(new ContractEvent(this, entity, ContractEvent.STATE_UPDATE));
    }
}