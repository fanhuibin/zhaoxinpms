package com.zhaoxinms.statistics.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.statistics.model.PayLogStatisticsModel;
import com.zhaoxinms.statistics.model.PaymentFeeStatisticsModel;
import com.zhaoxinms.statistics.model.PaymentHouseAndFeeStatisticsModel;
import com.zhaoxinms.statistics.model.PaymentHouseStatisticsModel;

public interface PaymentStatisticsMapper extends BaseMapper<PaymentBillEntity> {

    @Select("SELECT count(1) as count,DATE_FORMAT(pay_time, '%Y-%m') AS month"
        + "    , IFNULL(sum(receivable_money),0) AS receivable, IFNULL(sum(discount_money),0) AS discount, IFNULL(sum(pay_money),0) as payMoney, IFNULL(sum(late_fee_money),0) as lateFeeMoney"
        + ", IFNULL(sum(change_money),0) AS changeMoney, IFNULL(sum(pre_pay_money),0) as prePayMoney, IFNULL(sum(pre_save_money),0) as preSaveMoney "
        + "FROM payment_pay_log " + "WHERE type = #{type} AND DATE_FORMAT(pay_time, '%Y-%m') = #{month} ")
    List<PayLogStatisticsModel> getMonthPayLog(@Param("month") String month, @Param("type") String type);

    // @Select("SELECT DATE_FORMAT(pay_time, '%Y-%m') AS month" +
    // " , IFNULL(sum(receivable_money),0) AS sumReceivable, IFNULL(sum(discount_money),0) AS sumDiscount,
    // IFNULL(sum(pay_money),0) as sumPayMoney, IFNULL(sum(late_fee_money),0) as sumLateFee" +
    // ", IFNULL(sum(change_money),0) AS sumChange, IFNULL(sum(pre_pay_money),0) as sumPrePay,
    // IFNULL(sum(pre_save_money),0) as sumPreSave "+
    // "FROM payment_pay_log " +
    // "WHERE pay_time BETWEEN #{beginDate} AND #{endDate} AND type = #{type} "+
    // "GROUP BY month")
    // List<Map<String,String>> getMonthPayLog(@Param("beginDate") String beginDate, @Param("endDate") String endDate,
    // @Param("type") String type);
    //

    @Select("SELECT count(1) as count,"
        + "IFNULL(sum(receivable_money),0) AS receivable, IFNULL(sum(discount_money),0) AS discount, IFNULL(sum(pay_money),0) as payMoney, IFNULL(sum(late_fee_money),0) as lateFeeMoney "
        + ", IFNULL(sum(change_money),0) AS changeMoney, IFNULL(sum(pre_pay_money),0) as prePayMoney, IFNULL(sum(pre_save_money),0) as preSaveMoney "
        + "FROM payment_pay_log " + "WHERE pay_time BETWEEN #{beginDate} AND #{endDate} AND type = #{type} ")
    List<PayLogStatisticsModel> getPayLogByDate(@Param("beginDate") String beginDate, @Param("endDate") String endDate,
        @Param("type") String type);

    @Select("SELECT count(1) as count, resource_name as resourceName, pay_method as payMethod, "
        + "IFNULL(sum(receivable_money),0) AS receivable, IFNULL(sum(discount_money),0) AS discount, IFNULL(sum(pay_money),0) as payMoney, IFNULL(sum(late_fee_money),0) as lateFeeMoney "
        + ", IFNULL(sum(change_money),0) AS changeMoney, IFNULL(sum(pre_pay_money),0) as prePayMoney, IFNULL(sum(pre_save_money),0) as preSaveMoney "
        + "FROM payment_pay_log " + "WHERE pay_time BETWEEN #{beginDate} AND #{endDate} AND type = #{type} "
        + "GROUP BY resource_name,pay_method")
    List<PayLogStatisticsModel> getPayLogGroupByResourceAndPayType(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate, @Param("type") String type);

