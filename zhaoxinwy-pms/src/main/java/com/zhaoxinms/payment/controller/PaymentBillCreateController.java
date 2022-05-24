/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms website：http://pms.zhaoxinms.com wx： zhaoxinms
 *
 */
package com.zhaoxinms.payment.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillBatchForm;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillCrForm;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillGenerateForm;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillListVO;
import com.zhaoxinms.payment.service.PaymentBillCreateService;
import com.zhaoxinms.util.CalculationUtil;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "收费数据创建", description = "")
@RequestMapping("/payment/PaymentBillCreate")
public class PaymentBillCreateController {
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private PaymentBillCreateService paymentBillCreateService;

    /**
     * 创建
     *
     * @param paymentBillCrForm
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "收费数据创建", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@RequestBody @Valid PaymentBillCrForm paymentBillCrForm) throws DataException {
        SysUser userInfo = userProvider.get();
        PaymentBillEntity entity = JsonUtil.getJsonToBean(paymentBillCrForm, PaymentBillEntity.class);
        paymentBillCreateService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 批量创建预览
     *
     * @param paymentBillCrForm
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @PostMapping("/getBatchCreateData")
    @Transactional
    public ActionResult getBatchCreateData(@RequestBody @Valid PaymentBillBatchForm paymentBillBatchForm)
        throws DataException {
        SysUser userInfo = userProvider.get();
        List<PaymentBillEntity> bills = paymentBillCreateService.getBatchCreateData(paymentBillBatchForm);
        long num = bills.size();
        String total = "0";
        for (PaymentBillEntity bill : bills) {
            total = CalculationUtil.add(total, bill.getTotal(), 2);
        }
        List<PaymentBillListVO> listVO = JsonUtil.getJsonToList(bills, PaymentBillListVO.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("num", num);
        map.put("total", total);
        map.put("list", listVO);

        return ActionResult.success(map);
    }

    /**
     * 批量创建
     *
     * @param paymentBillCrForm
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "收费数据批量创建", businessType = BusinessType.INSERT)
    @PostMapping("/batchCreate")
    @Transactional
    public ActionResult batchCreate(@RequestBody @Valid PaymentBillBatchForm paymentBillBatchForm)
        throws DataException {
        SysUser userInfo = userProvider.get();
        paymentBillCreateService.batchCreate(paymentBillBatchForm);
        return ActionResult.success("创建数据成功");
    }

    /**
     * 批量创建预览
     *
     * @param paymentBillCrForm
     * @return
     * @throws ParseException
     */
    @PostMapping("/getGenerateData")
    @Transactional
    public ActionResult getGenerateData(@RequestBody @Valid PaymentBillGenerateForm paymentBillGenerateForm)
        throws DataException, ParseException {
        List<PaymentBillEntity> bills =
            paymentBillCreateService.getOrCreateGenerateData(paymentBillGenerateForm, false);
        long num = bills.size();
        String total = "0";
        for (PaymentBillEntity bill : bills) {
            total = CalculationUtil.add(total, bill.getTotal(), 2);
        }
        List<PaymentBillListVO> listVO = JsonUtil.getJsonToList(bills, PaymentBillListVO.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("num", num);
        map.put("total", total);
        map.put("list", listVO);

        return ActionResult.success(map);
    }

    /**
     * 批量创建
     *
     * @param paymentBillCrForm
     * @return
     * @throws ParseException
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "收费数据生成", businessType = BusinessType.INSERT)
    @PostMapping("/generate")
    @Transactional
    public ActionResult generate(@RequestBody @Valid PaymentBillGenerateForm paymentBillGenerateForm)
        throws DataException, ParseException {
        SysUser userInfo = userProvider.get();
        paymentBillCreateService.getOrCreateGenerateData(paymentBillGenerateForm, true);
        return ActionResult.success("生成数据成功");
    }

    /**
     * 预览：通过读表数据来生成收费数据
     *
     * @param paymentBillCrForm
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @PostMapping("/getMeterData")
    @Transactional
    public ActionResult getMeterData(@RequestBody @Valid PaymentBillGenerateForm paymentBillGenerateForm)
        throws DataException {
        SysUser userInfo = userProvider.get();

        List<PaymentBillEntity> bills = null;
        try {
            bills = paymentBillCreateService.getOrCreateMeterData(paymentBillGenerateForm, false);
        } catch (ParseException e) {
            throw new DataException("查询出错");
        }

        List<PaymentBillListVO> listVO = JsonUtil.getJsonToList(bills, PaymentBillListVO.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", listVO);

        return ActionResult.success(map);
    }

    /**
     * 通过读表数据来生成收费数据
     *
     * @param paymentBillCrForm
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @PostMapping("/createMeterData")
    @Transactional
    public ActionResult generateMeter(@RequestBody @Valid PaymentBillGenerateForm paymentBillGenerateForm)
        throws DataException {
        SysUser userInfo = userProvider.get();
        try {
            paymentBillCreateService.getOrCreateMeterData(paymentBillGenerateForm, true);
        } catch (ParseException e) {
            throw new DataException("创建数据出错");
        }
        return ActionResult.success("生成数据成功");
    }
}
