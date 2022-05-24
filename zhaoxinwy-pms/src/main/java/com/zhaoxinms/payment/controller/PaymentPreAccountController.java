/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms website：http://pms.zhaoxinms.com wx： zhaoxinms
 *
 */
package com.zhaoxinms.payment.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.RandomUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.model.configfeeitem.ConfigFeeItemPagination;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.payment.entity.PaymentPreAccountEntity;
import com.zhaoxinms.payment.model.paymentpreaccount.PaymentPreAccountCrForm;
import com.zhaoxinms.payment.model.paymentpreaccount.PaymentPreAccountInfoVO;
import com.zhaoxinms.payment.model.paymentpreaccount.PaymentPreAccountListVO;
import com.zhaoxinms.payment.model.paymentpreaccount.PaymentPreAccountPagination;
import com.zhaoxinms.payment.service.PaymentPreAccountService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "预存款账户管理", description = "")
@RequestMapping("/payment/PaymentPreAccount")
public class PaymentPreAccountController {
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private PaymentPreAccountService paymentPreAccountService;
    @Autowired
    private ConfigFeeItemService configFeeItemService;

    /**
     * 列表
     *
     * @param paymentPreAccountPagination
     * @return
     */
    @GetMapping
    public ActionResult list(PaymentPreAccountPagination paymentPreAccountPagination) throws IOException {
        List<PaymentPreAccountEntity> list = paymentPreAccountService.getList(paymentPreAccountPagination);
        List<PaymentPreAccountListVO> listVO = JsonUtil.getJsonToList(list, PaymentPreAccountListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(paymentPreAccountPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 查询resourceId下所有的账户
     * 
     * @param resourceId
     * @return
     */
    @GetMapping("/accounts/{resourceId}")
    public ActionResult accounts(@PathVariable("resourceId") String resourceId) {

        List<PaymentPreAccountEntity> accounts = paymentPreAccountService.getAccountsByResourceId(resourceId);
        List<PaymentPreAccountListVO> result = JsonUtil.getJsonToList(accounts, PaymentPreAccountListVO.class);
        // 查询收费项信息
        List<ConfigFeeItemEntity> items = configFeeItemService.getTypeList(new ConfigFeeItemPagination(), "1");
        for (PaymentPreAccountListVO form : result) {
            if (form.getUseFeeItem().equals("0")) {
                form.setFeeItemName("不指定收费项");
                continue;
            }
            for (ConfigFeeItemEntity item : items) {
                if (form.getFeeItemId().equals(item.getId()) && form.getUseFeeItem().equals("1")) {
                    form.setFeeItemName(item.getName());
                }
            }
        }
        return ActionResult.success(result);
    }

    /**
     * 查询resourceId余额大于0的账户
     * 
     * @param resourceId
     * @return
     */
    @GetMapping("/accounts/{resourceId}/canUse")
    public ActionResult accountsCanUse(@PathVariable("resourceId") String resourceId) {

        List<PaymentPreAccountEntity> accounts = paymentPreAccountService.getAccountsByResourceId(resourceId);
        List<PaymentPreAccountEntity> canUse = new ArrayList<PaymentPreAccountEntity>();
        for (PaymentPreAccountEntity account : accounts) {
            if (new BigDecimal(account.getAmt()).compareTo(BigDecimal.ZERO) > 0) {
                canUse.add(account);
            }
        }
        if (canUse.size() == 0) {
            throw new DataException("该用户没有预存款，查询失败");
        }

        List<PaymentPreAccountListVO> result = JsonUtil.getJsonToList(canUse, PaymentPreAccountListVO.class);
        // 查询收费项信息
        List<ConfigFeeItemEntity> items = configFeeItemService.getTypeList(new ConfigFeeItemPagination(), "1");
        for (PaymentPreAccountListVO form : result) {
            if (form.getUseFeeItem().equals("0")) {
                form.setFeeItemName("不指定收费项");
                continue;
            }
            for (ConfigFeeItemEntity item : items) {
                if (form.getFeeItemId().equals(item.getId()) && form.getUseFeeItem().equals("1")) {
                    form.setFeeItemName(item.getName());
                }
            }
        }
        return ActionResult.success(result);
    }
}
