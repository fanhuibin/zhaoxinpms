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
import com.zhaoxinms.base.util.DynDicUtil;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.payment.entity.PaymentMethod;
import com.zhaoxinms.payment.entity.PaymentPreEntity;
import com.zhaoxinms.payment.entity.PaymentTempEntity;
import com.zhaoxinms.payment.entity.pagination.PaymentMethodPagination;
import com.zhaoxinms.payment.model.paymentpre.PaymentPreCrForm;
import com.zhaoxinms.payment.model.paymentpre.PaymentPreInfoVO;
import com.zhaoxinms.payment.model.paymentpre.PaymentPreListVO;
import com.zhaoxinms.payment.model.paymentpre.PaymentPrePagination;
import com.zhaoxinms.payment.model.paymentpre.PaymentPreRefundForm;
import com.zhaoxinms.payment.service.IPaymentMethodService;
import com.zhaoxinms.payment.service.PaymentPreAccountService;
import com.zhaoxinms.payment.service.PaymentPreService;
import com.zhaoxinms.util.ConstantsUtil;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "预付款管理", description = "")
@RequestMapping("/payment/PaymentPre")
public class PaymentPreController {
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private PaymentPreService paymentPreService;
    @Autowired
    private PaymentPreAccountService paymentPreAccountService;
    @Autowired
    private IPaymentMethodService paymentMethodService;

    /**
     * 列表
     *
     * @param paymentPrePagination
     * @return
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @GetMapping
    public ActionResult list(PaymentPrePagination paymentPrePagination) throws IOException {
        List<PaymentPreEntity> list = paymentPreService.getList(paymentPrePagination);
        List<PaymentMethod> methods = paymentMethodService.getList(new PaymentMethodPagination());
        for (PaymentPreEntity entity : list) {
            for(PaymentMethod method : methods) {
                if(method.getCode().equals(entity.getPayType())) {
                    entity.setPayType(method.getName());
                }
            }
        }
        
        List<PaymentPreListVO> listVO = JsonUtil.getJsonToList(list, PaymentPreListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(paymentPrePagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 收取预存款
     *
     * @param paymentPreCrForm
     * @return
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @Log(title = "新增预付款", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional
    public ActionResult deposit(@RequestBody @Valid PaymentPreCrForm paymentPreCrForm) throws DataException {
        SysUser userInfo = userProvider.get();
        PaymentPreEntity entity = JsonUtil.getJsonToBean(paymentPreCrForm, PaymentPreEntity.class);
        paymentPreService.create(entity, ConstantsUtil.PAY_PRE_TYPE_ADD);
        return ActionResult.success("预存成功");
    }

    /**
     * 退还预存款
     *
     * @param paymentPreCrForm
     * @return
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @Log(title = "预付款退款", businessType = BusinessType.UPDATE)
    @PostMapping("/refund")
    @Transactional
    public ActionResult withdraw(@RequestBody @Valid PaymentPreRefundForm paymentPreRefundForm) throws DataException {
        SysUser userInfo = userProvider.get();
        paymentPreService.refund(paymentPreRefundForm);
        return ActionResult.success("预付款退还成功");
    }

    /**
     * 信息
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @GetMapping("/{id}")
    public ActionResult<PaymentPreInfoVO> info(@PathVariable("id") String id) {
        PaymentPreEntity entity = paymentPreService.getInfo(id);
        PaymentPreInfoVO vo = JsonUtil.getJsonToBean(entity, PaymentPreInfoVO.class);
        return ActionResult.success(vo);
    }
}
