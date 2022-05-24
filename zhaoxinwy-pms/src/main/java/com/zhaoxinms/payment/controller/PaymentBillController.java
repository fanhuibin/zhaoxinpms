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
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillInfoVO;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillListVO;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPagination;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillUpForm;
import com.zhaoxinms.payment.service.PaymentBillService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "收费数据管理", description = "")
@RequestMapping("/payment/PaymentBill")
public class PaymentBillController {
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private PaymentBillService paymentBillService;

    /**
     * 列表
     *
     * @param paymentBillPagination
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @GetMapping
    public ActionResult list(PaymentBillPagination paymentBillPagination) throws IOException {
        List<PaymentBillEntity> list = paymentBillService.getList(paymentBillPagination);

        List<PaymentBillListVO> listVO = JsonUtil.getJsonToList(list, PaymentBillListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(paymentBillPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 通过资源名查询其下的所有未付款缴费单
     *
     * @param paymentBillPagination
     * @return
     */
    @PreAuthorize("@ss.hasAnyRoles('payee,manager')")
    @GetMapping("/unpaied/{resourceName}")
    public ActionResult billList(@PathVariable("resourceName") String resourceName) throws IOException {
        List<PaymentBillEntity> list = paymentBillService.getUnpaiedListByResource(resourceName);
        List<PaymentBillListVO> listVO = JsonUtil.getJsonToList(list, PaymentBillListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        return ActionResult.success(vo);
    }

    /**
     * 通过资源名查询其下的所有未付款和付款中缴费单
     *
     * @param paymentBillPagination
     * @return
     */
    @PreAuthorize("@ss.hasAnyRoles('payee,manager')")
    @GetMapping("/needPay/{resourceName}")
    public ActionResult payingbillList(@PathVariable("resourceName") String resourceName) throws IOException {
        List<PaymentBillEntity> list = paymentBillService.getUnpaiedListByResource(resourceName);
        List<PaymentBillEntity> payingList = paymentBillService.getPayingListByResource(resourceName);
        List<PaymentBillListVO> listVO = JsonUtil.getJsonToList(list, PaymentBillListVO.class);
        List<PaymentBillListVO> payingListVO = JsonUtil.getJsonToList(payingList, PaymentBillListVO.class);
        for (PaymentBillListVO vo : payingListVO) {
            listVO.add(vo);
        }

        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        return ActionResult.success(vo);
    }

    /**
     * 通过资源名查询其下的所有已付款缴费单
     *
     * @param paymentBillPagination
     * @return
     */
    @PreAuthorize("@ss.hasAnyRoles('payee,manager')")
    @GetMapping("/paied/{resourceName}")
    public ActionResult paiedBillList(PaymentBillPagination paymentBillPagination, @PathVariable("resourceName") String resourceName) throws IOException {
        paymentBillPagination.setResourceName(resourceName);
        paymentBillPagination.setPayState("1");
        List<PaymentBillEntity> list = paymentBillService.getList(paymentBillPagination);
        List<PaymentBillListVO> listVO = JsonUtil.getJsonToList(list, PaymentBillListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(paymentBillPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 信息
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasAnyRoles('payee,manager')")
    @GetMapping("/{id}")
    public ActionResult<PaymentBillInfoVO> info(@PathVariable("id") String id) {
        PaymentBillEntity entity = paymentBillService.getInfo(id);
        PaymentBillInfoVO vo = JsonUtil.getJsonToBean(entity, PaymentBillInfoVO.class);
        return ActionResult.success(vo);
    }

    /**
     * 更新
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "收费数据更新", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id, @RequestBody @Valid PaymentBillUpForm paymentBillUpForm) throws DataException {
        PaymentBillEntity entity = JsonUtil.getJsonToBean(paymentBillUpForm, PaymentBillEntity.class);
        paymentBillService.update(id, entity);
        return ActionResult.success("更新收费数据成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "收费数据删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @Transactional
    public ActionResult delete(@PathVariable("id") String id) {
        PaymentBillEntity entity = paymentBillService.getInfo(id);
        if (entity != null) {
            paymentBillService.delete(entity);
        }
        return ActionResult.success("删除收费数据成功");
    }
}
