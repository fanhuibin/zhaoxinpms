package com.zhaoxinms.payment.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.payment.entity.PaymentMethod;
import com.zhaoxinms.payment.entity.pagination.PaymentMethodPagination;

/**
 * 支付方式Service接口
 * 
 * @author fanhuibin
 * @date 2022-04-15
 */
public interface IPaymentMethodService extends IService<PaymentMethod>
{

    List<PaymentMethod> getList(PaymentMethodPagination pagination);

    PaymentMethod getInfo(String id);
    
    PaymentMethod getByCode(String code);

    void delete(PaymentMethod entity);

    void create(PaymentMethod entity);

    boolean update(String id, PaymentMethod entity);
}
