/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms website：http://pms.zhaoxinms.com wx： zhaoxinms
 *
 */
package com.zhaoxinms.payment.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.service.BillRuleService;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.payment.entity.PaymentOrder;
import com.zhaoxinms.payment.entity.PaymentPayLogEntity;
import com.zhaoxinms.payment.entity.PaymentPreAccountEntity;
import com.zhaoxinms.payment.entity.PaymentPreEntity;
import com.zhaoxinms.payment.entity.pagination.PaymentOrderPagination;
import com.zhaoxinms.payment.mapper.PaymentOrderMapper;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillListVO;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPayForm;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillRefundForm;
import com.zhaoxinms.payment.model.paymentpreaccount.PaymentPreAccountPayForm;
import com.zhaoxinms.payment.service.PaymentBillService;
import com.zhaoxinms.payment.service.PaymentContractService;
import com.zhaoxinms.payment.service.PaymentOrderService;
import com.zhaoxinms.payment.service.PaymentPayLogService;
import com.zhaoxinms.payment.service.PaymentPreAccountService;
import com.zhaoxinms.payment.service.PaymentPreService;
import com.zhaoxinms.util.CalculationUtil;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.FeeCalculationUtil;
import com.zhaoxinms.util.ValidateUtil;

@Service
public class PaymentOrderServiceImpl extends ServiceImpl<PaymentOrderMapper, PaymentOrder> implements PaymentOrderService {
    @Autowired
    public ConfigHouseService houseService;
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
    @Autowired
    private PaymentBillService paymentBillService;

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

