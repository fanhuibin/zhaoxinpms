package com.zhaoxinms.payment.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.payment.entity.SysSms;
import com.zhaoxinms.payment.entity.pagination.SysSmsPagination;

/**
 * 消息推送记录Service接口
 * 
 * @author fanhuibin
 * @date 2022-06-28
 */
public interface ISysSmsService extends IService<SysSms>
{

    List<SysSms> getList(SysSmsPagination pagination);

    SysSms getInfo(String id);

    void delete(SysSms entity);

    void create(SysSms entity);

    boolean update(String id, SysSms entity);
}
