package com.zhaoxinms.payment.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.payment.entity.SysSmsTemplate;
import com.zhaoxinms.payment.entity.pagination.SysSmsTemplatePagination;

/**
 * 短信模板Service接口
 * 
 * @author fanhuibin
 * @date 2022-06-28
 */
public interface ISysSmsTemplateService extends IService<SysSmsTemplate>
{

    List<SysSmsTemplate> getList(SysSmsTemplatePagination pagination);

    SysSmsTemplate getInfo(String id);

    void delete(SysSmsTemplate entity);

    void create(SysSmsTemplate entity);

    boolean update(String id, SysSmsTemplate entity);
}
