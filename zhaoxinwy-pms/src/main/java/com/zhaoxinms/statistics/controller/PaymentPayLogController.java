package com.zhaoxinms.statistics.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.util.DynDicUtil;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.payment.entity.PaymentDepositEntity;
import com.zhaoxinms.payment.entity.PaymentMethod;
import com.zhaoxinms.payment.entity.PaymentPayLogEntity;
import com.zhaoxinms.payment.entity.pagination.PaymentMethodPagination;
import com.zhaoxinms.payment.model.paymentpaylog.PaymentPayLogListVO;
import com.zhaoxinms.payment.model.paymentpaylog.PaymentPayLogPagination;
import com.zhaoxinms.payment.service.IPaymentMethodService;
import com.zhaoxinms.payment.service.PaymentPayLogService;
import com.zhaoxinms.util.ConstantsUtil;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "用户支付统计")
@RequestMapping("/statistics/PaymentPayLog")
public class PaymentPayLogController {
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private DynDicUtil dynDicUtil;
    @Autowired
    private PaymentPayLogService paymentPayLogService;
    @Autowired
    private IPaymentMethodService paymentMethodService;

    @PreAuthorize("@ss.hasPermi('statistics:paymentBill:overdue')")
    @GetMapping
    public ActionResult list(PaymentPayLogPagination paymentPayLogPagination) throws IOException {
        List<PaymentPayLogEntity> list = paymentPayLogService.getList(paymentPayLogPagination);
        // 处理id字段转名称，若无需转或者为空可删除
        List<PaymentMethod> methods = paymentMethodService.getList(new PaymentMethodPagination());
        for (PaymentPayLogEntity entity : list) {
            for(PaymentMethod method : methods) {
                if(method.getCode().equals(entity.getPayMethod())) {
                    entity.setPayMethod(method.getName());
                }
            }
            if (entity.getType().equals(ConstantsUtil.PAY_LOG_TYPE_PAY)) {
                entity.setType("支付");
            } else {
                entity.setType("退款");
            }
        }
        List<PaymentPayLogListVO> listVO = JsonUtil.getJsonToList(list, PaymentPayLogListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(paymentPayLogPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }
}
