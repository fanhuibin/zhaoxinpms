package com.zhaoxinms.base.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConfigurationProperties(prefix = "config")
public class ConfigValueUtil {

    /**
     * 环境路径
     */
    @Value("${ruoyi.profile}")
    private String path;

    /**
     * 临时文件存储路径
     */
    @Value("${ruoyi.TemporaryFilePath}")
    private String temporaryFilePath;
    /**
     * 系统文件存储路径
     */
    @Value("${ruoyi.SystemFilePath}")
    private String systemFilePath;
    /**
     * 文件模板存储路径
     */
    @Value("${ruoyi.TemplateFilePath}")
    private String templateFilePath;

    /**
     * 允许上传文件类型
     */
    @Value("${ruoyi.AllowUploadFileType}")
    private String allowUploadFileType;
    /**
     * 允许图片类型
     */
    @Value("${ruoyi.AllowUploadImageType}")
    private String allowUploadImageType;

    public String getTemporaryFilePath() {
        return path + File.separator + temporaryFilePath + File.separator;
    }

    public String getSystemFilePath() {
        return  path + File.separator + systemFilePath + File.separator;
    }

    public String getTemplateFilePath() {
        return  path + File.separator + templateFilePath + File.separator;
    }

    public String getAllowUploadFileType() {
         return allowUploadFileType;
    }
    
    public String getAllowUploadImageType() {
        return allowUploadImageType;
   }
}
