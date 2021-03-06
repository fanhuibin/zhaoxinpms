package com.zhaoxinms.framework.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.pagehelper.Page;
import com.sun.jna.platform.win32.Netapi32Util.UserInfo;
import com.zhaoxinms.common.constant.HttpStatus;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.core.mybatisplus.BaseEntity;
import com.zhaoxinms.common.exception.ServiceException;
import com.zhaoxinms.common.utils.SecurityUtils;
import com.zhaoxinms.common.utils.StringUtils;
import com.zhaoxinms.common.utils.spring.SpringUtils;
import com.zhaoxinms.system.service.ISysUserService;

@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    
    @Override
    public void insertFill(MetaObject metaObject) {
        SysUser user;
        if(new Boolean(true).equals(SecurityUtils.isApp.get())) {
            ISysUserService sysUserService = SpringUtils.getBean(ISysUserService.class);
            user = sysUserService.selectUserById(1l);
        }else {
            user = SecurityUtils.getLoginUser().getUser();
        }
        
        insertFillOld(metaObject, user);
        insertFillNew(metaObject, user);
    }
    
    private void insertFillOld(MetaObject metaObject, SysUser user) {
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
    
    private void insertFillNew(MetaObject metaObject, SysUser user) {
        try {
            if (metaObject != null && metaObject.getOriginalObject() instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
                Date current = new Date();
                // ?????????????????? ?????????
                if (baseEntity.getCreateTime() == null) {
                    baseEntity.setCreateTime(current);
                }
                // ?????????????????? ?????????
                if (baseEntity.getUpdateTime() == null) {
                    baseEntity.setUpdateTime(current);
                }
                // ??????????????? ??? ??????????????? ?????????
                if (StringUtils.isNotBlank(user.getUserName())
                        && StringUtils.isBlank(baseEntity.getCreateBy())) {
                    baseEntity.setCreateBy(user.getUserName());
                }
                // ??????????????? ??? ??????????????? ?????????
                if (StringUtils.isNotBlank(user.getUserName())
                        && StringUtils.isBlank(baseEntity.getUpdateBy())) {
                    baseEntity.setUpdateBy(user.getUserName());
                }
            }
        } catch (Exception e) {
            throw new ServiceException("?????????????????? => " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        SysUser user;
        if(new Boolean(true).equals(SecurityUtils.isApp.get())) {
            ISysUserService sysUserService = SpringUtils.getBean(ISysUserService.class);
            user = sysUserService.selectUserById(1l);
        }else {
            user = SecurityUtils.getLoginUser().getUser();
        }
        
        oldUpdate(metaObject, user);
        newUpdate(metaObject, user);
    }

    private void newUpdate(MetaObject metaObject, SysUser user) {
        try {
            if (metaObject != null && metaObject.getOriginalObject() instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
                Date current = new Date();
                // ??????????????????(??????????????????)
                baseEntity.setUpdateTime(current);
                // ??????????????? ???????????????(??????????????????)
                if (StringUtils.isNotBlank(user.getUserName())) {
                    baseEntity.setUpdateBy(user.getUserName());
                }
            }
        } catch (Exception e) {
            throw new ServiceException("?????????????????? => " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    private void oldUpdate(MetaObject metaObject, SysUser user) {
        this.setFieldValByName("lastModifyTime", new Date(), metaObject);
        this.setFieldValByName("lastModifyUserId", ""+user.getUserId(), metaObject);
        this.setFieldValByName("lastModifyUser", ""+user.getUserId(), metaObject);
    }
}
