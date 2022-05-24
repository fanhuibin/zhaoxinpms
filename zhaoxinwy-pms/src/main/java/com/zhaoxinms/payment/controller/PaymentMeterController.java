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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.payment.entity.PaymentMeterEntity;
import com.zhaoxinms.payment.model.paymentmeter.PaymentMeterCrForm;
import com.zhaoxinms.payment.model.paymentmeter.PaymentMeterInfoVO;
import com.zhaoxinms.payment.model.paymentmeter.PaymentMeterListVO;
import com.zhaoxinms.payment.model.paymentmeter.PaymentMeterPagination;
import com.zhaoxinms.payment.model.paymentmeter.PaymentMeterUpForm;
import com.zhaoxinms.payment.service.PaymentMeterService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "抄表数据管理", description = "")
@RequestMapping("/payment/PaymentMeter")
public class PaymentMeterController {
    @Autowired
    private UserProvider userProvider;

    @Autowired
    private PaymentMeterService paymentMeterService;

    /**
     * 列表
     * 
     * @param paymentMeterPagination
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @GetMapping
    public ActionResult list(PaymentMeterPagination paymentMeterPagination) throws IOException {
        List<PaymentMeterEntity> list = paymentMeterService.getList(paymentMeterPagination);
        List<PaymentMeterListVO> listVO = JsonUtil.getJsonToList(list, PaymentMeterListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(paymentMeterPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 创建
     *
     * @param paymentMeterCrForm
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "创建抄表数据", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@RequestBody @Valid PaymentMeterCrForm paymentMeterCrForm) throws DataException {
        SysUser userInfo = userProvider.get();
        PaymentMeterEntity entity = JsonUtil.getJsonToBean(paymentMeterCrForm, PaymentMeterEntity.class);
        paymentMeterService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 信息
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @GetMapping("/{id}")
    public ActionResult<PaymentMeterInfoVO> info(@PathVariable("id") String id) {
        PaymentMeterEntity entity = paymentMeterService.getInfo(id);
        PaymentMeterInfoVO vo = JsonUtil.getJsonToBean(entity, PaymentMeterInfoVO.class);
        return ActionResult.success(vo);
    }

    /**
     * 更新
     * 
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "更新抄表数据", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id, @RequestBody @Valid PaymentMeterUpForm paymentMeterUpForm)
        throws DataException {
        paymentMeterService.updateById(id, paymentMeterUpForm);
        return ActionResult.success("更新成功");
    }

    /**
     * 删除
     * 
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "删除抄表数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @Transactional
    public ActionResult delete(@PathVariable("id") String id) {
        PaymentMeterEntity entity = paymentMeterService.getInfo(id);
        if (entity != null) {
            paymentMeterService.delete(entity);
        }
        return ActionResult.success("删除成功");
    }
}