            PaymentBillEntity entity = (PaymentBillEntity)paymentBillService.getById(vo.getId());
            vo.setFeeItemId(entity.getFeeItemId());
            ConfigFeeItemEntity fee = configFeeItemService.getById(entity.getFeeItemId());
            vo.setLateFee(FeeCalculationUtil.getLateFee(entity.getTotal(), entity.getDeadline(), fee));
            if (StringUtils.isEmpty(vo.getDiscount()) || vo.getDiscount().equals("0") || vo.getDiscount().equals("0.00")) {
                vo.setDiscount("0.00");
            }
            vo.setDiscount(CalculationUtil.add(vo.getDiscount(), "0", 2));
            vo.setReceivable(FeeCalculationUtil.getReceivable(vo.getTotal(), vo.getLateFee(), vo.getDiscount()));
            itemTotalMoney = CalculationUtil.add(itemTotalMoney, vo.getTotal());
            receivableMoney = CalculationUtil.add(receivableMoney, vo.getReceivable(), 2);
            lateFee = CalculationUtil.add(lateFee, vo.getLateFee(), 2);
            discountMoney = CalculationUtil.add(discountMoney, vo.getDiscount(), 2);
        }

        List<PaymentPreAccountPayForm> accountForms = FeeCalculationUtil.getAccountFeePay(preAccounts, payForm.getPaymentBills());
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

    /**
     * 
     * @param payForm
     * @param billCanHasOrder  bill是否允许绑定order
     */
    @Override
    public void payCheck(PaymentBillPayForm payForm, boolean billCanHasOrder) {
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
             * 所有的收费单都是未缴费状态才能添加订单
             */
            PaymentBillEntity bill = paymentBillService.getById(vo.getId());
            if (bill.getPayState().equals("" + ConstantsUtil.PAY_BILL_PAY_STATE_PAIED)) {
                throw new DataException(vo.getFeeItemName() + "已经完成缴费，不能重复缴费");
            }
            if (bill.getPayState().equals("" + ConstantsUtil.PAY_BILL_PAY_STATE_PAYING)) {
                throw new DataException(vo.getFeeItemName() + "正在缴费中，不能重复缴费");
            }
            
            
            if(billCanHasOrder == false) {
                /**
                 * bill已经绑定order的不能缴费
                 */
                if(!StringUtils.isBlank(bill.getOrderId())) {
                    throw new DataException(vo.getFeeItemName() + "正在缴费中，请在业主端撤销之后再缴费");
                }
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
        PaymentContractEntity contract = paymentContractService.getByResourceName(payForm.getPaymentBills().get(0).getResourceName());

        // 添加缴费记录表
        PaymentPayLogEntity payLog = paymentPayLogService.payBill(payForm, contract);

        // 添加付款订单
        PaymentOrder order = new PaymentOrder();
        order.setAmount(new BigDecimal(payForm.getReceivableMoney()));
        order.setSuccessTime(new Date());
        order.setState(PaymentOrder.STATE_SUCCESS);
        order.setWayCode(payLog.getPayMethod());

        String subject = "商铺" + payForm.getPaymentBills().get(0).getResourceName() + "的缴费订单";
        String body = payForm.getPaymentBills().get(0).getFeeItemName();
        if (payForm.getPaymentBills().size() > 1) {
            body += "等" + payForm.getPaymentBills().size() + "个收费项";
        }
        order.setSubject(subject);
        order.setBody(body);
        order.setExpiredTime(new Date()); // 订单过期时间 默认两个小时
        order.setErrCode(PaymentOrder.ERROR_CODE_OK);
        order.setClient(ConstantsUtil.CLIENT_WX_PC);
        order.setLogId(payLog.getId());
        order.setUserId(contract.getOwnerId());
        this.save(order);

        // 更新每个收费项的优惠和滞纳金
        for (PaymentBillListVO vo : payForm.getPaymentBills()) {
            PaymentBillEntity entity = paymentBillService.getById(vo.getId());
            entity.setDiscount(vo.getDiscount());
            entity.setReceivable(vo.getReceivable());
            entity.setLateFee(vo.getLateFee());
            entity.setPayLogId(payLog.getId());
            entity.setPayLogNo(payLog.getPayNo());
            entity.setPayState(String.valueOf(ConstantsUtil.PAY_BILL_PAY_STATE_PAIED));
            entity.setPayTime(new Date());
            entity.setOrderId(order.getId());
            paymentBillService.updateById(entity);
        }

        // 完成预存款扣费
        List<PaymentPreAccountPayForm> accountForms = payForm.getAccountForms();
        for (PaymentPreAccountPayForm account : accountForms) {
            String feeItemId = account.getFeeItemId();
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
    
    @Override
    @Transactional
    public void refundBill(PaymentBillRefundForm refundForm) {
        PaymentBillEntity bill = paymentBillService.getById(refundForm.getBillId());

        if(!bill.getPayState().equals(""+ConstantsUtil.PAY_BILL_PAY_STATE_PAIED)) {
            throw new DataException("未付款的缴费单不能退款");
        }
        if (bill == null || StringUtils.isEmpty(bill.getId())) {
            throw new DataException("查询缴费单失败");
        }

        if (!ValidateUtil.PositiveFloatOrNum(refundForm.getCurrentRefundAmount())) {
            throw new DataException("退款金额格式不正确");
        }
        if (CalculationUtil.compareTo(refundForm.getCurrentRefundAmount(), "0", 2, 0) == 0) {
            throw new DataException("退款金额不能为0");
        }

        String total = CalculationUtil.add(bill.getRefundAmount(), refundForm.getCurrentRefundAmount());

        PaymentPayLogEntity payLog =
            paymentPayLogService.refundBill(bill, refundForm.getPayMethod(), refundForm.getCurrentRefundAmount(), refundForm.getRefundComment());
        bill.setRefundTimes(bill.getRefundTimes() + 1);
        bill.setRefundAmount(total);

        if (CalculationUtil.compareTo(bill.getReceivable(), total, 2, 0) > 0) {
            bill.setRefundState(ConstantsUtil.PAY_BILL_REFUND_STATE_PARTIAL);
        } else if (CalculationUtil.compareTo(bill.getReceivable(), total, 2, 0) == 0) {
            bill.setRefundState(ConstantsUtil.PAY_BILL_REFUND_STATE_ALL);
        } else {
            throw new DataException("退款金额不能大于剩余可退款金额");
        }
        paymentBillService.updateById(bill);
        
        //更新order的refund信息
        PaymentOrder order = this.getById(bill.getOrderId());
        List<String> list = new ArrayList<String>();
        list.add(order.getId());
        List<PaymentBillEntity> bills = paymentBillService.getListByOrders(list);
        String refundTotal = "0";
        for(PaymentBillEntity entity : bills) {
            refundTotal = CalculationUtil.add(refundTotal, entity.getRefundAmount());
        }
        
        if (CalculationUtil.compareTo(order.getAmount().toString(), refundTotal, 2, 0) > 0) {
            order.setRefundState(ConstantsUtil.PAY_BILL_REFUND_STATE_PARTIAL);
        } else if (CalculationUtil.compareTo(bill.getReceivable(), total, 2, 0) == 0) {
            order.setRefundState(ConstantsUtil.PAY_BILL_REFUND_STATE_ALL);
        } else {
            throw new DataException("退款金额不能大于付款金额");
        }
        order.setRefundAmount(new BigDecimal(refundTotal));
        this.updateById(order);
    }
    
    @Override
    public List<PaymentOrder> getList(PaymentOrderPagination pagination) {
        LambdaQueryWrapper<PaymentOrder> lqw = buildQueryWrapper(pagination);
        
        //只查询init 和 success状态的订单
        lqw.in(PaymentOrder::getState, new Integer[] {PaymentOrder.STATE_INIT, PaymentOrder.STATE_SUCCESS});
        lqw.orderByDesc(PaymentOrder::getCreateTime);

        Page<PaymentOrder> page = new Page<>(pagination.getCurrentPage(), pagination.getPageSize());
        IPage<PaymentOrder> userIPage = this.page(page, lqw);
        return pagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public PaymentOrder getInfo(String id) {
        QueryWrapper<PaymentOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentOrder::getId, id);
        return this.getOne(queryWrapper);
    }
    
    private LambdaQueryWrapper<PaymentOrder> buildQueryWrapper(PaymentOrderPagination pagination) {
        LambdaQueryWrapper<PaymentOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(pagination.getState() != null, PaymentOrder::getState, pagination.getState());
        lqw.eq(StringUtils.isNotBlank(pagination.getOpenId()), PaymentOrder::getOpenId, pagination.getOpenId());
        lqw.eq(pagination.getRefundState() != null, PaymentOrder::getRefundState, pagination.getRefundState());
        lqw.eq(StringUtils.isNotBlank(pagination.getErrCode()), PaymentOrder::getErrCode, pagination.getErrCode());
        lqw.eq(pagination.getExpiredTime() != null, PaymentOrder::getExpiredTime, pagination.getExpiredTime());
        lqw.eq(pagination.getSuccessTime() != null, PaymentOrder::getSuccessTime, pagination.getSuccessTime());
        lqw.eq(StringUtils.isNotBlank(pagination.getUserId()), PaymentOrder::getUserId, pagination.getUserId());
        return lqw;
    }
}