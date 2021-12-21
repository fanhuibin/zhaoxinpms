package com.zhaoxinms.statistics.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.mapper.ConfigHouseMapper;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.baseconfig.service.impl.ConfigFeeItemServiceImpl;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillListVO;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPagination;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractFeeListVO;
import com.zhaoxinms.payment.service.PaymentBillService;
import com.zhaoxinms.payment.service.PaymentContractFeeService;
import com.zhaoxinms.statistics.mapper.PaymentStatisticsMapper;
import com.zhaoxinms.statistics.service.DashboardService;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.DateUtils;
import com.zhaoxinms.util.InputCheckUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "欠费统计", description = "")
@RequestMapping("/statistics/paymentBill")
public class PaymentBillStatisticsController {

    @Autowired
    private PaymentBillService paymentBillService;

    // 欠费明细表
    @PreAuthorize("@ss.hasPermi('statistics:paymentBill:overdue')")
    @GetMapping("/overdue")
    public ActionResult list(PaymentBillPagination paymentBillPagination) {
        QueryWrapper<PaymentBillEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getEnabledMark, 1));
        queryWrapper.lambda().and(t -> t.lt(PaymentBillEntity::getDeadline, new Date()));
        queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getPayState, '0'));
        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getResourceName())) {
            queryWrapper.lambda()
                .and(t -> t.like(PaymentBillEntity::getResourceName, paymentBillPagination.getResourceName()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getResourceId())) {
            queryWrapper.lambda()
                .and(t -> t.eq(PaymentBillEntity::getResourceId, paymentBillPagination.getResourceId()));
        }

        if (InputCheckUtil.isNotEmpty(paymentBillPagination.getFeeItemId())) {
            queryWrapper.lambda().and(t -> t.eq(PaymentBillEntity::getFeeItemId, paymentBillPagination.getFeeItemId()));
        }
        queryWrapper.lambda().orderByDesc(PaymentBillEntity::getDeadline);

        Page<PaymentBillEntity> page =
            new Page<>(paymentBillPagination.getCurrentPage(), paymentBillPagination.getPageSize());
        IPage<PaymentBillEntity> userIPage = paymentBillService.page(page, queryWrapper);
        List<PaymentBillEntity> list = paymentBillPagination.setData(userIPage.getRecords(), userIPage.getTotal());

        List<PaymentBillListVO> listVO = JsonUtil.getJsonToList(list, PaymentBillListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO pageVO = JsonUtil.getJsonToBean(paymentBillPagination, PaginationVO.class);
        vo.setPagination(pageVO);
        return ActionResult.success(vo);
    }
}
