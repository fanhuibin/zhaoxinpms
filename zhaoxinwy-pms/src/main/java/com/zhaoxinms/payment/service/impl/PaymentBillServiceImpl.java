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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.service.BillRuleService;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.event.ContractEvent;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.payment.entity.PaymentPayLogEntity;
import com.zhaoxinms.payment.entity.PaymentPreAccountEntity;
import com.zhaoxinms.payment.entity.PaymentPreEntity;
import com.zhaoxinms.payment.mapper.PaymentBillMapper;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillListVO;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPagination;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPayForm;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillRefundForm;
import com.zhaoxinms.payment.model.paymentpreaccount.PaymentPreAccountPayForm;
import com.zhaoxinms.payment.service.PaymentBillService;
import com.zhaoxinms.payment.service.PaymentContractService;
import com.zhaoxinms.payment.service.PaymentPayLogService;
import com.zhaoxinms.payment.service.PaymentPreAccountService;
import com.zhaoxinms.payment.service.PaymentPreService;
import com.zhaoxinms.util.CalculationUtil;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.DateUtils;
import com.zhaoxinms.util.FeeCalculationUtil;
import com.zhaoxinms.util.InputCheckUtil;
import com.zhaoxinms.util.ValidateUtil;


@Service
public class PaymentBillServiceImpl extends ServiceImpl<PaymentBillMapper, PaymentBillEntity>
    implements PaymentBillService {
    @Autowired
    public ConfigHouseService houseService;
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private PaymentPayLogService paymentPayLogService;
    @Autowired
    private ConfigFeeItemService configFeeItemService;
    @Autowired
    private PaymentPreAccountService paymentPreAccountService;
    @Autowired
    private PaymentPreService paymentPreService;
    @Autowired
    private PaymentContractService paymentContractService;
    @Autowired
    private BillRuleService billRuleService;

    @Override
    public List<PaymentBillEntity> getList(PaymentBillPagination paymentBillPagination) {
        String userId = "" + userProvider.get().getUserId();
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getPayBeginTime())
            && InputCheckUtil.isNotEmpty(paymentBillPagination.getPayEndTime())) {
            Date beginDate = new Date(Long.valueOf(paymentBillPagination.getPayBeginTime()));
            Date endDate = new Date(Long.valueOf(paymentBillPagination.getPayBeginTime()));
            String beginDateString = DateUtils.formatDate(beginDate, "yyyy-MM-dd 00:00:00");
            String endDateString = DateUtils.formatDate(endDate, "yyyy-MM-dd 23:59:59");
            queryWrapper.lambda().and(t -> t.between(PaymentBillEntity::getPayTime, beginDateString, endDateString));
        }
        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getBeginTime())
            && InputCheckUtil.isNotEmpty(paymentBillPagination.getEndTime())) {
            Date beginDate = new Date(Long.valueOf(paymentBillPagination.getBeginTime()));
            Date endDate = new Date(Long.valueOf(paymentBillPagination.getEndTime()));
            String beginDateString = DateUtils.formatDate(beginDate, "yyyy-MM-dd 00:00:00");
            String endDateString = DateUtils.formatDate(endDate, "yyyy-MM-dd 23:59:59");
            queryWrapper.lambda().and(t -> t.between(PaymentBillEntity::getBeginDate, beginDateString, endDateString));
        }
        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getResourceName())) {
            queryWrapper.lambda()
                .and(t -> t.like(PaymentBillEntity::getResourceName, paymentBillPagination.getResourceName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getResourceId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentBillEntity::getResourceId, paymentBillPagination.getResourceId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getFeeItemId())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getFeeItemId, paymentBillPagination.getFeeItemId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getFeeItemName())) {
            queryWrapper.lambda()
                .and(t -> t.like(PaymentBillEntity::getFeeItemName, paymentBillPagination.getFeeItemName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getPayLogNo())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayLogNo, paymentBillPagination.getPayLogNo()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getPayState())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayState, paymentBillPagination.getPayState()));
        }

        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
        queryWrapper.lambda().orderByDesc(PaymentBillEntity::getCreatorTime);

        Page<PaymentBillEntity> page =
            new Page<>(paymentBillPagination.getCurrentPage(), paymentBillPagination.getPageSize());
        IPage<PaymentBillEntity> userIPage = this.page(page, queryWrapper);
        return paymentBillPagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public List<PaymentBillEntity> getTypeList(PaymentBillPagination paymentBillPagination, String dataType) {
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getPayBeginTime())
            && InputCheckUtil.isNotEmpty(paymentBillPagination.getPayEndTime())) {
            Date beginDate = new Date(Long.valueOf(paymentBillPagination.getPayBeginTime()));
            Date endDate = new Date(Long.valueOf(paymentBillPagination.getPayBeginTime()));
            String beginDateString = DateUtils.formatDate(beginDate, "yyyy-MM-dd 00:00:00");
            String endDateString = DateUtils.formatDate(endDate, "yyyy-MM-dd 23:59:59");
            queryWrapper.lambda().and(t -> t.between(PaymentBillEntity::getPayTime, beginDateString, endDateString));
        }
        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getBeginTime())
            && InputCheckUtil.isNotEmpty(paymentBillPagination.getEndTime())) {
            Date beginDate = new Date(Long.valueOf(paymentBillPagination.getBeginTime()));
            Date endDate = new Date(Long.valueOf(paymentBillPagination.getEndTime()));
            String beginDateString = DateUtils.formatDate(beginDate, "yyyy-MM-dd 00:00:00");
            String endDateString = DateUtils.formatDate(endDate, "yyyy-MM-dd 23:59:59");
            queryWrapper.lambda().and(t -> t.between(PaymentBillEntity::getBeginDate, beginDateString, endDateString));
        }
        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getResourceName())) {
            queryWrapper.lambda()
                .and(t -> t.like(PaymentBillEntity::getResourceName, paymentBillPagination.getResourceName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getResourceId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentBillEntity::getResourceId, paymentBillPagination.getResourceId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getFeeItemId())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getFeeItemId, paymentBillPagination.getFeeItemId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getFeeItemName())) {
            queryWrapper.lambda()
                .and(t -> t.like(PaymentBillEntity::getFeeItemName, paymentBillPagination.getFeeItemName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getPayLogNo())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayLogNo, paymentBillPagination.getPayLogNo()));
        }

        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
        queryWrapper.lambda().orderByDesc(PaymentBillEntity::getCreatorTime);
        if ("0".equals(dataType)) {
            Page<PaymentBillEntity> page =
                new Page<>(paymentBillPagination.getCurrentPage(), paymentBillPagination.getPageSize());
            IPage<PaymentBillEntity> userIPage = this.page(page, queryWrapper);
            return paymentBillPagination.setData(userIPage.getRecords(), userIPage.getTotal());
        } else {
            return this.list(queryWrapper);
        }
    }

    @Override
    public PaymentBillEntity getInfo(String id) {
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentBillEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    /**
     * 缴费单的更新只能更新周期和费用
     */
    @Override
    public boolean update(String id, PaymentBillEntity entity) {
        PaymentBillEntity oldEntity = this.getById(id);
        if (!StringUtils.isEmpty(oldEntity.getPayLogId())) {
            throw new DataException("该缴费单已经付费了，修改失败");
        }
        oldEntity.setReceivable(entity.getReceivable());
        oldEntity.setBeginDate(entity.getBeginDate());
        oldEntity.setEndDate(entity.getEndDate());
        return this.updateById(oldEntity);
    }

    @Override
    public void delete(PaymentBillEntity entity) {
        if (!StringUtils.isEmpty(entity.getPayLogId())) {
            throw new DataException("该缴费单已经付费了，删除失败");
        }
        if (entity != null) {
            String userId = "" + userProvider.get().getUserId();
            entity.setDeleteTime(new Date());
            entity.setDeleteUserId(userId);
            entity.setEnabledMark(0);
            this.updateById(entity);
        }
    }

    @Override
    public void disableByResourceId(String resourceId) {
        String userId = "" + userProvider.get().getUserId();
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.like(PaymentBillEntity::getEnabledMark, 1));
        List<PaymentBillEntity> list = this.list(queryWrapper);

        for (PaymentBillEntity entity : list) {
            entity.setDeleteTime(new Date());
            entity.setDeleteUserId(userId);
        }
        this.updateBatchById(list);
    }

    @Override
    public List<PaymentBillEntity> getUnpaiedListByResource(String resourceName) {
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getResourceName, resourceName));
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayState, "0"));
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
        return this.list(queryWrapper);
    }
    
    @Override
    public List<PaymentBillEntity> getUnpaiedListByResources(List<String> resourceNames) {
        if(resourceNames.size() > 0) {
            QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().and(t -> t.in(PaymentBillEntity::getResourceName, resourceNames));
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayState, "0"));
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
            return this.list(queryWrapper);
        }else {
            return new ArrayList<PaymentBillEntity>();
        }
    }
    
    @Override
    public List<PaymentBillEntity> getPaiedListByContracts(List<String> contracts) {
        if(contracts.size() > 0) {
            QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().and(t -> t.in(PaymentBillEntity::getContractId, contracts));
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayState, "1"));
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
            return this.list(queryWrapper);
        }else {
            return new ArrayList<PaymentBillEntity>();
        }
    }

    @Override
    public List<PaymentBillEntity> getUnpaiedListByFeeItemId(String paymentFeeItemId) {
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getFeeItemId, paymentFeeItemId));
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayState, "0"));
        queryWrapper.lambda().and(t -> t.like(PaymentBillEntity::getEnabledMark, 1));
        return this.list(queryWrapper);
    }

    @Override
    public void updateFee(PaymentBillEntity entity) throws IllegalAccessException {
        if (StringUtils.isEmpty(entity.getId())) {
            throw new DataException("请求的数据不合法");
        }
        PaymentBillEntity oldEntity = this.getById(entity.getId());
        if (entity.getTotal() != entity.getTotal()) {
            throw new DataException("数据异常，请求失败");
        }

        ConfigFeeItemEntity fee = configFeeItemService.getById(entity.getFeeItemId());
        entity.setLateFee(FeeCalculationUtil.getLateFee(entity.getTotal(), entity.getDeadline(), fee));
        if (StringUtils.isEmpty(entity.getDiscount()) || entity.getDiscount().equals("0")) {
            entity.setDiscount("0");
        }
        entity.setReceivable(
            FeeCalculationUtil.getReceivable(entity.getTotal(), entity.getLateFee(), entity.getDiscount()));
        this.save(entity);
    }

    @Override
    public void payCalc(PaymentBillPayForm payForm) throws IllegalAccessException {
        if (payForm.getPaymentBills().size() == 0) {
            throw new DataException("请求数据不合法");
        }
        String receivableMoney = "0";
        String itemTotalMoney = "0";
        String discountMoney = "0";
        String lateFee = "0";
        String payMoney = payForm.getPayMoney();
        boolean usePrePay = payForm.isUsePrePay();
        boolean usePreSave = payForm.isUsePreSave();
        String prePayMoney = "0";
        String changeMoney = "0";
        String preSaveMoney = "0";
        String canUsePrePayMoney = "0";
        String resourceId = payForm.getPaymentBills().get(0).getResourceId();

        if (StringUtils.isEmpty(payMoney)) {
            payMoney = "0";
        }

        // 总的预付款
        List<PaymentPreAccountEntity> preAccounts = paymentPreAccountService.getAccountsByResourceId(resourceId);

        for (PaymentBillListVO vo : payForm.getPaymentBills()) {
            if (!vo.getResourceId().equals(resourceId)) {
                throw new DataException("请求数据不合法");
            }

            PaymentBillEntity entity = this.getById(vo.getId());
            vo.setFeeItemId(entity.getFeeItemId());
            ConfigFeeItemEntity fee = configFeeItemService.getById(entity.getFeeItemId());
            vo.setLateFee(FeeCalculationUtil.getLateFee(entity.getTotal(), entity.getDeadline(), fee));
            if (StringUtils.isEmpty(vo.getDiscount()) || vo.getDiscount().equals("0")
                || vo.getDiscount().equals("0.00")) {
                vo.setDiscount("0.00");
            }
            vo.setDiscount(CalculationUtil.add(vo.getDiscount(), "0", 2));
            vo.setReceivable(FeeCalculationUtil.getReceivable(vo.getTotal(), vo.getLateFee(), vo.getDiscount()));
            itemTotalMoney = CalculationUtil.add(itemTotalMoney, vo.getTotal());
            receivableMoney = CalculationUtil.add(receivableMoney, vo.getReceivable(), 2);
            lateFee = CalculationUtil.add(lateFee, vo.getLateFee(), 2);
            discountMoney = CalculationUtil.add(discountMoney, vo.getDiscount(), 2);
        }

        List<PaymentPreAccountPayForm> accountForms =
            FeeCalculationUtil.getAccountFeePay(preAccounts, payForm.getPaymentBills());
        for (PaymentPreAccountPayForm account : accountForms) {
            canUsePrePayMoney = CalculationUtil.add(canUsePrePayMoney, account.getPayMoney(), 2);
        }

        // 计算预付款付费金额
        if (usePrePay) {
            prePayMoney = canUsePrePayMoney;
        } else {
            prePayMoney = "0";
        }

        changeMoney = CalculationUtil.subtract(payMoney, receivableMoney, 2);
        changeMoney = CalculationUtil.add(changeMoney, prePayMoney, 2);

        // 计算找零或者结存金额
        if (usePreSave) {
            preSaveMoney = changeMoney;
            changeMoney = "0";
        } else {
            preSaveMoney = "0";
        }

        payForm.setItemTotalMoney(itemTotalMoney);
        payForm.setReceivableMoney(receivableMoney);
        payForm.setDiscountMoney(discountMoney);
        payForm.setLateFeeMoney(lateFee);
        payForm.setPayMoney(CalculationUtil.add(payMoney, "0", 2));
        payForm.setPrePayMoney(prePayMoney);
        payForm.setChangeMoney(changeMoney);
        payForm.setPreSaveMoney(preSaveMoney);
        payForm.setCanUsePrePayMoney(canUsePrePayMoney);
        payForm.setAccountForms(accountForms);
    }

    @Override
    public void payCheck(PaymentBillPayForm payForm) {
        if (StringUtils.isEmpty(payForm.getPayMethod())) {
            throw new DataException("支付方式不能为空");
        }
        String resourceId = payForm.getPaymentBills().get(0).getResourceId();
        /**
         * 验证数据： 1.单个收费项的收费不能小于0 2.付款金额不能小于应收款 3.预存款支付不能小于0 4.找零、找零结转不能小于0
         */
        for (PaymentBillListVO vo : payForm.getPaymentBills()) {
            if (!ValidateUtil.PositiveFloatOrNum(vo.getReceivable())) {
                throw new DataException(vo.getFeeItemName() + "的应收金额不能小于0");
            }
            if (!vo.getResourceId().equals(resourceId)) {
                throw new DataException("所选的收费项不属于同一用户，缴费失败");
            }

            /**
             * 所有的收费单都是未缴费状态
             */
            PaymentBillEntity bill = this.getById(vo.getId());
            if (!bill.getPayState().equals("0")) {
                throw new DataException(vo.getFeeItemName() + "已经完成缴费，不能重复缴费");
            }
        }

        if (!ValidateUtil.PositiveFloatOrNum(payForm.getPayMoney())) {
            throw new DataException("支付金额不能小于0");
        }

        if (!ValidateUtil.PositiveFloatOrNum(payForm.getPrePayMoney())) {
            throw new DataException("预存款支付不能小于0");
        }

        if (!ValidateUtil.PositiveFloatOrNum(payForm.getChangeMoney())) {
            throw new DataException("找零金额不能小于0");
        }

        if (!ValidateUtil.PositiveFloatOrNum(payForm.getPreSaveMoney())) {
            throw new DataException("找零结转金额不能小于0");
        }

    }

    @Override
    public PaymentPayLogEntity paySave(PaymentBillPayForm payForm) {
        String resourceId = payForm.getPaymentBills().get(0).getResourceId();
        PaymentContractEntity contract =
            paymentContractService.getByResourceName(payForm.getPaymentBills().get(0).getResourceName());
        // 添加缴费记录表
        PaymentPayLogEntity payLog = paymentPayLogService.payBill(payForm, contract);

        // 更新每个收费项的优惠和滞纳金
        for (PaymentBillListVO vo : payForm.getPaymentBills()) {
            PaymentBillEntity entity = this.getById(vo.getId());
            entity.setDiscount(vo.getDiscount());
            entity.setReceivable(vo.getReceivable());
            entity.setLateFee(vo.getLateFee());
            entity.setPayLogId(payLog.getId());
            entity.setPayLogNo(payLog.getPayNo());
            entity.setPayState("1");
            entity.setPayTime(new Date());
            this.updateById(entity);
        }
        // 完成预存款扣费
        List<PaymentPreAccountPayForm> accountForms = payForm.getAccountForms();
        for (PaymentPreAccountPayForm account : accountForms) {
            String feeItemId = account.getFeeItemId();
            String chargeAmount = account.getPayMoney();

            PaymentPreEntity preEntity = new PaymentPreEntity();
            preEntity.setResourceId(resourceId);
            preEntity.setOperateTime(new Date());
            preEntity.setAmt("-" + account.getPayMoney());
            preEntity.setBlock(contract.getBlockCode());
            preEntity.setFeeItemId(feeItemId);
            preEntity.setResourceId(resourceId);
            preEntity.setFeeUser(contract.getUserName());
            preEntity.setPayType("");
            preEntity.setResourceName(contract.getResourceName());
            String payNo = billRuleService.getBillNumber("pay_no", false);
            preEntity.setPayNo(payNo);
            paymentPreService.minus(preEntity, ConstantsUtil.PAY_PRE_TYPE_PAY);
        }

        // 完成预存款结转
        if (CalculationUtil.compareTo(payForm.getPreSaveMoney(), "0", 2, 0) > 0) {
            PaymentPreEntity preEntity = new PaymentPreEntity();
            preEntity.setResourceId(resourceId);
            preEntity.setOperateTime(new Date());
            preEntity.setAmt(payForm.getPreSaveMoney());
            preEntity.setBlock(contract.getBlockCode());
            preEntity.setFeeItemId("");
            preEntity.setResourceId(resourceId);
            preEntity.setFeeUser(contract.getUserName());
            preEntity.setPayType(payForm.getPayMethod());
            preEntity.setUseFeeItem("0");
            preEntity.setResourceName(contract.getResourceName());
            String payNo = billRuleService.getBillNumber("pay_no", false);
            preEntity.setPayNo(payNo);
            paymentPreService.create(preEntity, ConstantsUtil.PAY_PRE_TYPE_PAYADD);
        }

        return payLog;
    }
    
    // 商铺下的所有缴费单也禁用掉
    @EventListener
    public void deleteAccount(ContractEvent event) {
        if (event.getState().equals(event.STATE_CANCEL)) {
            PaymentContractEntity contract = event.getContract();
            String resourceId = contract.getResourceId();
            this.disableByResourceId(resourceId);
        }
    }

    @Override
    @Transactional
    public void refundBill(PaymentBillRefundForm refundForm) {
        PaymentBillEntity bill = this.getById(refundForm.getBillId());
        
        if(bill == null || StringUtils.isEmpty(bill.getId())) {
            throw new DataException("查询缴费单失败");
        }
        
        if(!ValidateUtil.PositiveFloatOrNum(refundForm.getCurrentRefundAmount())) {
            throw new DataException("退款金额格式不正确");
        }
        if(CalculationUtil.compareTo(refundForm.getCurrentRefundAmount(), "0", 2, 0) == 0) {
            throw new DataException("退款金额不能为0");
        }
        
        String total = CalculationUtil.add(bill.getRefundAmount(), refundForm.getCurrentRefundAmount());
        
        PaymentPayLogEntity payLog = paymentPayLogService.refundBill(bill, refundForm.getPayMethod(), refundForm.getCurrentRefundAmount(), refundForm.getRefundComment());
        bill.setRefundTimes(bill.getRefundTimes() + 1);
        bill.setRefundAmount(total);
        
        if(CalculationUtil.compareTo(bill.getReceivable(), total, 2, 0) > 0) {
            bill.setRefundState(ConstantsUtil.PAY_BILL_REFUND_STATE_PARTIAL);
        }else if(CalculationUtil.compareTo(bill.getReceivable(), total, 2, 0) == 0) {
            bill.setRefundState(ConstantsUtil.PAY_BILL_REFUND_STATE_ALL);
        }else {
            throw new DataException("退款金额不能大于剩余可退款金额");
        }
        this.updateById(bill);
    }
}