package com.zhaoxinms.base.util;

public class UploaderUtil {

    /**
     * 附件名称处理
     * @param url
     * @param fileName
     * @return
     */
    public static String uploaderFile(String url, String fileName) {
        if (url == null) {
            url = "/Download?encryption=";
        }
        String name = DesUtil.aesEncode(fileName);
        return url + name;
    }

    /**
     * 附件名称处理
     * @param fileName
     * @return
     */
    public static String uploaderFile(String fileName) {
        return uploaderFile(null, fileName);
    }
}
