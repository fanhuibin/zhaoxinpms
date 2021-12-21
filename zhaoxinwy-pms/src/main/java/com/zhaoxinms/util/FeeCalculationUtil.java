package com.zhaoxinms.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.entity.ConfigHouseEntity;
import com.zhaoxinms.baseconfig.service.impl.ConfigFeeItemServiceImpl;
import com.zhaoxinms.payment.entity.PaymentPreAccountEntity;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillListVO;
import com.zhaoxinms.payment.model.paymentpreaccount.PaymentPreAccountPayForm;

public class FeeCalculationUtil {

    public static String getHouseNum(ConfigHouseEntity house, ConfigFeeItemEntity fee) {
        if (fee.getNumType().equals(ConfigFeeItemServiceImpl.Number_BUILDINGAREA)) {
            return house.getBuildingSquare();
        } else if (fee.getNumType().equals(ConfigFeeItemServiceImpl.Number_Flat)) {
            return "1";
        } else if (fee.getNumType().equals(ConfigFeeItemServiceImpl.Number_FLOOR)) {
            return house.getFloor();
        } else if (fee.getNumType().equals(ConfigFeeItemServiceImpl.Number_METER)) {
            throw new DataException("请使用抄表功能获取数量");
        } else if (fee.getNumType().equals(ConfigFeeItemServiceImpl.Number_USEAREA)) {
            return house.getUseSquare();
        } else if (fee.getNumType().equals(ConfigFeeItemServiceImpl.Number_PEOPLE)) {
            throw new DataException("收费项的数量类型不支持");
        } else if (fee.getNumType().equals(ConfigFeeItemServiceImpl.Number_RENT_FEE)) {
            return house.getRentFee();
        } else {
            throw new DataException("收费项的数量类型不支持");
        }
    }

    public static String getMeterNum(String lastIndex, String currentIndex, String multiple, String loss) {
        String result = CalculationUtil.subtract(currentIndex, lastIndex, 2);
        result = CalculationUtil.multiply(result, multiple, 2);
        result = CalculationUtil.add(result, loss, 2);
        return result;
    }

    public static String getTotal(String num, String price, ConfigFeeItemEntity fee) {
        if (fee.getFormula().equals(ConfigFeeItemServiceImpl.FORMULA_BASE)) {
            return CalculationUtil.multiply(num, price, 2);
        } else if (fee.getFormula().equals(ConfigFeeItemServiceImpl.FORMULA_EXPRESSION)) {
            return DynamicExpressiontUtil.getExpressResult(fee.getFormulaExpression(), num, price);
        } else {
            throw new DataException("收费项的公式不支持");
        }
    }

    /**
     * 计算滞纳金
     * 
     * @throws IllegalAccessException
     */
    public static String getLateFee(String total, Date deadline, ConfigFeeItemEntity entity)
        throws IllegalAccessException {
        Date currentDate = new Date();
        if (entity.getLateFeeEnable().equals("0") || deadline.getTime() > currentDate.getTime()) {
            return "0";
        } else {
            double days = DateUtils.getDistanceOfTwoDate(deadline, currentDate);
            if (days > Integer.valueOf(entity.getLateFeeDays())) {
                double realDay = days - Integer.valueOf(entity.getLateFeeDays());
                DecimalFormat format = new DecimalFormat();
                format.setRoundingMode(RoundingMode.UP);
                String resultDays = format.format(realDay);

                // 计费天数*计费百分比*金额
                String dayFee = CalculationUtil.multiply(total, entity.getLateFeeRate(), 2);
                dayFee = CalculationUtil.divide(dayFee, "100", 2);
                String resultFee = CalculationUtil.multiply(dayFee, resultDays, 2);
                return resultFee;
            } else {
                return "0";
            }
        }
    }

    /**
     * 计算应收金额
     * 
     * @return
     */
    public static String getReceivable(String total, String lateFee, String discount) {
        String result = CalculationUtil.add(total, lateFee, 2);
        result = CalculationUtil.subtract(result, discount, 2);
        return result;
    }

    /**
     * 计算余额账户可用情况
     * 
     * @return
     */
    public static List<PaymentPreAccountPayForm> getAccountFeePay(List<PaymentPreAccountEntity> preAccounts,
        List<PaymentBillListVO> bills) {
        List<PaymentPreAccountPayForm> payForms = new ArrayList<PaymentPreAccountPayForm>();

        // 1.分收费项统计付款金额
        // feeTotal是剩余的待付款
        Map<String, String> feeTotal = new HashMap<String, String>();
        for (PaymentBillListVO vo : bills) {
            if (feeTotal.get(vo.getFeeItemId()) == null) {
                String total = "0";
                total = CalculationUtil.add(total, vo.getReceivable());
                feeTotal.put(vo.getFeeItemId(), total);
            } else {
                String total = feeTotal.get(vo.getFeeItemId());
                total = CalculationUtil.add(total, vo.getReceivable());
                feeTotal.put(vo.getFeeItemId(), total);
            }
        }

        // 2分收费项统计预存款可用余额 与 付款金额的关系
        // 2.1指定余额大于付款金额，全额支付
        // 2.2指定余额小于付款金额，剩下的部分用不指定收费项的余额结算
        // 2.3指定余额不存在，直接进入不指定收费项结算
        for (PaymentPreAccountEntity account : preAccounts) {
            PaymentPreAccountPayForm payForm = JsonUtil.getJsonToBean(account, PaymentPreAccountPayForm.class);
            if (!StringUtils.isEmpty(account.getFeeItemId())) {
                String accountMoney = payForm.getAmt();
                String payMoney = feeTotal.get(payForm.getFeeItemId());
                if (payMoney == null) {
                    payMoney = "0.00";
                }
                if (CalculationUtil.compareTo(accountMoney, payMoney, 2, 0) > 0) {
                    payForm.setPayMoney(payMoney);
                    feeTotal.put(payForm.getFeeItemId(), "0");
                } else {
                    payForm.setPayMoney(accountMoney);
                    feeTotal.put(payForm.getFeeItemId(), CalculationUtil.subtract(payMoney, accountMoney, 2));
                }
                payForms.add(payForm);
            }
        }

        // 统计剩下的待付款
        String redius = "0";
        for (String key : feeTotal.keySet()) {
            redius = CalculationUtil.add(redius, feeTotal.get(key));
        }

        // 计算无分类账户的使用金额
        for (PaymentPreAccountEntity account : preAccounts) {
            PaymentPreAccountPayForm payForm = JsonUtil.getJsonToBean(account, PaymentPreAccountPayForm.class);
            if (StringUtils.isEmpty(account.getFeeItemId())) {
                String accountMoney = payForm.getAmt();
                String payMoney = redius;
                if (CalculationUtil.compareTo(accountMoney, payMoney, 2, 0) > 0) {
                    payForm.setPayMoney(payMoney);
                } else {
                    payForm.setPayMoney(accountMoney);
                }
                payForms.add(payForm);
            }
        }
        return payForms;
    }
}
