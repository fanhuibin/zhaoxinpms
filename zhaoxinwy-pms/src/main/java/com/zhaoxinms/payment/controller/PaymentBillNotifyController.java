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
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.ConfigValueUtil;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.ServletUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.entity.PaymentMeterEntity;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillInfoVO;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillListVO;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillPagination;
import com.zhaoxinms.payment.model.paymentbill.PaymentBillUpForm;
import com.zhaoxinms.payment.model.paymentmeter.PaymentMeterPagination;
import com.zhaoxinms.payment.service.PaymentBillService;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "费用催收", description = "")
@RequestMapping("/payment/PaymentBillNotify")
public class PaymentBillNotifyController {
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private PaymentBillService paymentBillService;
    @Autowired
    private ConfigValueUtil configValueUtil;

    
    /**
     * 所有可催费的bill
     *
     * @param paymentBillPagination
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @GetMapping("unpaiedAndPayingList")
    public ActionResult unpaiedAndPayingList(PaymentBillPagination paymentBillPagination) throws IOException {
        List<PaymentBillEntity> list = paymentBillService.getUnpaiedAndPayingListByResourceLike(paymentBillPagination);
        List<PaymentBillListVO> listVO = JsonUtil.getJsonToList(list, PaymentBillListVO.class);
        return ActionResult.success(listVO);
    }
    
    /**
     * 所有可催费的bill
     *
     * @param paymentBillPagination
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @GetMapping("print")
    public ActionResult print(PaymentBillPagination paymentBillPagination) throws IOException {
        List<PaymentBillEntity> list = paymentBillService.getUnpaiedAndPayingListByResourceLike(paymentBillPagination);
        List<PaymentBillListVO> listVO = JsonUtil.getJsonToList(list, PaymentBillListVO.class);
        
        Map map = new HashMap();
        map.put("company", PayLogPrintController.COMPANY_NAME);
        map.put("bills", listVO);
        return ActionResult.success(map);
    }
    
    /**
     * 下载bill
     *
     * @param paymentBillPagination
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @GetMapping("download")
    public void downloadBill(PaymentBillPagination paymentBillPagination) throws IOException {
        List<PaymentBillEntity> list = paymentBillService.getUnpaiedAndPayingListByResourceLike(paymentBillPagination);
        List<PaymentBillListVO> listVO = JsonUtil.getJsonToList(list, PaymentBillListVO.class);
        String filePath = configValueUtil.getTemplateFilePath() + "fee_notify.xlsx";
        
        // 1.获取excel模板
        TemplateExportParams params = new TemplateExportParams(filePath);
        Map<String, Object> map = new HashMap<String, Object>(100);
        map.put("indexList", listVO);

        // 2.执行excel导出
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);

        // 3.下载文件
        String fileName = "缴费通知单.xlsx";
        try {
            HttpServletResponse response = ServletUtil.getResponse();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("download-filename",  URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}
