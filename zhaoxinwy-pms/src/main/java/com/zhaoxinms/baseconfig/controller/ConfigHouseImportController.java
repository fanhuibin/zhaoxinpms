package com.zhaoxinms.baseconfig.controller;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
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
import com.zhaoxinms.baseconfig.model.house.HouseCrForm;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.enums.BusinessType;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "商铺导入" , description = "商铺导入")
@RequestMapping("/baseconfig/HouseImport")
public class ConfigHouseImportController {
    @Autowired
    private ConfigHouseService houseService;
    @Autowired
    private ConfigValueUtil configValueUtil;
    @Autowired
    private FileService fileService;

     @GetMapping("/Template")
     public void info(){
    	 String filePath = configValueUtil.getTemplateFilePath() + "house_import_template.xlsx";
    	 //1.获取excel模板
         TemplateExportParams params = new TemplateExportParams(filePath);
         
         Map<String, Object> map = new HashMap<String, Object>(100);
         //2.执行excel导出
         Workbook workbook = ExcelExportUtil.exportExcel(params, map);

         //3.下载文件
	     String fileName = "商铺导入模板.xlsx";
	     try {
	            HttpServletResponse response = ServletUtil.getResponse();
	            response.setCharacterEncoding("UTF-8");
	            response.setHeader("content-Type", "application/vnd.ms-excel");
	            response.setHeader("Content-Disposition",
	                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
	            response.setHeader("download-filename",  URLEncoder.encode(fileName, "UTF-8"));
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
             //上传文件
             FileUtil.upFile(file, filePath, fileName);
             DownloadVO vo = DownloadVO.builder().build();
             vo.setName(fileName);
             return ActionResult.success(vo);
         } else {
             return ActionResult.fail("选择文件不符合导入");
         }

     }

     /**
      * 导入
      *
      * @return
      */
     @PreAuthorize("@ss.hasRole('manager')")
     @Log(title = "商铺导入", businessType = BusinessType.IMPORT)
     @GetMapping("/Import")
     @Transactional
     public ActionResult importPreview(String fileName) {
         String filePath = fileService.getFilePath(FileTypeEnum.TEMPORARY);
         File temporary = new File(filePath + fileName);
         //得到数据
         List<HouseCrForm> houseList = ExcelUtil.importExcel(temporary, 1, 1, HouseCrForm.class);
         int num = houseService.importHouse(houseList);
         Map<String,String> map = new HashMap<String,String>();
    	 map.put("num",""+ num);
         return ActionResult.success(map);
     }
}
