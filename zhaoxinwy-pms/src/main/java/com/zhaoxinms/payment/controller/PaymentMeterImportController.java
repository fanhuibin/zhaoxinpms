/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms website：http://pms.zhaoxinms.com wx： zhaoxinms
 *
 */
package com.zhaoxinms.payment.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.service.FileService;
import com.zhaoxinms.base.util.ConfigValueUtil;
import com.zhaoxinms.base.util.ExcelUtil;
import com.zhaoxinms.base.util.FileTypeEnum;
import com.zhaoxinms.base.util.FileUtil;
import com.zhaoxinms.base.util.RandomUtil;
import com.zhaoxinms.base.util.ServletUtil;
import com.zhaoxinms.base.util.UpUtil;
import com.zhaoxinms.base.vo.DownloadVO;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.payment.entity.PaymentMeterEntity;
import com.zhaoxinms.payment.entity.PaymentMeterIndexEntity;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractFeeListVO;
import com.zhaoxinms.payment.model.paymentmeter.PaymentMeterImport;
import com.zhaoxinms.payment.model.paymentmeter.PaymentMeterPagination;
import com.zhaoxinms.payment.model.paymentmeterindex.PaymentMeterIndexPagination;
import com.zhaoxinms.payment.service.PaymentContractFeeService;
import com.zhaoxinms.payment.service.PaymentMeterIndexService;
import com.zhaoxinms.payment.service.PaymentMeterService;
import com.zhaoxinms.util.DateUtils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "抄表数据导入", description = "")
@RequestMapping("/payment/PaymentMeterImport")
public class PaymentMeterImportController {
    @Autowired
    private ConfigValueUtil configValueUtil;
    @Autowired
    private FileService fileService;
    @Autowired
    private PaymentMeterService paymentMeterService;
    @Autowired
    private PaymentMeterIndexService paymentMeterIndexService;
    @Autowired
    private ConfigFeeItemService configFeeItemService;
    @Autowired
    private PaymentContractFeeService paymentContractFeeService;

    @GetMapping("/Template")
    public void info(@RequestParam("feeId") String feeId) {
        ConfigFeeItemEntity fee = configFeeItemService.getById(feeId);
        String filePath = configValueUtil.getTemplateFilePath() + "meter_import_template.xlsx";
        // 1.获取excel模板
        TemplateExportParams params = new TemplateExportParams(filePath);

        // 2.查询上次的抄表数据
        List<PaymentMeterIndexEntity> indexList =
            paymentMeterIndexService.getTypeList(new PaymentMeterIndexPagination(), "1");

        // 3.查询绑定了该费用的商铺
        List<PaymentContractFeeListVO> list = paymentContractFeeService.getByFeeId(feeId);

        // 3.整合商铺和抄表数据
        List<PaymentMeterImport> meters = new ArrayList<PaymentMeterImport>();
        for (PaymentContractFeeListVO contractFee : list) {
            PaymentMeterImport meter = new PaymentMeterImport();
            meter.setBlock(contractFee.getBlockCode());
            meter.setCode(contractFee.getResourceCode());
            meter.setLastIndex("0");
            meter.setLastIndexDate("");
            for (PaymentMeterIndexEntity m : indexList) {
                if (m.getResourceName().equals(contractFee.getResourceName())) {
                    meter.setLastIndex(m.getCurrentIndex());
                    meter.setLastIndexDate(DateUtils.formatDate(m.getCurrentIndexDate(), "yyyy-MM-dd"));
                }
            }
            meter.setCurrentIndexDate("");
            meter.setCurrentIndex("");
            meter.setFeeItemName(fee.getName());
            meter.setMultiple("1");
            meter.setLoss("0");
            meters.add(meter);
        }

        Map<String, Object> map = new HashMap<String, Object>(100);
        map.put("indexList", meters);

        // 2.执行excel导出
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);

        // 3.下载文件
        String fileName = "抄表数据导入模板.xlsx";
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

    @GetMapping("/Download")
    public void download() {
        String filePath = configValueUtil.getTemplateFilePath() + "meter_import_template.xlsx";
        // 1.获取excel模板
        TemplateExportParams params = new TemplateExportParams(filePath);

        List<PaymentMeterEntity> list = paymentMeterService.getTypeList(new PaymentMeterPagination(), "1");
        Map<String, Object> map = new HashMap<String, Object>(100);
        map.put("indexList", list);

        // 2.执行excel导出
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);

        // 3.下载文件
        String fileName = "抄表数据导出.xlsx";
        try {
            HttpServletResponse response = ServletUtil.getResponse();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件(excel)
     *
     * @return
     */
    @ApiOperation("上传文件")
    @PostMapping("/Uploader")
    @Transactional
    public ActionResult uploader() {
        List<MultipartFile> list = UpUtil.getFileAll();
        MultipartFile file = list.get(0);
        if (file.getOriginalFilename().contains(".xlsx")) {
            String filePath = fileService.getFilePath(FileTypeEnum.TEMPORARY);
            String fileName = RandomUtil.uuId() + "." + UpUtil.getFileType(file);
            // 上传文件
            FileUtil.upFile(file, filePath, fileName);
            DownloadVO vo = DownloadVO.builder().build();
            vo.setName(fileName);
            return ActionResult.success(vo);
        } else {
            return ActionResult.fail("选择文件不符合导入");
        }

    }

    /**
     * 数据导入
     *
     * @return
     */
    @PreAuthorize("@ss.hasRole('manager')")
    @Log(title = "抄表数据导入", businessType = BusinessType.IMPORT)
    @GetMapping("/Import")
    @Transactional
    public ActionResult importPreview(String fileName) {
        String filePath = fileService.getFilePath(FileTypeEnum.TEMPORARY);
        File temporary = new File(filePath + fileName);
        // 得到数据
        List<PaymentMeterImport> meterList = ExcelUtil.importExcel(temporary, 1, 1, PaymentMeterImport.class);
        int num = paymentMeterService.importMeter(meterList);
        Map<String, String> map = new HashMap<String, String>();
        map.put("num", "" + num);
        return ActionResult.success(map);
    }
}
