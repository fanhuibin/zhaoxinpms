package com.zhaoxinms.owner.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.owner.entity.OwnerUser;
import com.zhaoxinms.owner.entity.pagination.OwnerUserPagination;

/**
 * 业主信息Service接口
 * 
 * @author cycberform
 * @date 2022-02-23
 */
public interface IOwnerUserService extends IService<OwnerUser>
{

    List<OwnerUser> getList(OwnerUserPagination pagination);

    OwnerUser getInfo(Long id);

    void delete(OwnerUser entity);

    void create(OwnerUser entity);

    boolean update(Long id, OwnerUser entity);

    OwnerUser getByIdcard(String idcard);

    OwnerUser getByPhoneNo(String phoneNo);
}
