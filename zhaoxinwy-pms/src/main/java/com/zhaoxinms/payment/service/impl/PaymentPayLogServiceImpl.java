package com.zhaoxinms.payment.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.service.BillRuleService;
import com.zhaoxinms.base.util.StringUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.payment.entity.PaymentDepositEntity;
import com.zhaoxinms.payment.entity.PaymentPayLogEntity;
import com.zhaoxinms.payment.entity.PaymentPreEntity;
import com.zhaoxinms.payment.entity.PaymentTempEntity;
import com.zhaoxinms.payment.mapper.PaymentPayLogMapper;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillListVO;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPayForm;
import com.zhaoxinms.payment.model.paymentpaylog.PaymentPayLogPagination;
import com.zhaoxinms.payment.service.PaymentContractService;
import com.zhaoxinms.payment.service.PaymentPayLogService;
import com.zhaoxinms.util.CalculationUtil;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.DateUtils;
import com.zhaoxinms.util.InputCheckUtil;

/**
 *
 * payment_pay_log 
 * 版权： 作者： CYCBERFORM 日期： 2021-09-01 09:31:28 
 *  金额计算公式： 公式1： 应收金额 = 收费项费用合计+滞纳金合计-优惠金额 
 *  公式2：应收金额= 实付金额 + 预付款支付金额 - 找零金额 - 预付款预存金额
 */
@Service

