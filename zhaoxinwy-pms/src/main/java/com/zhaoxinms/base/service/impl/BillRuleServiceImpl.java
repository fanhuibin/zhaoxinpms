package com.zhaoxinms.base.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.entity.BillRuleEntity;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.mapper.BillRuleMapper;
import com.zhaoxinms.base.service.BillRuleService;
import com.zhaoxinms.base.util.DateUtil;
import com.zhaoxinms.base.util.PadUtil;
import com.zhaoxinms.base.util.RandomUtil;
import com.zhaoxinms.base.util.RedisUtil;
import com.zhaoxinms.base.util.StringUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.util.type.SortType;
import com.zhaoxinms.base.vo.Pagination;


@Service
public class BillRuleServiceImpl extends ServiceImpl<BillRuleMapper, BillRuleEntity> implements BillRuleService {

    @Autowired
    private UserProvider userProvider;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<BillRuleEntity> getList(Pagination pagination) {
        QueryWrapper<BillRuleEntity> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(pagination.getKeyword())) {
            queryWrapper.lambda().and(
                    t -> t.like(BillRuleEntity::getFullName, pagination.getKeyword())
                            .or().like(BillRuleEntity::getEnCode, pagination.getKeyword())
            );
        }
        if (SortType.ASC.equals(pagination.getSort().toLowerCase())) {
            queryWrapper.lambda().orderByAsc(BillRuleEntity::getSortCode).orderByAsc(BillRuleEntity::getCreatorTime);
        } else {
            queryWrapper.lambda().orderByDesc(BillRuleEntity::getSortCode).orderByDesc(BillRuleEntity::getCreatorTime);
        }
        Page<BillRuleEntity> page = new Page<>(pagination.getCurrentPage(), pagination.getPageSize());
        IPage<BillRuleEntity> userIPage = this.page(page, queryWrapper);
        return pagination.setData(userIPage.getRecords(), page.getTotal());
    }

    @Override
    public List<BillRuleEntity> getList() {
        QueryWrapper<BillRuleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(BillRuleEntity::getSortCode);
        return this.list(queryWrapper);
    }

    @Override
    public BillRuleEntity getInfo(String id) {
        QueryWrapper<BillRuleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BillRuleEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean isExistByFullName(String fullName, String id) {
        QueryWrapper<BillRuleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BillRuleEntity::getFullName, fullName);
        if (!StringUtils.isEmpty(id)) {
            queryWrapper.lambda().ne(BillRuleEntity::getId, id);
        }
        return this.count(queryWrapper) > 0 ? true : false;
    }

    @Override
    public boolean isExistByEnCode(String enCode, String id) {
        QueryWrapper<BillRuleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BillRuleEntity::getEnCode, enCode);
        if (!StringUtils.isEmpty(id)) {
            queryWrapper.lambda().ne(BillRuleEntity::getId, id);
        }
        return this.count(queryWrapper) > 0 ? true : false;
    }

    @Override
    @Transactional
    public String getNumber(String enCode) {
        StringBuilder strNumber = new StringBuilder();
        QueryWrapper<BillRuleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BillRuleEntity::getEnCode, enCode);
        BillRuleEntity entity = this.getOne(queryWrapper);
        if (entity != null) {
            //处理隔天流水号归0
            if (entity.getOutputNumber() != null) {
            	int prefixLength = entity.getPrefix().length();
                int dateLength = entity.getDateFormat().length();
                String serialDate = entity.getOutputNumber().substring(prefixLength, prefixLength+dateLength);
                String thisDate = DateUtil.dateNow("yyyyMMdd");
                if (!serialDate.equals(thisDate)) {
                    entity.setThisNumber(Integer.valueOf(entity.getStartNumber()));
                }
                entity.setThisNumber(entity.getThisNumber() + 1);
            } else {
                entity.setThisNumber(Integer.valueOf(entity.getStartNumber())+1);
            }
            //拼接单据编码
            // 前缀
            strNumber.append(entity.getPrefix());
            strNumber.append(DateUtil.dateNow(entity.getDateFormat()));
            strNumber.append(PadUtil.padRight(String.valueOf(entity.getThisNumber()), entity.getDigit(), '0'));
            //更新流水号
            entity.setOutputNumber(strNumber.toString());
            this.updateById(entity);
        } else {
            strNumber.append("单据规则不存在");
        }
        return strNumber.toString();
    }

    @Override
    public void create(BillRuleEntity entity) {
        entity.setId(RandomUtil.uuId());
        entity.setCreatorUserId(""+userProvider.get().getUserId());
        this.save(entity);
    }

    @Override
    public boolean update(String id, BillRuleEntity entity) {
        entity.setId(id);
        entity.setLastModifyTime(new Date());
        entity.setLastModifyUserId(""+userProvider.get().getUserId());
        return this.updateById(entity);
    }

    @Override
    public void delete(BillRuleEntity entity) {
        this.removeById(entity.getId());
    }

    @Override
    @Transactional
    public boolean first(String id) {
        boolean isOk = false;
        //获取要上移的那条数据的信息
        BillRuleEntity upEntity = this.getById(id);
        Long upSortCode = upEntity.getSortCode() == null ? 0 : upEntity.getSortCode();
        //查询上几条记录
        QueryWrapper<BillRuleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .lt(BillRuleEntity::getSortCode, upSortCode)
                .orderByDesc(BillRuleEntity::getSortCode);
        List<BillRuleEntity> downEntity = this.list(queryWrapper);
        if (downEntity.size() > 0) {
            //交换两条记录的sort值
            Long temp = upEntity.getSortCode();
            upEntity.setSortCode(downEntity.get(0).getSortCode());
            downEntity.get(0).setSortCode(temp);
            this.updateById(downEntity.get(0));
            this.updateById(upEntity);
            isOk = true;
        }
        return isOk;
    }

    @Override
    @Transactional
    public boolean next(String id) {
        boolean isOk = false;
        //获取要下移的那条数据的信息
        BillRuleEntity downEntity = this.getById(id);
        Long upSortCode = downEntity.getSortCode() == null ? 0 : downEntity.getSortCode();
        //查询下几条记录
        QueryWrapper<BillRuleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .gt(BillRuleEntity::getSortCode, upSortCode)
                .orderByAsc(BillRuleEntity::getSortCode);
        List<BillRuleEntity> upEntity = this.list(queryWrapper);
        if (upEntity.size() > 0) {
            //交换两条记录的sort值
            Long temp = downEntity.getSortCode();
            downEntity.setSortCode(upEntity.get(0).getSortCode());
            upEntity.get(0).setSortCode(temp);
            this.updateById(upEntity.get(0));
            this.updateById(downEntity);
            isOk = true;
        }
        return isOk;
    }

    @Override
    public String getBillNumber(String enCode, boolean isCache) throws DataException {
        String strNumber;
        if (isCache == true) {
            String cacheKey = userProvider.get().getUserId() + enCode;
            if (!redisUtil.exists(cacheKey)) {
                strNumber = this.getNumber(enCode);
                redisUtil.insert(cacheKey, strNumber);
            } else {
                strNumber = String.valueOf(redisUtil.getString(cacheKey));
            }
        } else {
            strNumber = this.getNumber(enCode);
        }
        return strNumber;
    }

    @Override
    public void useBillNumber(String enCode) {
        String cacheKey = userProvider.get().getUserId() + enCode;
        redisUtil.remove(cacheKey);
    }
}
