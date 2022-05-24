package com.zhaoxinms.base.controller;

import java.io.File;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.service.FileService;
import com.zhaoxinms.base.util.ConfigValueUtil;
import com.zhaoxinms.base.util.DateUtil;
import com.zhaoxinms.base.util.DesUtil;
import com.zhaoxinms.base.util.DownUtil;
import com.zhaoxinms.base.util.FileUtil;
import com.zhaoxinms.base.util.OptimizeUtil;
import com.zhaoxinms.base.util.RandomUtil;
import com.zhaoxinms.base.util.RedisUtil;
import com.zhaoxinms.base.util.ServletUtil;
import com.zhaoxinms.base.util.StringUtil;
import com.zhaoxinms.base.util.UpUtil;
import com.zhaoxinms.base.util.UploaderUtil;
import com.zhaoxinms.base.util.UploaderVO;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.DownloadVO;
import com.zhaoxinms.common.core.domain.entity.SysUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "文件上传下载", value = "file")
@RestController
public class DownloadController {

    @Autowired
    private ConfigValueUtil configValueUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private FileService fileService;


    /**
     * 上传文件/图片
     *
     * @return
     */
    @ApiOperation("上传文件/图片")
    @PostMapping("/Uploader/{type}")
    public ActionResult uploader(@PathVariable("type") String type, MultipartFile file) {
        String fileType = UpUtil.getFileType(file);
        if (!OptimizeUtil.fileType(configValueUtil.getAllowUploadFileType(), fileType)) {
            return ActionResult.fail("上传失败，文件格式不允许上传");
        }
        if (OptimizeUtil.fileSize(file.getSize(),1024000)) {
            return ActionResult.fail("上传失败，文件大小超过1M");
        }
        String fileName = DateUtil.dateNow("yyyyMMdd") + "_" + RandomUtil.uuId() + "." + fileType;
        UploaderVO vo = UploaderVO.builder().name(fileName).build();
        String filePath = fileService.getFilePath(type);
        FileUtil.upFile(file, filePath, fileName);
        return ActionResult.success(vo);
    }

    /**
     * 获取下载文件链接
     *
     * @return
     */
    @ApiOperation("获取下载文件链接")
    @GetMapping("/Download/{type}/{fileName}")
    public ActionResult downloadUrl(@PathVariable("type") String type, @PathVariable("fileName") String fileName) {
        SysUser userInfo = userProvider.get();
        String filePath = fileService.getFilePath(type) + fileName;
        if (FileUtil.fileIsFile(filePath)) {
            DownloadVO vo = DownloadVO.builder().name(fileName).url(UploaderUtil.uploaderFile(userInfo.getUserId() + "#" + fileName + "#" + type)).build();
            return ActionResult.success(vo);
        }
        return ActionResult.fail("文件不存在");
    }

    /**
     * 下载文件链接
     *
     * @return
     */
    @ApiOperation("下载文件链接")
    @GetMapping("/Download")
    public void downloadFile() throws DataException {
        HttpServletRequest request = ServletUtil.getRequest();
        String reqJson = request.getParameter("encryption");
        String fileNameAll = DesUtil.aesDecode(reqJson);
        if (!StringUtil.isEmpty(fileNameAll)) {
            String[] data = fileNameAll.split("#");
            String token = data.length > 0 ? data[0] : "";
            if (redisUtil.exists(token)) {
                String fileName = data.length > 1 ? data[1] : "";
                String type = data.length > 2 ? data[2] : "";
                String filePath = fileService.getFilePath(type.toLowerCase()) + fileName;
//                if (FileUtil.fileIsFile(filePath)) {
                DownUtil.dowloadFile(filePath, fileName);
//                }
            } else {
                throw new DataException("token验证失败");
            }
        }
    }

    /**
     * 下载文件链接
     *
     * @return
     */
    @ApiOperation("下载模板文件链接")
    @GetMapping("/DownloadModel")
    public void downloadModel() {
        HttpServletRequest request = ServletUtil.getRequest();
        String reqJson = request.getParameter("encryption");
        String fileNameAll = DesUtil.aesDecode(reqJson);
        if (!StringUtil.isEmpty(fileNameAll)) {
            String token = fileNameAll.split("#")[0];
            if (redisUtil.exists(token)) {
                String fileName = fileNameAll.split("#")[1];
                String filePath = configValueUtil.getTemplateFilePath() + fileName;
//                if (FileUtil.fileIsFile(filePath)) {
                System.out.println(Charset.defaultCharset() + "路径2是" + filePath);
                DownUtil.dowloadFile(filePath, fileName);
//                }
            }
        }
    }


    /**
     * 获取图片
     *
     * @param fileName
     * @param type
     * @return
     */
    @ApiOperation("获取图片")
    @GetMapping("/Image/{type}/{fileName}")
    public void downLoadImg(@PathVariable("type") String type, @PathVariable("fileName") String fileName) {
        String filePath = fileService.getFilePath(type) + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            DownUtil.dowloadFile(filePath);
        }
    }

    /**
     * 通过type获取路径
     *
     * @param type 类型
     * @return
     */
    @GetMapping("/getPath/{type}")
    public String getPath(@PathVariable("type") String type) {
        return fileService.getFilePath(type);
    }

}
