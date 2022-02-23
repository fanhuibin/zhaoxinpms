package com.zhaoxinms.owner.service.impl;

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
import com.zhaoxinms.owner.mapper.OwnerUserMapper;
import com.zhaoxinms.owner.entity.OwnerUser;
import com.zhaoxinms.owner.service.IOwnerUserService;
import com.zhaoxinms.owner.entity.pagination.OwnerUserPagination;

/**
 * 业主信息Service业务层处理
 * 
 * @author cycberform
 * @date 2022-02-23
 */
@Service
public class OwnerUserServiceImpl extends ServiceImpl<OwnerUserMapper, OwnerUser> implements IOwnerUserService 
{
    @Autowired
    private OwnerUserMapper ownerUserMapper;
    @Autowired
    private UserProvider userProvider;
    
    @Override
    public List<OwnerUser> getList(OwnerUserPagination pagination) {
    	LambdaQueryWrapper<OwnerUser> lqw = buildQueryWrapper(pagination);
    	lqw.orderByDesc(OwnerUser::getCreateTime);
    	
        Page<OwnerUser> page =
            new Page<>(pagination.getCurrentPage(), pagination.getPageSize());
        IPage<OwnerUser> userIPage = this.page(page, lqw);
        return pagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }
    
    @Override
    public OwnerUser getInfo(Long id) {
        QueryWrapper<OwnerUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OwnerUser::getId, id);
        return this.getOne(queryWrapper);
    }
    
    @Override
    public void create(OwnerUser entity) {
		validEntityBeforeSave(entity);
        this.save(entity);
    }
    
    @Override
    public boolean update(Long id, OwnerUser entity) {
      	entity.setId(id);
      	validEntityBeforeSave(entity);
        return this.updateById(entity);
    }
    
    @Override
    public void delete(OwnerUser entity) {
        if (entity != null) {
        	 this.delete(entity);
        }
    }

	private LambdaQueryWrapper<OwnerUser> buildQueryWrapper(OwnerUserPagination pagination) {
        LambdaQueryWrapper<OwnerUser> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(pagination.getUserName()), OwnerUser::getUserName, pagination.getUserName());
        lqw.eq(StringUtils.isNotBlank(pagination.getIdcard()), OwnerUser::getIdcard, pagination.getIdcard());
        lqw.eq(StringUtils.isNotBlank(pagination.getPhonenumber()), OwnerUser::getPhonenumber, pagination.getPhonenumber());
        lqw.eq(pagination.getOwnCount() != null, OwnerUser::getOwnCount, pagination.getOwnCount());
        return lqw;
    }


    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(OwnerUser entity){
        //TODO 做一些数据校验,如唯一约束
    }
}
