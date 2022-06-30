/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms website：http://pms.zhaoxinms.com wx： zhaoxinms
 *
 */
package com.zhaoxinms.payment.controller;

import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.zhaoxinms.common.annotation.RepeatSubmit;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.core.validate.AddGroup;
import com.zhaoxinms.common.core.validate.EditGroup;
import com.zhaoxinms.common.core.validate.QueryGroup;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.common.utils.poi.ExcelUtil;
import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.payment.entity.PaymentMethod;
import com.zhaoxinms.payment.entity.PaymentOrder;
import com.zhaoxinms.payment.entity.bo.PaymentOrderBo;
import com.zhaoxinms.payment.entity.pagination.PaymentMethodPagination;
import com.zhaoxinms.payment.entity.pagination.PaymentOrderPagination;
import com.zhaoxinms.payment.entity.vo.PaymentOrderVo;
import com.zhaoxinms.payment.service.IPaymentMethodService;
import com.zhaoxinms.payment.service.PaymentOrderService;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 在线支付订单Controller
 * 
 * @author cycberform
 * @date 2022-03-16
 */
@RestController
@RequestMapping("/payment/paymentOrder")
public class PaymentOrderController {

    @Autowired
    private UserProvider userProvider;
    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private IPaymentMethodService paymentMethodService;

    /**
     * 查询在线支付订单列表
     */
    @PreAuthorize("@ss.hasPermi('payment:paymentOrder:list')")
    @GetMapping("/list")
    public ActionResult list(PaymentOrderPagination paymentOrderPagination) {
        List<PaymentOrder> list = paymentOrderService.getList(paymentOrderPagination);
        List<PaymentOrderVo> listVO = JsonUtil.getJsonToList(list, PaymentOrderVo.class);

        // 封装参数
        List<PaymentMethod> methods = paymentMethodService.getList(new PaymentMethodPagination());
        Map<String, PaymentMethod> methodMap = methods.stream().collect(Collectors.toMap(PaymentMethod::getCode, PaymentMethod -> PaymentMethod));
        for (PaymentOrderVo vo : listVO) {
            if(StringUtils.isNotEmpty(vo.getWayCode())) {
                vo.setWayCode(methodMap.get(vo.getWayCode()).getName());
            }
            if (vo.getClient().equals("" + ConstantsUtil.CLIENT_WX_MINIAPP)) {
                vo.setClient("业主小程序");
            }
            if (vo.getClient().equals("" + ConstantsUtil.CLIENT_WX_PC)) {
                vo.setClient("后台添加");
            }
            if (vo.getState().equals(PaymentOrder.STATE_INIT)) {
                vo.setStateName("待付款");
            }
            if (vo.getState().equals(PaymentOrder.STATE_SUCCESS)) {
                vo.setStateName("支付成功");
            }
            if(vo.getRefundState().equals(PaymentOrder.REFUND_STATE_NONE)) {
                vo.setRefundStateName("未退款");
            }
            if(vo.getRefundState().equals(PaymentOrder.REFUND_STATE_PART)) {
                vo.setRefundStateName("部分退款");
            }
            if(vo.getRefundState().equals(PaymentOrder.REFUND_STATE_ALL)) {
                vo.setRefundStateName("全额退款");
            }
        }

        PageListVO vo = new PageListVO();
        vo.setList(listVO);

        PaginationVO page = JsonUtil.getJsonToBean(paymentOrderPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 获取在线支付订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('payment:paymentOrder:query')")
    @GetMapping(value = "/{id}")
    public ActionResult<PaymentOrderVo> info(@PathVariable("id") String id) {
        PaymentOrder entity = paymentOrderService.getInfo(id);
        PaymentOrderVo vo = JsonUtil.getJsonToBean(entity, PaymentOrderVo.class);
        return ActionResult.success(vo);
    }
}