    // 查询过期未完成付款的订单统计
    @Select("SELECT count(1) as count,IFNULL(sum(total),0) as sum " + "FROM payment_bill "
        + "WHERE enabled_mark = '1' AND pay_state = '0' AND deadline < now() ")
    Map<String, Object> getDelayPayCount();

    // 查询待付款的订单统计
    @Select("SELECT count(1) as count,IFNULL(sum(total),0) as sum " + "FROM payment_bill "
        + "WHERE enabled_mark = '1' AND pay_state = '0'")
    Map<String, Object> getUnpaiedCount();

    @Select("SELECT count(1) as count,IFNULL(sum(receivable),0) as total, IFNULL(sum(late_fee),0) as lateFee, IFNULL(sum(discount),0) as discount, fee_item_id as feeItemId, fee_item_name as feeItemName "
        + "FROM payment_bill "
        + "WHERE enabled_mark = '1' AND pay_state = '1' AND pay_time BETWEEN #{beginDate} AND #{endDate} "
        + "GROUP BY fee_item_id")
    List<PaymentFeeStatisticsModel> getBillSumGroupByFee(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate);

    @Select("SELECT count(1) as count, IFNULL(sum(amt),0) as total, fee_item_id as feeItemId, fee_item_name as feeItemName "
        + "FROM payment_deposit " + "WHERE operate_time BETWEEN #{beginDate} AND #{endDate} " + "GROUP BY fee_item_id")
    List<PaymentFeeStatisticsModel> getDepositSumGroupByFee(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate);

    @Select("SELECT count(1) as count, IFNULL(sum(amt),0) as total, fee_item_id as feeItemId, fee_item_name as feeItemName "
        + "FROM payment_deposit " + "WHERE state = 'refunded' AND refund_time BETWEEN #{beginDate} AND #{endDate} "
        + "GROUP BY fee_item_id")
    List<PaymentFeeStatisticsModel> getDepositRefundSumGroupByFee(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate);

    @Select("SELECT count(1) as count, IFNULL(sum(amt),0) as total, fee_item_id as feeItemId, fee_item_name as feeItemName "
        + "FROM payment_temp " + "WHERE operate_time BETWEEN #{beginDate} AND #{endDate} " + "GROUP BY fee_item_id")
    List<PaymentFeeStatisticsModel> getTempSumGroupByFee(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate);

    @Select("SELECT count(1) as count, IFNULL(sum(amt),0) as total, fee_item_id as feeItemId, fee_item_name as feeItemName "
        + "FROM payment_temp " + "WHERE state = 'refunded' AND refund_time BETWEEN #{beginDate} AND #{endDate} "
        + "GROUP BY fee_item_id")
    List<PaymentFeeStatisticsModel> getTempRefundSumGroupByFee(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate);

