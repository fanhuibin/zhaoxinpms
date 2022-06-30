package com.zhaoxinms.payment.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.entity.PaymentDepositEntity;
import com.zhaoxinms.payment.entity.PaymentPayLogEntity;
import com.zhaoxinms.payment.entity.PaymentPreEntity;
import com.zhaoxinms.payment.entity.PaymentTempEntity;
import com.zhaoxinms.payment.model.paymentpre.PaymentPrePagination;
import com.zhaoxinms.payment.service.PaymentBillService;
import com.zhaoxinms.payment.service.PaymentDepositService;
import com.zhaoxinms.payment.service.PaymentPayLogService;
import com.zhaoxinms.payment.service.PaymentPreService;
import com.zhaoxinms.payment.service.PaymentTempService;
import com.zhaoxinms.system.service.ISysUserService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "收费打印功能", description = "")
@RequestMapping("/print/printData")
public class PayLogPrintController {
    @Autowired
    private PaymentPayLogService paymentPayLogService;
    @Autowired
    private PaymentBillService paymentBillService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private PaymentTempService paymentTempService;
    @Autowired
    private PaymentDepositService paymentDepositService;
    @Autowired
    private PaymentPreService paymentPreService;
     
    
    public static final String COMPANY_NAME = "北京XX物业管理有限公司";
    
    /**
     * 临时收费打印
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @GetMapping("tempPrint")
    public ActionResult tempPrint(@RequestParam(name = "payNo") String payNo) throws IOException {
        PaymentPayLogEntity log = paymentPayLogService.getByPayNo(payNo);
        SysUser user = sysUserService.selectUserById(Long.valueOf(log.getCreatorUserId()));
        PaymentTempEntity temp = paymentTempService.getByPayNo(log.getPayNo());
        Map map = new HashMap();
        map.put("company", COMPANY_NAME);
        map.put("payLog", log);
        map.put("creator", user);
        map.put("paymentTemp", temp);
        return ActionResult.success(map);
    }
    
    /**
     * 物业收费单打印
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @GetMapping("billPrint")
    public ActionResult billPrint(@RequestParam(name = "payLogId") String payLogId) {
        PaymentPayLogEntity log = paymentPayLogService.getById(payLogId);
        SysUser user = sysUserService.selectUserById(Long.valueOf(log.getCreatorUserId()));
        List<PaymentBillEntity> bills = paymentBillService.getBillsByLogId(payLogId);
        
        Map map = new HashMap();
        map.put("company", COMPANY_NAME);
        map.put("payLog", log);
        map.put("creator", user);
        map.put("bills", bills);
        return ActionResult.success(map);
    }
    
    /**
     * 押金收费打印
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @GetMapping("depositPrint")
    public ActionResult depositPrint(@RequestParam(name = "payNo") String payNo) throws IOException {
        PaymentPayLogEntity log = paymentPayLogService.getByPayNo(payNo);
        SysUser user = sysUserService.selectUserById(Long.valueOf(log.getCreatorUserId()));
        PaymentDepositEntity deposit = paymentDepositService.getByPayNo(payNo);
        Map map = new HashMap();
        map.put("company", COMPANY_NAME);
        map.put("payLog", log);
        map.put("creator", user);
        map.put("paymentDeposit", deposit);
        return ActionResult.success(map);
    }
    
    /**
     * 押金收费退款打印
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @GetMapping("depositRefundPrint")
    public ActionResult depositRefundPrint(@RequestParam(name = "refundNo") String refundNo) throws IOException {
        PaymentPayLogEntity log = paymentPayLogService.getByPayNo(refundNo);
        SysUser user = sysUserService.selectUserById(Long.valueOf(log.getCreatorUserId()));
        PaymentDepositEntity deposit = paymentDepositService.getByRefundNo(refundNo);
        Map map = new HashMap();
        map.put("company", COMPANY_NAME);
        map.put("payLog", log);
        map.put("creator", user);
        map.put("paymentDeposit", deposit);
        return ActionResult.success(map);
    }
    
    /**
     * 预存收款收据
     */
    @PreAuthorize("@ss.hasRole('payee')")
    @GetMapping("prePayPrint")
    public ActionResult prePayPrint(@RequestParam(name = "payNo") String payNo) throws IOException {
        
        PaymentPreEntity pre = paymentPreService.getByPayNo(payNo);
        SysUser user = sysUserService.selectUserById(Long.valueOf(pre.getOperateUser()));
        Map map = new HashMap();
        map.put("company", COMPANY_NAME);
        map.put("creator", user);
        map.put("paymentPre", pre);
        return ActionResult.success(map);
    }
}
