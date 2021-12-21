package com.zhaoxinms.statistics.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaoxinms.statistics.mapper.PaymentStatisticsMapper;
import com.zhaoxinms.statistics.model.PaymentFeeStatisticsModel;
import com.zhaoxinms.statistics.model.PaymentHouseStatisticsModel;

@Service
public class PaymentStatisticsService {
    @Autowired
    private PaymentStatisticsMapper mapper;

    public List<PaymentFeeStatisticsModel> getBillSumGroupByFee(String beginDate, String endDate) {
        List<PaymentFeeStatisticsModel> result = mapper.getBillSumGroupByFee(beginDate, endDate);
        return result;
    }

    public List<PaymentFeeStatisticsModel> getDepositSumGroupByFee(String beginDate, String endDate) {
        List<PaymentFeeStatisticsModel> result = mapper.getDepositSumGroupByFee(beginDate, endDate);
        return result;
    }

    public List<PaymentFeeStatisticsModel> getDepositRefundSumGroupByFee(String beginDate, String endDate) {
        List<PaymentFeeStatisticsModel> result = mapper.getDepositRefundSumGroupByFee(beginDate, endDate);
        return result;
    }

    public List<PaymentFeeStatisticsModel> getTempSumGroupByFee(String beginDate, String endDate) {
        List<PaymentFeeStatisticsModel> result = mapper.getTempSumGroupByFee(beginDate, endDate);
        return result;
    }

    public List<PaymentFeeStatisticsModel> getTempRefundSumGroupByFee(String beginDate, String endDate) {
        List<PaymentFeeStatisticsModel> result = mapper.getTempRefundSumGroupByFee(beginDate, endDate);
        return result;
    }

    public List<PaymentHouseStatisticsModel> getBillSumGroupByHouse(String beginDate, String endDate) {
        List<PaymentHouseStatisticsModel> result = mapper.getBillSumGroupByHouse(beginDate, endDate);
        return result;
    }

    public List<PaymentHouseStatisticsModel> getDepositSumGroupByHouse(String beginDate, String endDate) {
        List<PaymentHouseStatisticsModel> result = mapper.getDepositSumGroupByHouse(beginDate, endDate);
        return result;
    }

    public List<PaymentHouseStatisticsModel> getDepositRefundSumGroupByHouse(String beginDate, String endDate) {
        List<PaymentHouseStatisticsModel> result = mapper.getDepositRefundSumGroupByHouse(beginDate, endDate);
        return result;
    }

    public List<PaymentHouseStatisticsModel> getTempSumGroupByHouse(String beginDate, String endDate) {
        List<PaymentHouseStatisticsModel> result = mapper.getTempSumGroupByHouse(beginDate, endDate);
        return result;
    }

    public List<PaymentHouseStatisticsModel> getTempRefundSumGroupByHouse(String beginDate, String endDate) {
        List<PaymentHouseStatisticsModel> result = mapper.getTempRefundSumGroupByHouse(beginDate, endDate);
        return result;
    }

    // public Map<String,Object> getPreSum(String beginDate, String endDate){
    // Map<String,Object> result = mapper.getPreSum(beginDate, endDate);
    // return result;
    // }
    //
    // public Map<String,Object> getPreRefundSum(String beginDate, String endDate){
    // Map<String,Object> result = mapper.getPreRefundSum(beginDate, endDate);
    // return result;
    // }
}
