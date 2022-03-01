package com.zhaoxinms.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractCrForm;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractPagination;

import java.util.*;

/**
 *
 * payment_contract 版本： V3.1.0 版权： 作者： CYCBERFORM 日期： 2021-08-23 15:28:33
 */
public interface PaymentContractService extends IService<PaymentContractEntity> {

    List<PaymentContractEntity> getList(PaymentContractPagination paymentContractPagination);

    List<PaymentContractEntity> getTypeList(PaymentContractPagination paymentContractPagination, String dataType);

    List<PaymentContractEntity> getListByResourceIds(List<String> ids);

    PaymentContractEntity getInfo(String id);

    /**
     * 取消房屋租赁合同
     * 
     * @param id
     * @param entity
     * @return
     */
    void cancelContract(String houseId) throws DataException;

    /**
     * 创建合同
     * 
     * @param form
     * @throws DataException
     */
    void create(PaymentContractCrForm form) throws DataException;

    /**
     * 更新合同
     * 
     * @param form
     * @throws DataException
     */
    void update(String id, PaymentContractCrForm form) throws DataException;

    PaymentContractEntity getByResourceName(String resourceName);

    List<PaymentContractEntity> getByResourceNameTips(String resourceName);

    List<PaymentContractEntity> getByOwnerId(String ownerId);
}
