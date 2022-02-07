package com.zhaoxinms.base.util;

import org.springframework.stereotype.Component;

import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.utils.SecurityUtils;

@Component
public class UserProvider {

    public SysUser get() {
    	SysUser user = SecurityUtils.getLoginUser().getUser();
        return user;
    }

   
}
