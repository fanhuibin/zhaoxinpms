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
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.payment.entity.PaymentMeterIndexEntity;
import com.zhaoxinms.payment.model.paymentmeterindex.PaymentMeterIndexCrForm;
import com.zhaoxinms.payment.model.paymentmeterindex.PaymentMeterIndexInfoVO;
import com.zhaoxinms.payment.model.paymentmeterindex.PaymentMeterIndexListVO;
import com.zhaoxinms.payment.model.paymentmeterindex.PaymentMeterIndexPagination;
import com.zhaoxinms.payment.model.paymentmeterindex.PaymentMeterIndexUpForm;
import com.zhaoxinms.payment.service.PaymentMeterIndexService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "抄表数据")
@RequestMapping("/payment/PaymentMeterIndex")
public class PaymentMeterIndexController {
    @Autowired
    private UserProvider userProvider;

    @Autowired
    private PaymentMeterIndexService paymentMeterIndexService;

    /**
     * 列表
     *
     * @param paymentMeterIndexPagination
     * @return
     */
    @GetMapping
    public ActionResult list(PaymentMeterIndexPagination paymentMeterIndexPagination) throws IOException {
        List<PaymentMeterIndexEntity> list = paymentMeterIndexService.getList(paymentMeterIndexPagination);
        List<PaymentMeterIndexListVO> listVO = JsonUtil.getJsonToList(list, PaymentMeterIndexListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(paymentMeterIndexPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 创建
     *
     * @param paymentMeterIndexCrForm
     * @return
     */
    @PostMapping
    @Transactional
    public ActionResult create(@RequestBody @Valid PaymentMeterIndexCrForm paymentMeterIndexCrForm)
        throws DataException {
        SysUser userInfo = userProvider.get();
        PaymentMeterIndexEntity entity = JsonUtil.getJsonToBean(paymentMeterIndexCrForm, PaymentMeterIndexEntity.class);
        entity.setId(RandomUtil.uuId());
        paymentMeterIndexService.createOrUpdateIndex(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ActionResult<PaymentMeterIndexInfoVO> info(@PathVariable("id") String id) {
        PaymentMeterIndexEntity entity = paymentMeterIndexService.getInfo(id);
        PaymentMeterIndexInfoVO vo = JsonUtil.getJsonToBean(entity, PaymentMeterIndexInfoVO.class);
        return ActionResult.success(vo);
    }

    /**
     * 更新
     *
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id,
        @RequestBody @Valid PaymentMeterIndexUpForm paymentMeterIndexUpForm) throws DataException {
        PaymentMeterIndexEntity entity = paymentMeterIndexService.getInfo(id);
        if (entity != null) {
            paymentMeterIndexService.delete(entity);
            SysUser userInfo = userProvider.get();
            entity = JsonUtil.getJsonToBean(paymentMeterIndexUpForm, PaymentMeterIndexEntity.class);
            entity.setId(id);
            paymentMeterIndexService.createOrUpdateIndex(entity);
            return ActionResult.success("更新成功");
        } else {
            return ActionResult.fail("更新失败，数据不存在");
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ActionResult delete(@PathVariable("id") String id) {
        PaymentMeterIndexEntity entity = paymentMeterIndexService.getInfo(id);
        if (entity != null) {
            paymentMeterIndexService.delete(entity);
        }
        return ActionResult.success("删除成功");
    }

}
