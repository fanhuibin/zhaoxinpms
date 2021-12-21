package com.zhaoxinms.base.util;

import org.springframework.stereotype.Component;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;

@Component
public class UserProvider {

    public SysUser get() {
    	SysUser user = SecurityUtils.getLoginUser().getUser();
        return user;
    }

   
}
