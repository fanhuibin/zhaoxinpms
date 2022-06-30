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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.entity.ConfigHouseEntity;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.baseconfig.service.impl.ConfigFeeItemServiceImpl;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.payment.entity.PaymentContractFeeEntity;
import com.zhaoxinms.payment.entity.PaymentMeterEntity;
import com.zhaoxinms.payment.mapper.PaymentBillMapper;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillBatchForm;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillGenerateForm;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractFeeListVO;
import com.zhaoxinms.payment.service.PaymentBillCreateService;
import com.zhaoxinms.payment.service.PaymentContractFeeService;
import com.zhaoxinms.payment.service.PaymentContractService;
import com.zhaoxinms.payment.service.PaymentMeterIndexService;
import com.zhaoxinms.payment.service.PaymentMeterService;
import com.zhaoxinms.payment.service.PaymentPayLogService;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.DateUtils;
import com.zhaoxinms.util.FeeCalculationUtil;
import com.zhaoxinms.util.ValidateUtil;

@Service
public class PaymentBillCreateServiceImpl extends ServiceImpl<PaymentBillMapper, PaymentBillEntity>
    implements PaymentBillCreateService {
    @Autowired
    public ConfigHouseService houseService;
    @Autowired
    private PaymentContractService paymentContractService;
    @Autowired
    private ConfigFeeItemService configFeeItemService;
    @Autowired
    private ConfigHouseService configHouseService;
    @Autowired
    private PaymentContractFeeService paymentContractFeeService;
    @Autowired
    private PaymentMeterService paymentMeterService;
    @Autowired
    private PaymentMeterIndexService paymentMeterIndexService;

    private void inputCheck(PaymentBillEntity entity) {
        // 校验数据
        if (StringUtils.isEmpty(entity.getResourceId()) || StringUtils.isEmpty(entity.getResourceName())) {
            throw new DataException("商铺信息不能为空");
        }
        if (StringUtils.isEmpty(entity.getFeeItemId())) {
            throw new DataException("收费项信息不能为空");
        }
        if (StringUtils.isEmpty(entity.getFeeUser())) {
            throw new DataException("客户信息不能为空");
        }
        if (!ValidateUtil.PositiveFloatOrNum(entity.getMultiple())) {
            throw new DataException("倍率格式不正确");
        }
        if (!ValidateUtil.PositiveFloatOrNum(entity.getLoss())) {
            throw new DataException("损耗格式不正确");
        }
        if (!ValidateUtil.PositiveFloatOrNum(entity.getPrice())) {
            throw new DataException("单价格式不正确");
        }
        if (!ValidateUtil.PositiveFloatOrNum(entity.getNum())) {
            throw new DataException("数量格式不正确");
        }
        if (entity.getEndDate().getTime() < entity.getBeginDate().getTime()) {
            throw new DataException("结束日期不能小于开始日期");
        }
        // 单价和数量要大于0
        if (new BigDecimal(entity.getPrice()).compareTo(BigDecimal.ZERO) <= 0) {
            throw new DataException("单价必须大于0");
        }
        if (new BigDecimal(entity.getNum()).compareTo(BigDecimal.ZERO) <= 0) {
            throw new DataException("数量必须大于0");
        }
    }

    @Override
    public void create(PaymentBillEntity entity) {
        inputCheck(entity);
        entity.setType(ConstantsUtil.PAY_BILL_TYPE_HOUSE);

        PaymentContractEntity contract = paymentContractService.getByResourceName(entity.getResourceName());
        if (contract == null) {
            throw new DataException("合同信息不存在");
        }

        // 查询收费项
        ConfigFeeItemEntity fee = configFeeItemService.getById(entity.getFeeItemId());

        entity.setContractId(contract.getId());
        entity.setFeeItemName(fee.getName());
        // 数据计算
        // 公式1： total = num * price
        String total = FeeCalculationUtil.getTotal(entity.getNum(), entity.getPrice(), fee);
        entity.setTotal(total);
        entity.setReceivable(null);
        entity.setDiscount(null);
        entity.setLateFee(null);
        this.save(entity);
    }

    @Override
    public void batchCreate(@Valid PaymentBillBatchForm paymentBillBatchForm) {
        List<PaymentBillEntity> entitys = this.getBatchCreateData(paymentBillBatchForm);
        this.saveBatch(entitys);
    }

    @Override
    public List<PaymentBillEntity> getBatchCreateData(@Valid PaymentBillBatchForm paymentBillBatchForm) {
        String[] resourceNames = new String[] {};
        if (paymentBillBatchForm.getResourceName().contains("，")) {
            resourceNames = paymentBillBatchForm.getResourceName().split("，");
        } else if (paymentBillBatchForm.getResourceName().contains(",")) {
            resourceNames = paymentBillBatchForm.getResourceName().split(",");
        } else {
            resourceNames = paymentBillBatchForm.getResourceName().split(",");
        }

        if (resourceNames.length == 0) {
            throw new DataException("商铺编号不能为空");
        }

        List<ConfigHouseEntity> entitys = configHouseService.getByNames(resourceNames);
        for (ConfigHouseEntity house : entitys) {
            if (house.getState().equals(ConstantsUtil.HOUSE_STATE_EMPTY)) {
                throw new DataException("商铺“" + house.getName() + "”处于空置状态，不能添加收费数据");
            }
        }

        List<PaymentBillEntity> bills = new ArrayList<PaymentBillEntity>();

        ConfigFeeItemEntity fee = configFeeItemService.getById(paymentBillBatchForm.getFeeItemId());
        if (fee.getNumType().equals(ConfigFeeItemServiceImpl.Number_METER)) {
            throw new DataException("收费项“" + fee.getName() + "”是按走表数字来计算数量，请用抄表数据管理来批量生成数据");
        }

        for (ConfigHouseEntity entity : entitys) {
            PaymentBillEntity bill = new PaymentBillEntity();
            bill.setResourceId(entity.getId());
            bill.setResourceName(entity.getName());
            bill.setType(ConstantsUtil.PAY_BILL_TYPE_HOUSE);
            PaymentContractEntity contract = paymentContractService.getByResourceName(entity.getName());
            bill.setContractId(contract.getId());
            bill.setFeeUser(contract.getUserName());
            bill.setFeeItemId(paymentBillBatchForm.getFeeItemId());
            bill.setFeeItemName(fee.getName());
            bill.setPrice(paymentBillBatchForm.getPrice());
            bill.setBeginDate(new Date(paymentBillBatchForm.getBeginDate()));
            bill.setEndDate(new Date(paymentBillBatchForm.getEndDate()));
            bill.setDeadline(new Date(paymentBillBatchForm.getDeadline()));
            bill.setLastIndex("0");
            bill.setCurrentIndex("0");
            bill.setLoss("0");
            bill.setMultiple("1");
            bill.setNum(FeeCalculationUtil.getHouseNum(entity, fee));
            bill.setTotal(FeeCalculationUtil.getTotal(bill.getNum(), bill.getPrice(), fee));
            bill.setDiscount("0");
            bill.setLateFee("0");
            this.inputCheck(bill);
            bills.add(bill);
        }
        if (bills.size() == 0) {
            throw new DataException("生成数据失败，请检查商铺的编号是否正确");
        }
        return bills;
    }

    @Override
    public List<PaymentBillEntity> getOrCreateGenerateData(@Valid PaymentBillGenerateForm paymentBillGenerateForm,
        boolean create) throws ParseException {
        String feeItemId = paymentBillGenerateForm.getFeeItemId();
        Date beginDate = new Date(paymentBillGenerateForm.getBeginDate());
        Date endDate = new Date(paymentBillGenerateForm.getEndDate());

        ConfigFeeItemEntity fee = configFeeItemService.getById(feeItemId);
        if (fee.getNumType().equals(ConfigFeeItemServiceImpl.Number_METER)) {
            throw new DataException("该方法不能创建抄表类收费数据");
        }

        List<PaymentContractFeeListVO> contractFees =
            paymentContractFeeService.getCanGenerateData(feeItemId, beginDate, endDate);
        if (contractFees.size() == 0) {
            throw new DataException("在指定时间段内，没有待收费的“"+fee.getName()+"”");
        }

        // 生成收费数据
        List<PaymentBillEntity> bills = new ArrayList<PaymentBillEntity>();
        List<PaymentContractFeeListVO> usedFee = new ArrayList<PaymentContractFeeListVO>();
        for (PaymentContractFeeListVO contractFee : contractFees) {
            ConfigHouseEntity house = configHouseService.getById(contractFee.getResourceId());

            PaymentBillEntity bill = new PaymentBillEntity();
            bill.setResourceId(contractFee.getResourceId());
            bill.setResourceName(contractFee.getResourceName());
            bill.setType(ConstantsUtil.PAY_BILL_TYPE_HOUSE);
            bill.setContractId(contractFee.getContractId());
            bill.setFeeUser(contractFee.getUserName());
            bill.setFeeItemId(fee.getId());
            bill.setFeeItemName(fee.getName());
            String price = fee.getPrice();
            if (StringUtils.isNotEmpty(paymentBillGenerateForm.getPrice())) {
                price = paymentBillGenerateForm.getPrice();
            }
            bill.setPrice(price);
            bill.setBeginDate(contractFee.getNextBillDate());
            String billEndDate = DateUtils.getStepEndDate(bill.getBeginDate(), Integer.valueOf(fee.getPeriod()));
            String newNextBillDate = DateUtils.getStepMonth(bill.getBeginDate(), Integer.valueOf(fee.getPeriod()));
            bill.setEndDate(DateUtils.parseDate(billEndDate, "yyyy-MM-dd"));
            contractFee.setNextBillDate(DateUtils.parseDate(newNextBillDate, "yyyy-MM-dd"));
            bill.setDeadline(new Date(paymentBillGenerateForm.getDeadline()));
            bill.setLastIndex("0");
            bill.setCurrentIndex("0");
            bill.setLoss("0");
            bill.setMultiple("1");
            bill.setNum(FeeCalculationUtil.getHouseNum(house, fee));
            bill.setTotal(FeeCalculationUtil.getTotal(bill.getNum(), bill.getPrice(), fee));
            bill.setDiscount("0");
            bill.setLateFee("0");
            this.inputCheck(bill);
            bills.add(bill);
            usedFee.add(contractFee);
        }

        if (create) {
            this.saveBatch(bills);
            for (PaymentContractFeeListVO contractFee : usedFee) {
                LambdaUpdateWrapper<PaymentContractFeeEntity> update =
                    new LambdaUpdateWrapper<PaymentContractFeeEntity>();
                update.eq(PaymentContractFeeEntity::getId, contractFee.getId())
                    .set(PaymentContractFeeEntity::getNextBillDate, contractFee.getNextBillDate());
                Integer rows = paymentContractFeeService.getBaseMapper().update(null, update);
                if (rows != 1) {
                    throw new DataException("更新数据出错");
                }
            }
        }
        return bills;
    }

    @Override
    public List<PaymentBillEntity> getOrCreateMeterData(@Valid PaymentBillGenerateForm paymentBillGenerateForm,
        boolean create) throws ParseException {
        String feeItemId = paymentBillGenerateForm.getFeeItemId();
        Date beginDate = new Date(paymentBillGenerateForm.getBeginDate());
        Date endDate = new Date(paymentBillGenerateForm.getEndDate());

        ConfigFeeItemEntity fee = configFeeItemService.getById(feeItemId);
        if (!fee.getNumType().equals(ConfigFeeItemServiceImpl.Number_METER)) {
            throw new DataException("该方法只能创建抄表类收费数据");
        }

        // 通过收费项查询所有的抄表数据
        List<PaymentMeterEntity> meters = paymentMeterService.getByFeeId(feeItemId);
        if (meters.size() == 0) {
            throw new DataException("没有查找到合适的抄表数据，请检查抄表数据是否已经导入");
        }

        List<PaymentContractFeeListVO> contractFees =
            paymentContractFeeService.getCanGenerateData(feeItemId, beginDate, endDate);
        if (contractFees.size() == 0) {
            throw new DataException("在“" + DateUtils.formatDate(beginDate, "yyyy-MM-dd") + "-"
                + DateUtils.formatDate(endDate, "yyyy-MM-dd") + "”" + "费用期内没有待收费的商铺");
        }

        // 生成收费数据
        List<PaymentBillEntity> bills = new ArrayList<PaymentBillEntity>();
        List<PaymentMeterEntity> usedMeter = new ArrayList<PaymentMeterEntity>();
        List<PaymentContractFeeListVO> usedFee = new ArrayList<PaymentContractFeeListVO>();
        for (PaymentMeterEntity meter : meters) {
            for (PaymentContractFeeListVO contractFee : contractFees) {
                if (meter.getResourceId().equals(contractFee.getResourceId())) {
                    PaymentBillEntity bill = new PaymentBillEntity();
                    bill.setResourceId(meter.getResourceId());
                    bill.setResourceName(meter.getResourceName());
                    bill.setType(ConstantsUtil.PAY_BILL_TYPE_HOUSE);
                    bill.setContractId(contractFee.getContractId());
                    bill.setFeeUser(contractFee.getUserName());
                    bill.setFeeItemId(fee.getId());
                    bill.setFeeItemName(fee.getName());
                    bill.setPrice(fee.getPrice());
                    bill.setBeginDate(contractFee.getNextBillDate());
                    String billEndDate = DateUtils.getStepEndDate(bill.getBeginDate(), Integer.valueOf(fee.getPeriod()));
                    bill.setEndDate(DateUtils.parseDate(billEndDate, "yyyy-MM-dd"));
                    bill.setDeadline(new Date(paymentBillGenerateForm.getDeadline()));
                    bill.setLastIndex(meter.getLastIndex());
                    bill.setCurrentIndex(meter.getCurrentIndex());
                    bill.setLoss(meter.getLoss());
                    bill.setMultiple(meter.getMultiple());
                    bill.setNum(meter.getResult());
                    bill.setTotal(FeeCalculationUtil.getTotal(bill.getNum(), bill.getPrice(), fee));
                    bill.setDiscount("0");
                    bill.setLateFee("0");
                    this.inputCheck(bill);
                    bills.add(bill);
                    usedMeter.add(meter);
                    usedFee.add(contractFee);
                }
            }
        }

        if (create) {
            this.saveBatch(bills);
            for (PaymentMeterEntity meter : usedMeter) {
                paymentMeterService.updateIndex(meter);
            }
            for (PaymentContractFeeListVO contractFee : usedFee) {
                int times = contractFee.getTimes() + 1;
                String newNextBillDate = DateUtils.getStepMonth(contractFee.getBeginDate(), times*Integer.valueOf(fee.getPeriod()));
                LambdaUpdateWrapper<PaymentContractFeeEntity> update =
                    new LambdaUpdateWrapper<PaymentContractFeeEntity>();
                update.eq(PaymentContractFeeEntity::getId, contractFee.getId())
                    .set(PaymentContractFeeEntity::getNextBillDate, newNextBillDate)
                    .set(PaymentContractFeeEntity::getTimes, times);
                Integer rows = paymentContractFeeService.getBaseMapper().update(null, update);
                if (rows != 1) {
                    throw new DataException("更新数据出错");
                }
            }
        }
        return bills;
    }

}