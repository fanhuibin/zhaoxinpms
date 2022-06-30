package com.zhaoxinms.payment.service.impl;

import java.util.List;
import com.zhaoxinms.common.utils.DateUtils;
import com.zhaoxinms.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.payment.mapper.SysSmsTemplateMapper;
import com.zhaoxinms.payment.entity.SysSmsTemplate;
import com.zhaoxinms.payment.service.ISysSmsTemplateService;
import com.zhaoxinms.payment.entity.pagination.SysSmsTemplatePagination;

/**
 * 短信模板Service业务层处理
 * 
 * @author fanhuibin
 * @date 2022-06-28
 */
@Service
public class SysSmsTemplateServiceImpl extends ServiceImpl<SysSmsTemplateMapper, SysSmsTemplate> implements ISysSmsTemplateService 
{
    @Autowired
    private SysSmsTemplateMapper sysSmsTemplateMapper;
    @Autowired
    private UserProvider userProvider;
    
    @Override
    public List<SysSmsTemplate> getList(SysSmsTemplatePagination pagination) {
    	LambdaQueryWrapper<SysSmsTemplate> lqw = buildQueryWrapper(pagination);
    	lqw.orderByDesc(SysSmsTemplate::getCreateTime);
    	
        Page<SysSmsTemplate> page =
            new Page<>(pagination.getCurrentPage(), pagination.getPageSize());
        IPage<SysSmsTemplate> userIPage = this.page(page, lqw);
        return pagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }
    
    @Override
    public SysSmsTemplate getInfo(String id) {
        QueryWrapper<SysSmsTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysSmsTemplate::getId, id);
        return this.getOne(queryWrapper);
    }
    
    @Override
    public void create(SysSmsTemplate entity) {
		validEntityBeforeSave(entity);
        this.save(entity);
    }
    
    @Override
    public boolean update(String id, SysSmsTemplate entity) {
      	entity.setId(id);
      	validEntityBeforeSave(entity);
        return this.updateById(entity);
    }
    
    @Override
    public void delete(SysSmsTemplate entity) {
        if (entity != null) {
        	 this.removeById(entity.getId());
        }
    }

	private LambdaQueryWrapper<SysSmsTemplate> buildQueryWrapper(SysSmsTemplatePagination pagination) {
        LambdaQueryWrapper<SysSmsTemplate> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(pagination.getTemplateName()), SysSmsTemplate::getTemplateName, pagination.getTemplateName());
        lqw.eq(StringUtils.isNotBlank(pagination.getTemplateCode()), SysSmsTemplate::getTemplateCode, pagination.getTemplateCode());
        lqw.eq(StringUtils.isNotBlank(pagination.getTemplateType()), SysSmsTemplate::getTemplateType, pagination.getTemplateType());
        lqw.eq(StringUtils.isNotBlank(pagination.getTemplateContent()), SysSmsTemplate::getTemplateContent, pagination.getTemplateContent());
        return lqw;
    }


    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(SysSmsTemplate entity){
        //TODO 做一些数据校验,如唯一约束
    }
}
