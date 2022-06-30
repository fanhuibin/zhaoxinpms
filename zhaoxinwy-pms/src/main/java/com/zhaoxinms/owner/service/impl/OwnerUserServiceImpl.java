package com.zhaoxinms.owner.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.common.exception.ServiceException;
import com.zhaoxinms.common.utils.StringUtils;
import com.zhaoxinms.event.ContractEvent;
import com.zhaoxinms.event.OwnerUserEvent;
import com.zhaoxinms.owner.entity.OwnerUser;
import com.zhaoxinms.owner.entity.pagination.OwnerUserPagination;
import com.zhaoxinms.owner.mapper.OwnerUserMapper;
import com.zhaoxinms.owner.service.IOwnerUserService;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.payment.service.PaymentContractService;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.ValidateUtil;

/**
 * 业主信息Service业务层处理
 * 
 * @author cycberform
 * @date 2022-02-23
 */
@Service
public class OwnerUserServiceImpl extends ServiceImpl<OwnerUserMapper, OwnerUser> implements IOwnerUserService {
    @Autowired
    private PaymentContractService paymentContractService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<OwnerUser> getList(OwnerUserPagination pagination) {
        LambdaQueryWrapper<OwnerUser> lqw = buildQueryWrapper(pagination);
        lqw.orderByDesc(OwnerUser::getCreateTime);

        Page<OwnerUser> page = new Page<>(pagination.getCurrentPage(), pagination.getPageSize());
        IPage<OwnerUser> userIPage = this.page(page, lqw);
        return pagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public OwnerUser getByPhoneNo(String phoneNo) {
        OwnerUserPagination pagination = new OwnerUserPagination();
        pagination.setPhonenumber(phoneNo);
        LambdaQueryWrapper<OwnerUser> lqw = buildQueryWrapper(pagination);
        if (this.list(lqw).size() > 0) {
            return this.list(lqw).get(0);
        } else {
            return null;
        }
    }

    @Override
    public OwnerUser getByIdcard(String idcard) {
        OwnerUserPagination pagination = new OwnerUserPagination();
        pagination.setIdcard(idcard);
        LambdaQueryWrapper<OwnerUser> lqw = buildQueryWrapper(pagination);
        if (this.list(lqw).size() > 0) {
            return this.list(lqw).get(0);
        } else {
            return null;
        }
    }

    @Override
    public OwnerUser getInfo(Long id) {
        QueryWrapper<OwnerUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OwnerUser::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public void create(OwnerUser entity) {
        validEntityBeforeSave(entity);
        entity.setOwnCount(0l);
        entity.setRentedCount(0l);
        this.save(entity);
    }

    @Override
    public boolean update(Long id, OwnerUser entity) {
        entity.setId(id);
        
        OwnerUser oldEntity = this.getById(id);
        //修改手机号发起手机号变更的时间
        if(!oldEntity.getPhonenumber().equals(entity.getPhonenumber())) {
            applicationEventPublisher.publishEvent(new OwnerUserEvent(this, oldEntity, OwnerUserEvent.STATE_CHANGE_PHONE));
            entity.setIsBind(ConstantsUtil.NO);
        }
        
        validEntityBeforeSave(entity);
        return this.updateById(entity);
    }

    @Override
    public void delete(OwnerUser entity) {
        if (entity != null) {
            throw new DataException("业主信息暂不支持删除操作");
        }
    }

    private LambdaQueryWrapper<OwnerUser> buildQueryWrapper(OwnerUserPagination pagination) {
        LambdaQueryWrapper<OwnerUser> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(pagination.getCompany()), OwnerUser::getCompany, pagination.getCompany());
        lqw.eq(StringUtils.isNotBlank(pagination.getUserName()), OwnerUser::getUserName, pagination.getUserName());
        lqw.eq(StringUtils.isNotBlank(pagination.getIdcard()), OwnerUser::getIdcard, pagination.getIdcard());
        lqw.eq(StringUtils.isNotBlank(pagination.getPhonenumber()), OwnerUser::getPhonenumber, pagination.getPhonenumber());
        lqw.eq(pagination.getOwnCount() != null, OwnerUser::getOwnCount, pagination.getOwnCount());
        return lqw;
    }

    /**
     * 保存前的数据校验
     * 
     * @param entity
     *            实体类数据
     */
    private void validEntityBeforeSave(OwnerUser entity) {

        Long id = entity.getId();

        if (!ValidateUtil.IDcard(entity.getIdcard())) {
            throw new ServiceException("身份证号格式不正确");
        }
        if (!ValidateUtil.Mobile(entity.getPhonenumber())) {
            throw new ServiceException("电话号格式不正确");
        }

        // 同一个身份证号的只能注册一次
        OwnerUser searchByIdcard = this.getByIdcard(entity.getIdcard());
        OwnerUser searchByPhone = this.getByPhoneNo(entity.getPhonenumber());
        if (searchByIdcard != null && !searchByIdcard.getId().equals(id)) {
            throw new ServiceException("登记业主信息失败，身份证号已被登记");
        }

        // 同一个电话号只能注册一次
        if (searchByPhone != null && !searchByPhone.getId().equals(id)) {
            throw new ServiceException("登记业主信息失败，手机号已被登记");
        }
    }

    // 监听contractEvent
    @EventListener
    public void contractChange(ContractEvent event) {
        // 查询
        PaymentContractEntity contract = event.getContract();
        String ownerId = contract.getOwnerId();

        OwnerUser ownerUser = this.getById(ownerId);

        // 通过ownerId查询所有名下的商铺
        long owned = 0;
        long rented = 0;
        List<PaymentContractEntity> list = paymentContractService.getByOwnerId(ownerId);
        for (PaymentContractEntity c : list) {
            if (c.getContractType().equals(ConstantsUtil.HOUSE_STATE_RENTED)) {
                rented++;
            } else if (c.getContractType().equals(ConstantsUtil.HOUSE_STATE_SELLED)) {
                owned++;
            }
        }
        ownerUser.setOwnCount(owned);
        ownerUser.setRentedCount(rented);
        this.update(Long.valueOf(ownerId), ownerUser);
    }
}
