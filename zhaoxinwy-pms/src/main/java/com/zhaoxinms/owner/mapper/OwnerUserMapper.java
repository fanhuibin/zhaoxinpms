package com.zhaoxinms.owner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaoxinms.owner.entity.OwnerUser;

/**
 * 业主信息Mapper接口
 * 
 * @author cycberform
 * @date 2022-02-23
 */
public interface OwnerUserMapper extends BaseMapper<OwnerUser> {

    @Update(value = "update owner_user set is_bind = #{bind} where id = #{id}")
    public void updateBind(@Param(value = "bind") byte bind, @Param(value = "id") Long id);
}