public class PaymentPayLogServiceImpl extends ServiceImpl<PaymentPayLogMapper, PaymentPayLogEntity>
    implements PaymentPayLogService {

    @Autowired
    private UserProvider userProvider;
    @Autowired
    private BillRuleService billRuleService;

    @Override
    public List<PaymentPayLogEntity> getList(PaymentPayLogPagination paymentPayLogPagination) {
        String userId = "" + userProvider.get().getUserId();
        QueryWrapper<PaymentPayLogEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(paymentPayLogPagination.getPayBeginDate())
            && InputCheckUtil.isNotEmpty(paymentPayLogPagination.getPayEndDate())) {
            Date beginDate = new Date(Long.valueOf(paymentPayLogPagination.getPayBeginDate()));
            Date endDate = new Date(Long.valueOf(paymentPayLogPagination.getPayBeginDate()));
            String beginDateString = DateUtils.formatDate(beginDate, "yyyy-MM-dd 00:00:00");
            String endDateString = DateUtils.formatDate(endDate, "yyyy-MM-dd 23:59:59");
            queryWrapper.lambda().and(t -> t.between(PaymentPayLogEntity::getPayTime, beginDateString, endDateString));
        }
        if (InputCheckUtil.isNotEmpty(paymentPayLogPagination.getPayMethod())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentPayLogEntity::getPayMethod, paymentPayLogPagination.getPayMethod()));
        }

        if (InputCheckUtil.isNotEmpty(paymentPayLogPagination.getType())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPayLogEntity::getType, paymentPayLogPagination.getType()));
        }

        if (InputCheckUtil.isNotEmpty(paymentPayLogPagination.getName())) {
            queryWrapper.lambda().and(t -> t.like(PaymentPayLogEntity::getName, paymentPayLogPagination.getName()));
        }

        // 排序
        if (StringUtil.isEmpty(paymentPayLogPagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(PaymentPayLogEntity::getPayTime);
        } else {
            queryWrapper = "asc".equals(paymentPayLogPagination.getSort().toLowerCase())
                ? queryWrapper.orderByAsc(paymentPayLogPagination.getSidx())
                : queryWrapper.orderByDesc(paymentPayLogPagination.getSidx());
        }
        Page<PaymentPayLogEntity> page =
            new Page<>(paymentPayLogPagination.getCurrentPage(), paymentPayLogPagination.getPageSize());
        IPage<PaymentPayLogEntity> userIPage = this.page(page, queryWrapper);
        return paymentPayLogPagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public List<PaymentPayLogEntity> getTypeList(PaymentPayLogPagination paymentPayLogPagination, String dataType) {
        QueryWrapper<PaymentPayLogEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(paymentPayLogPagination.getPayBeginDate())
            && InputCheckUtil.isNotEmpty(paymentPayLogPagination.getPayEndDate())) {
            Date beginDate = new Date(paymentPayLogPagination.getPayBeginDate());
            Date endDate = new Date(paymentPayLogPagination.getPayBeginDate());
            String beginDateString = DateUtils.formatDate(beginDate, "yyyy-MM-dd 00:00:00");
            String endDateString = DateUtils.formatDate(endDate, "yyyy-MM-dd 23:59:59");
            queryWrapper.lambda().and(t -> t.between(PaymentPayLogEntity::getPayTime, beginDateString, endDateString));
        }
        if (InputCheckUtil.isNotEmpty(paymentPayLogPagination.getPayMethod())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentPayLogEntity::getPayMethod, paymentPayLogPagination.getPayMethod()));
        }

        if (InputCheckUtil.isNotEmpty(paymentPayLogPagination.getType())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentPayLogEntity::getType, paymentPayLogPagination.getType()));
        }

        if (InputCheckUtil.isNotEmpty(paymentPayLogPagination.getName())) {
            queryWrapper.lambda().and(t -> t.like(PaymentPayLogEntity::getName, paymentPayLogPagination.getName()));
        }

        // 排序
        if (StringUtil.isEmpty(paymentPayLogPagination.getSidx())) {
            queryWrapper.lambda().orderByDesc(PaymentPayLogEntity::getPayTime);
        } else {
            queryWrapper = "asc".equals(paymentPayLogPagination.getSort().toLowerCase())
                ? queryWrapper.orderByAsc(paymentPayLogPagination.getSidx())
                : queryWrapper.orderByDesc(paymentPayLogPagination.getSidx());
        }
        if ("0".equals(dataType)) {
            Page<PaymentPayLogEntity> page =
                new Page<>(paymentPayLogPagination.getCurrentPage(), paymentPayLogPagination.getPageSize());
            IPage<PaymentPayLogEntity> userIPage = this.page(page, queryWrapper);
            return paymentPayLogPagination.setData(userIPage.getRecords(), userIPage.getTotal());
        } else {
            return this.list(queryWrapper);
        }
    }

    @Override
    public PaymentPayLogEntity getInfo(String id) {
        QueryWrapper<PaymentPayLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentPayLogEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    // 公式1： 应收金额 = 收费项费用合计+滞纳金合计-优惠金额
    private boolean validateRule1(PaymentPayLogEntity entity) {
        String tempFee1 = CalculationUtil.add(entity.getItemTotalMoney(), entity.getLateFeeMoney(), 2);
        String tempFee2 = CalculationUtil.subtract(tempFee1, entity.getDiscountMoney(), 2);
        if (CalculationUtil.compareTo(tempFee2, entity.getReceivableMoney(), 2, BigDecimal.ROUND_HALF_UP) == 0) {
            return true;
        }
        return false;
    }

    // 公式2： 应收金额 = 实付金额 + 预付款支付金额 - 找零金额 - 预付款预存金额
    private boolean validateRule2(PaymentPayLogEntity entity) {
        String totalPay = CalculationUtil.add(entity.getPayMoney(), entity.getPrePayMoney(), 2);
        String totalChange = CalculationUtil.add(entity.getChangeMoney(), entity.getPreSaveMoney(), 2);
        String receivable = CalculationUtil.subtract(totalPay, totalChange, 2);
        if (CalculationUtil.compareTo(receivable, entity.getReceivableMoney(), 2, BigDecimal.ROUND_HALF_UP) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public PaymentPayLogEntity createDepositPayLog(PaymentDepositEntity deposit, String method) {
        PaymentPayLogEntity log = new PaymentPayLogEntity();
        String name = deposit.getFeeItemName();
        log.setCertificate("");
        log.setResourceName(deposit.getResourceName());
        log.setPayTime(new Date());
        log.setName(name);
        log.setPayMethod(method);
        log.setPayerName(deposit.getFeeUser());
        log.setPayerIdcard("");
        log.setPayerPhone("");
        log.setType(ConstantsUtil.PAY_LOG_TYPE_PAY);
        log.setFeeType(ConstantsUtil.PAY_LOG_FEE_TYPE_DEPOSIT);
        log.setPayMethod(method);
        log.setComment("");
        String payNo = billRuleService.getBillNumber("pay_no", false);
        log.setPayNo(payNo);
        log.setReceivableMoney(deposit.getAmt());
        log.setItemTotalMoney(deposit.getAmt());
        log.setLateFeeMoney("0");
        log.setDiscountMoney("0");
        log.setChangeMoney("0");
        log.setPayMoney(deposit.getAmt());
        log.setPrePayMoney("0");
        log.setPreSaveMoney("0");
        if (!validateRule1(log)) {
            throw new DataException("创建收费数据失败，收费数据应该满足： 应收金额 = 收费项费用合计+滞纳金合计-优惠金额");
        }
        if (!validateRule2(log)) {
            throw new DataException("创建收费数据失败，收费数据应该满足： 应收金额 = 实付金额 + 预付款支付金额 - 找零金额 - 预付款预存金额");
        }
        this.save(log);
        return log;
    }

    @Override
    public PaymentPayLogEntity createDepositRefundLog(PaymentDepositEntity deposit, String method) {
        PaymentPayLogEntity log = new PaymentPayLogEntity();
        String name = deposit.getFeeItemName();
        log.setCertificate("");
        log.setResourceName(deposit.getResourceName());
        log.setPayTime(new Date());
        log.setName(name);
        log.setPayMethod(method);
        log.setPayerName(deposit.getFeeUser());
        log.setPayerIdcard("");
        log.setPayerPhone("");
        log.setType(ConstantsUtil.PAY_LOG_TYPE_REFUND);
        log.setFeeType(ConstantsUtil.PAY_LOG_FEE_TYPE_DEPOSIT);
        log.setPayMethod(method);
        log.setComment("");
        String payNo = billRuleService.getBillNumber("pay_no", false);
        log.setPayNo(payNo);
        log.setItemTotalMoney("-" + deposit.getAmt());
        log.setReceivableMoney("-" + deposit.getAmt());
        log.setLateFeeMoney("0");
        log.setDiscountMoney("0");
        log.setChangeMoney("0");
        log.setPayMoney("-" + deposit.getAmt());
        log.setPrePayMoney("0");
        log.setPreSaveMoney("0");
        if (!validateRule1(log)) {
            throw new DataException("创建收费数据失败，收费数据应该满足： 应收金额 = 收费项费用合计+滞纳金合计-优惠金额");
        }
        if (!validateRule2(log)) {
            throw new DataException("创建收费数据失败，收费数据应该满足： 应收金额 = 实付金额 + 预付款支付金额 - 找零金额 - 预付款预存金额");
        }
        this.save(log);
        return log;
    }

    @Override
    public PaymentPayLogEntity payBill(PaymentBillPayForm payForm, PaymentContractEntity contract) {
          
        PaymentPayLogEntity log = new PaymentPayLogEntity();
        String name = "";
        int nameCount = 0;
        for (int i = 0; i < payForm.getPaymentBills().size(); i++) {
            PaymentBillListVO bill = payForm.getPaymentBills().get(i);
            if (nameCount < 2) {
                if (!StringUtils.isEmpty(name)) {
                    name = name + ",";
                }
                name += bill.getFeeItemName();
            }
            nameCount++;
        }
        if (nameCount > 2) {
            name += "等...";
        }
        log.setCertificate("");
        log.setPayTime(new Date());
        log.setResourceName(payForm.getPaymentBills().get(0).getResourceName());
        log.setName(name);
        log.setPayMethod(payForm.getPayMethod());
        log.setPayerName(contract.getUserName());
        log.setPayerIdcard(contract.getUserIdcard());
        log.setPayerPhone(contract.getUserPhone());
        log.setType(ConstantsUtil.PAY_LOG_TYPE_PAY);
        log.setComment("");
        String payNo = billRuleService.getBillNumber("pay_no", false);
        log.setPayNo(payNo);
        log.setLateFeeMoney(payForm.getLateFeeMoney());
        log.setDiscountMoney(payForm.getDiscountMoney());
        log.setItemTotalMoney(payForm.getItemTotalMoney());
        log.setReceivableMoney(payForm.getReceivableMoney());
        log.setPayMoney(payForm.getPayMoney());
        log.setPrePayMoney(payForm.getPrePayMoney());
        log.setPreSaveMoney(payForm.getPreSaveMoney());
        log.setChangeMoney(payForm.getChangeMoney());
        log.setFeeType(ConstantsUtil.PAY_LOG_FEE_TYPE_HOUSE);
        log.setType(ConstantsUtil.PAY_LOG_TYPE_PAY);

        if (!validateRule1(log)) {
            throw new DataException("创建收费数据失败，收费数据应该满足： 应收金额 = 收费项费用合计+滞纳金合计-优惠金额");
        }
        if (!validateRule2(log)) {
            throw new DataException("创建收费数据失败，收费数据应该满足： 应收金额 = 实付金额 + 预付款支付金额 - 找零金额 - 预付款预存金额");
        }
        this.save(log);
        return log;
    }

    @Override
    public PaymentPayLogEntity createTempPayLog(PaymentTempEntity entity, String payType) {
        PaymentPayLogEntity log = new PaymentPayLogEntity();
        String name = entity.getFeeItemName();
        log.setCertificate("");
        log.setResourceName(entity.getResourceName());
        log.setPayTime(new Date());
        log.setName(name);
        log.setPayMethod(payType);
        log.setPayerName(entity.getFeeUser());
        log.setPayerIdcard("");
        log.setPayerPhone("");
        log.setType(ConstantsUtil.PAY_LOG_TYPE_PAY);
        log.setFeeType(ConstantsUtil.PAY_LOG_FEE_TYPE_DEPOSIT);
        log.setPayMethod(payType);
        log.setComment("");
        String payNo = billRuleService.getBillNumber("pay_no", false);
        log.setPayNo(payNo);
        log.setReceivableMoney(entity.getAmt());
        log.setItemTotalMoney(entity.getAmt());
        log.setLateFeeMoney("0");
        log.setDiscountMoney("0");
        log.setChangeMoney("0");
        log.setPayMoney(entity.getAmt());
        log.setPrePayMoney("0");
        log.setPreSaveMoney("0");
        if (!validateRule1(log)) {
            throw new DataException("创建收费数据失败，收费数据应该满足： 应收金额 = 收费项费用合计+滞纳金合计-优惠金额");
        }
        if (!validateRule2(log)) {
            throw new DataException("创建收费数据失败，收费数据应该满足： 应收金额 = 实付金额 + 预付款支付金额 - 找零金额 - 预付款预存金额");
        }
        this.save(log);
        return log;
    }

    @Override
    public PaymentPayLogEntity createTempRefundLog(PaymentTempEntity deposit, String method) {
        PaymentPayLogEntity log = new PaymentPayLogEntity();
        String name = deposit.getFeeItemName();
        log.setCertificate("");
        log.setResourceName(deposit.getResourceName());
        log.setPayTime(new Date());
        log.setName(name);
        log.setPayMethod(method);
        log.setPayerName(deposit.getFeeUser());
        log.setPayerIdcard("");
        log.setPayerPhone("");
        log.setType(ConstantsUtil.PAY_LOG_TYPE_REFUND);
        log.setFeeType(ConstantsUtil.PAY_LOG_FEE_TYPE_TEMP);
        log.setComment("");
        String payNo = billRuleService.getBillNumber("pay_no", false);
        log.setPayNo(payNo);
        log.setItemTotalMoney("-" + deposit.getAmt());
        log.setReceivableMoney("-" + deposit.getAmt());
        log.setLateFeeMoney("0");
        log.setDiscountMoney("0");
        log.setChangeMoney("0");
        log.setPayMoney("-" + deposit.getAmt());
        log.setPrePayMoney("0");
        log.setPreSaveMoney("0");
        if (!validateRule1(log)) {
            throw new DataException("创建收费数据失败，收费数据应该满足： 应收金额 = 收费项费用合计+滞纳金合计-优惠金额");
        }
        if (!validateRule2(log)) {
            throw new DataException("创建收费数据失败，收费数据应该满足： 应收金额 = 实付金额 + 预付款支付金额 - 找零金额 - 预付款预存金额");
        }
        this.save(log);
        return log;
    }

    @Override
    public PaymentPayLogEntity refundBill(PaymentBillEntity entity, String payMethod, String amcount, String comment) {
        PaymentPayLogEntity log = new PaymentPayLogEntity();
        String name = entity.getFeeItemName();
        log.setCertificate("");
        log.setResourceName(entity.getResourceName());
        log.setPayTime(new Date());
        log.setName(name);
        log.setPayMethod(payMethod);
        log.setPayerName(entity.getFeeUser());
        log.setPayerIdcard("");
        log.setPayerPhone("");
        log.setType(ConstantsUtil.PAY_LOG_TYPE_REFUND);
        log.setFeeType(ConstantsUtil.PAY_LOG_FEE_TYPE_HOUSE);
        log.setComment("");
        String payNo = billRuleService.getBillNumber("pay_no", false);
        log.setPayNo(payNo);
        log.setItemTotalMoney("-" + amcount);
        log.setReceivableMoney("-" + amcount);
        log.setLateFeeMoney("0");
        log.setDiscountMoney("0");
        log.setChangeMoney("0");
        log.setPayMoney("-" + amcount);
        log.setPrePayMoney("0");
        log.setPreSaveMoney("0");
        log.setBusinessId(entity.getId());
        if (!validateRule1(log)) {
            throw new DataException("创建收费数据失败，收费数据应该满足： 应收金额 = 收费项费用合计+滞纳金合计-优惠金额");
        }
        if (!validateRule2(log)) {
            throw new DataException("创建收费数据失败，收费数据应该满足： 应收金额 = 实付金额 + 预付款支付金额 - 找零金额 - 预付款预存金额");
        }
        this.save(log);
        return log;
    }

    @Override
    public PaymentPayLogEntity getByPayNo(String payNo) {
        QueryWrapper<PaymentPayLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentPayLogEntity::getPayNo, payNo);
        return this.getOne(queryWrapper);
    }
}