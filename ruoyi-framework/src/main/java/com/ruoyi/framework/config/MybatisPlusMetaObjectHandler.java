package com.ruoyi.framework.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.sun.jna.platform.win32.Netapi32Util.UserInfo;

@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
    	SysUser user = SecurityUtils.getLoginUser().getUser();
        Object enabledMark = this.getFieldValByName("enabledMark", metaObject);
        Object creatorUserId = this.getFieldValByName("creatorUserId", metaObject);
        Object creatorTime = this.getFieldValByName("creatorTime", metaObject);
        Object creatorUser = this.getFieldValByName("creatorUser", metaObject);
        if (enabledMark == null) {
            this.setFieldValByName("enabledMark", 1, metaObject);
        }
        if (creatorUserId == null) {
            this.setFieldValByName("creatorUserId", ""+user.getUserId(), metaObject);
        }
        if (creatorTime == null) {
            this.setFieldValByName("creatorTime", new Date(), metaObject);
        }
        if (creatorUser == null) {
            this.setFieldValByName("creatorUser", ""+user.getUserId(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    	SysUser user = SecurityUtils.getLoginUser().getUser();
        this.setFieldValByName("lastModifyTime", new Date(), metaObject);
        this.setFieldValByName("lastModifyUserId", ""+user.getUserId(), metaObject);
        this.setFieldValByName("lastModifyUser", ""+user.getUserId(), metaObject);
    }


}
