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
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
import com.zhaoxinms.payment.entity.vo.PaymentMethodVo;
import com.zhaoxinms.payment.entity.bo.PaymentMethodBo;
import com.zhaoxinms.payment.entity.pagination.PaymentMethodPagination;
import com.zhaoxinms.payment.service.IPaymentMethodService;
import com.zhaoxinms.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;
/**
 * 支付方式Controller
 * 
 * @author fanhuibin
 * @date 2022-04-15
 */
@RestController
@RequestMapping("/payment/paymentMethod")
public class PaymentMethodController
{

	@Autowired
    private UserProvider userProvider;

    @Autowired
    private IPaymentMethodService paymentMethodService;

    /**
     * 查询支付方式列表
     */
    @PreAuthorize("@ss.hasPermi('payment:paymentMethod:list')")
    @GetMapping("/list")
    public ActionResult list(PaymentMethodPagination paymentMethodPagination) {
        List<PaymentMethod> list = paymentMethodService.getList(paymentMethodPagination);
        List<PaymentMethodVo> listVO = JsonUtil.getJsonToList(list, PaymentMethodVo.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(paymentMethodPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 获取支付方式详细信息
     */
    @PreAuthorize("@ss.hasPermi('payment:paymentMethod:query')")
    @GetMapping(value = "/{id}")
 	public ActionResult<PaymentMethodVo> info(@PathVariable("id") String id) {
        PaymentMethod entity = paymentMethodService.getInfo(id);
        PaymentMethodVo vo = JsonUtil.getJsonToBean(entity, PaymentMethodVo.class);
        return ActionResult.success(vo);
    }

    /**
     * 新增支付方式
     */
    @PreAuthorize("@ss.hasPermi('payment:paymentMethod:add')")
    @Log(title = "支付方式", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@Validated(AddGroup.class) @RequestBody PaymentMethodBo bo) throws DataException {
        SysUser userInfo = userProvider.get();
        PaymentMethod entity = JsonUtil.getJsonToBean(bo, PaymentMethod.class);
        paymentMethodService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 修改支付方式
     */
    @PreAuthorize("@ss.hasPermi('payment:paymentMethod:edit')")
    @Log(title = "支付方式", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id, @Validated(EditGroup.class) @RequestBody PaymentMethodBo bo)
        throws DataException {
        PaymentMethod entity = JsonUtil.getJsonToBean(bo, PaymentMethod.class);
        paymentMethodService.update(id, entity);
        return ActionResult.success("更新成功");
    }

    /**
     * 删除支付方式
     */
    @PreAuthorize("@ss.hasPermi('payment:paymentMethod:remove')")
    @Log(title = "支付方式", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
	@Transactional
    public ActionResult delete(@PathVariable("id") String id) {
        PaymentMethod entity = paymentMethodService.getInfo(id);
        if (entity != null) {
            paymentMethodService.delete(entity);
        }
        return ActionResult.success("删除成功");
    }
}
