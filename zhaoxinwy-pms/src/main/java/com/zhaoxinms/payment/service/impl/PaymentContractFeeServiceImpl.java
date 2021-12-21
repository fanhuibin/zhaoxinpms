package com.zhaoxinms.payment.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.payment.entity.PaymentContractFeeEntity;
import com.zhaoxinms.payment.mapper.PaymentContractFeeMapper;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractFeeListVO;
import com.zhaoxinms.payment.service.PaymentContractFeeService;
import com.zhaoxinms.util.DateUtils;

@Service
public class PaymentContractFeeServiceImpl extends ServiceImpl<PaymentContractFeeMapper, PaymentContractFeeEntity>
    implements PaymentContractFeeService {

    @Autowired
    private UserProvider userProvider;
    @Autowired
    private ConfigFeeItemService configFeeItemService;

    @Override
    public PaymentContractFeeEntity getInfo(String id) {
        QueryWrapper<PaymentContractFeeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentContractFeeEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public void create(PaymentContractFeeEntity entity) {
        entity.setNextBillDate(entity.getBeginDate());
        this.save(entity);
    }

    @Override
    public boolean update(String id, PaymentContractFeeEntity entity) {
        entity.setId(id);
        return this.updateById(entity);
    }

    @Override
    public void delete(PaymentContractFeeEntity entity) {
        if (entity != null) {
            this.removeById(entity.getId());
        }
    }

    @Override
    public void deleteByContractId(String contractId) {
        QueryWrapper<PaymentContractFeeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentContractFeeEntity::getContractId, contractId));
        List<PaymentContractFeeEntity> entitys = this.list(queryWrapper);
        for (PaymentContractFeeEntity entity : entitys) {
            this.delete(entity);
        }
    }

    @Override
    public List<PaymentContractFeeEntity> getbyContractId(String contractId) {
        QueryWrapper<PaymentContractFeeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentContractFeeEntity::getContractId, contractId));
        return this.list(queryWrapper);
    }

    @Override
    public List<PaymentContractFeeListVO> getCanGenerateData(String feeItemId, Date beginDate, Date endDate) {

        String aDate = DateUtils.formatDate(beginDate, "yyyy-MM-dd");
        String bDate = DateUtils.formatDate(endDate, "yyyy-MM-dd");
        return this.getCanGenerateData("", feeItemId, aDate, bDate);
    }

    @Override
    public List<PaymentContractFeeListVO> getCanGenerateData(String resourceName, String feeItemId, String beginDate,
        String endDate) {

        List<PaymentContractFeeListVO> searchResult =
            this.baseMapper.getByFeeAndBillDate(resourceName, feeItemId, beginDate, endDate);
        List<PaymentContractFeeListVO> result = new ArrayList<PaymentContractFeeListVO>();
        for (PaymentContractFeeListVO fee : searchResult) {
            if (fee.getNextBillDate().getTime() >= fee.getBeginDate().getTime()) {
                if (fee.getEndDate() == null || fee.getEndDate().getTime() > fee.getNextBillDate().getTime()) {
                    result.add(fee);
                }
            }
        }
        return result;
    }

    @Override
    public List<PaymentContractFeeListVO> getByFeeId(String feeItemId) {
        return this.baseMapper.getByFee(feeItemId);
    }
}