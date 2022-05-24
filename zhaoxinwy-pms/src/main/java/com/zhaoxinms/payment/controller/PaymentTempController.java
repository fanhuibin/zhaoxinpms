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
import com.zhaoxinms.payment.entity.PaymentMethod;
import com.zhaoxinms.payment.entity.PaymentTempEntity;
import com.zhaoxinms.payment.entity.pagination.PaymentMethodPagination;
import com.zhaoxinms.payment.model.paymenttemp.PaymentTempCrForm;
import com.zhaoxinms.payment.model.paymenttemp.PaymentTempInfoVO;
import com.zhaoxinms.payment.model.paymenttemp.PaymentTempListVO;
import com.zhaoxinms.payment.model.paymenttemp.PaymentTempPagination;
import com.zhaoxinms.payment.model.paymenttemp.PaymentTempRefundForm;
import com.zhaoxinms.payment.service.IPaymentMethodService;
import com.zhaoxinms.payment.service.PaymentTempService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "临时收费", description = "")
@RequestMapping("/payment/PaymentTemp")
public class PaymentTempController {
    @Autowired
    private DynDicUtil dynDicUtil;
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private PaymentTempService paymentTempService;
    @Autowired
    private IPaymentMethodService paymentMethodService;

    /**
     * 列表
     *
     * @param paymentTempPagination
     * @return
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @GetMapping
    public ActionResult list(PaymentTempPagination paymentTempPagination) throws IOException {
        List<PaymentTempEntity> list = paymentTempService.getList(paymentTempPagination);
        List<PaymentMethod> methods = paymentMethodService.getList(new PaymentMethodPagination());
        for (PaymentTempEntity entity : list) {
            for(PaymentMethod method : methods) {
                if(method.getCode().equals(entity.getPayType())) {
                    entity.setPayType(method.getName());
                }
            }
        }
        List<PaymentTempListVO> listVO = JsonUtil.getJsonToList(list, PaymentTempListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(paymentTempPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 创建
     *
     * @param paymentTempCrForm
     * @return
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @Log(title = "临时收费", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@RequestBody @Valid PaymentTempCrForm paymentTempCrForm) throws DataException {
        SysUser userInfo = userProvider.get();
        PaymentTempEntity entity = JsonUtil.getJsonToBean(paymentTempCrForm, PaymentTempEntity.class);
        paymentTempService.create(entity);
        return ActionResult.success("新增费用成功");
    }

    /**
     * 信息
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @GetMapping("/{id}")
    public ActionResult<PaymentTempInfoVO> info(@PathVariable("id") String id) {
        PaymentTempEntity entity = paymentTempService.getInfo(id);
        PaymentTempInfoVO vo = JsonUtil.getJsonToBean(entity, PaymentTempInfoVO.class);
        return ActionResult.success(vo);
    }

    /**
     * 退款
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @Log(title = "临时收费退款", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id, @RequestBody @Valid PaymentTempRefundForm form)
        throws DataException {
        boolean succ = paymentTempService.refund(id, form);
        if (succ) {
            return ActionResult.success("退还费用成功");
        } else {
            return ActionResult.fail("退还费用失败，数据不存在");
        }
    }
}
