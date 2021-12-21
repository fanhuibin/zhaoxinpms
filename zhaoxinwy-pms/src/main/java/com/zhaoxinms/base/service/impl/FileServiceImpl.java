package com.zhaoxinms.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaoxinms.base.service.FileService;
import com.zhaoxinms.base.util.ConfigValueUtil;
import com.zhaoxinms.base.util.FileTypeEnum;

@Service("fileService")
public class FileServiceImpl implements FileService{
	@Autowired
    private ConfigValueUtil configValueUtil;
	   /**
     * 通过fileType获取文件夹名称
     *
     * @param fileType 文件类型
     * @return
     */
    public String getFilePath(String fileType) {
        String filePath = null;
        //判断是那种类型得到相应的文件夹
        switch (fileType){
            //临时文件存储路径
            case FileTypeEnum.TEMPORARY:
                filePath = configValueUtil.getTemporaryFilePath();
                break;
            //允许上传文件类型
            case FileTypeEnum.ALLOWUPLOADFILETYPE:
                filePath = configValueUtil.getAllowUploadFileType();
                break;
            //文件模板存储路径
            case FileTypeEnum.TEMPLATEFILE:
                filePath = configValueUtil.getTemplateFilePath();
                break;
            default:
                break;
        }
        return filePath;
    }
}