    // @Select("SELECT count(1) as count, IFNULL(sum(amt),0) as amt, fee_item_id as feeItemId, fee_item_name as
    // feeItemName " +
    // "FROM payment_pre " +
    // "WHERE type = 'pay' AND operate_time BETWEEN #{beginDate} AND #{endDate} ")
    // Map<String,Object> getPreSum(@Param("beginDate") String beginDate, @Param("endDate") String endDate);
    //
    // @Select("SELECT count(1) as count, IFNULL(sum(amt),0) as amt, fee_item_id as feeItemId, fee_item_name as
    // feeItemName " +
    // "FROM payment_pre " +
    // "WHERE type = 'refund' AND operate_time BETWEEN #{beginDate} AND #{endDate} ")
    // Map<String,Object> getPreRefundSum(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    @Select("SELECT count(1) as count, IFNULL(sum(amt),0) as amt, resource_name as resourceName, pay_type as payType "
        + "FROM payment_pre " + "WHERE type = #{type} AND operate_time BETWEEN #{beginDate} AND #{endDate} "
        + "GROUP BY resource_name,pay_type ")
    List<Map<String, Object>> getPreSumGroupByResourceNameAndPayType(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate, @Param("type") String type);

    @Select("SELECT count(1) as count, type as type, IFNULL(sum(amt),0) as amt, resource_name as resourceName "
        + "FROM payment_pre " + "WHERE operate_time BETWEEN #{beginDate} AND #{endDate} "
        + "GROUP BY resource_name,type ")
    List<Map<String, Object>> getPreSumGroupByResourceNameAndType(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate);

    @Select("SELECT count(1) as count,IFNULL(sum(receivable),0) as total, IFNULL(sum(late_fee),0) as lateFee, IFNULL(sum(discount),0) as discount, resource_id as resourceId, resource_name as resourceName "
        + "FROM payment_bill "
        + "WHERE enabled_mark = '1' AND pay_state = '1' AND pay_time BETWEEN #{beginDate} AND #{endDate} "
        + "GROUP BY resourceName order by resourceId")
    List<PaymentHouseStatisticsModel> getBillSumGroupByHouse(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate);

    @Select("SELECT count(1) as count, IFNULL(sum(amt),0) as total, resource_id as resourceId, resource_name as resourceName "
        + "FROM payment_deposit " + "WHERE operate_time BETWEEN #{beginDate} AND #{endDate} "
        + "GROUP BY resource_name order by resourceId")
    List<PaymentHouseStatisticsModel> getDepositSumGroupByHouse(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate);

    @Select("SELECT count(1) as count, IFNULL(sum(amt),0) as total, resource_id as resourceId, resource_name as resourceName "
        + "FROM payment_deposit " + "WHERE state = 'refunded' AND refund_time BETWEEN #{beginDate} AND #{endDate} "
        + "GROUP BY resource_name order by resourceId")
    List<PaymentHouseStatisticsModel> getDepositRefundSumGroupByHouse(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate);

    @Select("SELECT count(1) as count, IFNULL(sum(amt),0) as total, resource_id as resourceId, resource_name as resourceName "
        + "FROM payment_temp " + "WHERE operate_time BETWEEN #{beginDate} AND #{endDate} "
        + "GROUP BY resource_name order by resourceId")
    List<PaymentHouseStatisticsModel> getTempSumGroupByHouse(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate);

    @Select("SELECT count(1) as count, IFNULL(sum(amt),0) as total, resource_name as resourceName, resource_id as resourceId "
        + "FROM payment_temp " + "WHERE state = 'refunded' AND refund_time BETWEEN #{beginDate} AND #{endDate} "
        + "GROUP BY resource_name order by resourceId")
    List<PaymentHouseStatisticsModel> getTempRefundSumGroupByHouse(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate);

    @Select("SELECT count(1) as count,fee_item_id as feeItemId,IFNULL(sum(receivable),0) as total, IFNULL(sum(late_fee),0) as lateFee, IFNULL(sum(discount),0) as discount, resource_id as resourceId, resource_name as resourceName "
        + "FROM payment_bill "
        + "WHERE enabled_mark = '1' AND pay_state = '1' AND pay_time BETWEEN #{beginDate} AND #{endDate} "
        + "GROUP BY resource_name,fee_item_id order by resourceId")
    List<PaymentHouseAndFeeStatisticsModel> getBillSumGroupByHouseAndFee(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate);

    @Select("SELECT count(1) as count,fee_item_id as feeItemId, IFNULL(sum(amt),0) as total, resource_id as resourceId, resource_name as resourceName "
        + "FROM payment_deposit " + "WHERE operate_time BETWEEN #{beginDate} AND #{endDate} "
        + "GROUP BY resource_name,fee_item_id order by resourceId")
    List<PaymentHouseAndFeeStatisticsModel> getDepositSumGroupByHouseAndFee(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate);

    @Select("SELECT count(1) as count,fee_item_id as feeItemId, IFNULL(sum(amt),0) as total, resource_id as resourceId, resource_name as resourceName "
        + "FROM payment_temp " + "WHERE operate_time BETWEEN #{beginDate} AND #{endDate} "
        + "GROUP BY resource_name,fee_item_id order by resourceId")
    List<PaymentHouseAndFeeStatisticsModel> getTempSumGroupByHouseAndFee(@Param("beginDate") String beginDate,
        @Param("endDate") String endDate);
}
