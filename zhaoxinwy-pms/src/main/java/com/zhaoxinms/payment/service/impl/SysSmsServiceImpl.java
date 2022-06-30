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
import com.zhaoxinms.payment.mapper.SysSmsMapper;
import com.zhaoxinms.payment.entity.SysSms;
import com.zhaoxinms.payment.service.ISysSmsService;
import com.zhaoxinms.payment.entity.pagination.SysSmsPagination;

/**
 * 消息推送记录Service业务层处理
 * 
 * @author fanhuibin
 * @date 2022-06-28
 */
@Service
public class SysSmsServiceImpl extends ServiceImpl<SysSmsMapper, SysSms> implements ISysSmsService 
{
    @Autowired
    private SysSmsMapper sysSmsMapper;
    @Autowired
    private UserProvider userProvider;
    
    @Override
    public List<SysSms> getList(SysSmsPagination pagination) {
    	LambdaQueryWrapper<SysSms> lqw = buildQueryWrapper(pagination);
    	lqw.orderByDesc(SysSms::getCreateTime);
    	
        Page<SysSms> page =
            new Page<>(pagination.getCurrentPage(), pagination.getPageSize());
        IPage<SysSms> userIPage = this.page(page, lqw);
        return pagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }
    
    @Override
    public SysSms getInfo(String id) {
        QueryWrapper<SysSms> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysSms::getId, id);
        return this.getOne(queryWrapper);
    }
    
    @Override
    public void create(SysSms entity) {
		validEntityBeforeSave(entity);
        this.save(entity);
    }
    
    @Override
    public boolean update(String id, SysSms entity) {
      	entity.setId(id);
      	validEntityBeforeSave(entity);
        return this.updateById(entity);
    }
    
    @Override
    public void delete(SysSms entity) {
        if (entity != null) {
        	 this.removeById(entity.getId());
        }
    }

	private LambdaQueryWrapper<SysSms> buildQueryWrapper(SysSmsPagination pagination) {
        LambdaQueryWrapper<SysSms> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(pagination.getEsTitle()), SysSms::getEsTitle, pagination.getEsTitle());
        lqw.eq(StringUtils.isNotBlank(pagination.getEsType()), SysSms::getEsType, pagination.getEsType());
        lqw.eq(StringUtils.isNotBlank(pagination.getEsReceiver()), SysSms::getEsReceiver, pagination.getEsReceiver());
        lqw.eq(StringUtils.isNotBlank(pagination.getEsParam()), SysSms::getEsParam, pagination.getEsParam());
        lqw.eq(StringUtils.isNotBlank(pagination.getEsContent()), SysSms::getEsContent, pagination.getEsContent());
        lqw.eq(pagination.getEsSendTime() != null, SysSms::getEsSendTime, pagination.getEsSendTime());
        lqw.eq(StringUtils.isNotBlank(pagination.getEsSendStatus()), SysSms::getEsSendStatus, pagination.getEsSendStatus());
        lqw.eq(pagination.getEsSendNum() != null, SysSms::getEsSendNum, pagination.getEsSendNum());
        lqw.eq(StringUtils.isNotBlank(pagination.getEsResult()), SysSms::getEsResult, pagination.getEsResult());
        return lqw;
    }


    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(SysSms entity){
        //TODO 做一些数据校验,如唯一约束
    }
}
