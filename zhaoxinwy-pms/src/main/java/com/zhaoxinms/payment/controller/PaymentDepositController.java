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
import org.springframework.web.bind.annotation.PutMapping;
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
import com.zhaoxinms.payment.entity.PaymentDepositEntity;
import com.zhaoxinms.payment.entity.PaymentMethod;
import com.zhaoxinms.payment.entity.PaymentPreEntity;
import com.zhaoxinms.payment.entity.pagination.PaymentMethodPagination;
import com.zhaoxinms.payment.model.paymentdeposit.PaymentDepositCrForm;
import com.zhaoxinms.payment.model.paymentdeposit.PaymentDepositInfoVO;
import com.zhaoxinms.payment.model.paymentdeposit.PaymentDepositListVO;
import com.zhaoxinms.payment.model.paymentdeposit.PaymentDepositPagination;
import com.zhaoxinms.payment.model.paymentdeposit.PaymentDepositRefundForm;
import com.zhaoxinms.payment.service.IPaymentMethodService;
import com.zhaoxinms.payment.service.PaymentDepositService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "押金管理", description = "")
@RequestMapping("/payment/PaymentDeposit")
public class PaymentDepositController {
    @Autowired
    private DynDicUtil dynDicUtil;
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private PaymentDepositService paymentDepositService;
    @Autowired
    private IPaymentMethodService paymentMethodService;

    /**
     * 列表
     *
     * @param paymentDepositPagination
     * @return
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @GetMapping
    public ActionResult list(PaymentDepositPagination paymentDepositPagination) throws IOException {
        List<PaymentDepositEntity> list = paymentDepositService.getList(paymentDepositPagination);
        List<PaymentMethod> methods = paymentMethodService.getList(new PaymentMethodPagination());
        for (PaymentDepositEntity entity : list) {
            for(PaymentMethod method : methods) {
                if(method.getCode().equals(entity.getPayType())) {
                    entity.setPayType(method.getName());
                }
            }
        }
        
        List<PaymentDepositListVO> listVO = JsonUtil.getJsonToList(list, PaymentDepositListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(paymentDepositPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 创建
     *
     * @param paymentDepositCrForm
     * @return
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @Log(title = "创建押金", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@RequestBody @Valid PaymentDepositCrForm paymentDepositCrForm) throws DataException {
        SysUser userInfo = userProvider.get();
        PaymentDepositEntity entity = JsonUtil.getJsonToBean(paymentDepositCrForm, PaymentDepositEntity.class);
        paymentDepositService.create(entity);
        return ActionResult.success("新增押金成功");
    }

    /**
     * 信息
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @GetMapping("/{id}")
    public ActionResult<PaymentDepositInfoVO> info(@PathVariable("id") String id) {
        PaymentDepositEntity entity = paymentDepositService.getInfo(id);
        PaymentDepositInfoVO vo = JsonUtil.getJsonToBean(entity, PaymentDepositInfoVO.class);
        return ActionResult.success(vo);
    }

    /**
     * 退款
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @Log(title = "押金退款", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id, @RequestBody @Valid PaymentDepositRefundForm form)
        throws DataException {
        boolean succ = paymentDepositService.refund(id, form);
        if (succ) {
            return ActionResult.success("退还押金成功");
        } else {
            return ActionResult.fail("退还押金失败，数据不存在");
        }
    }
}
