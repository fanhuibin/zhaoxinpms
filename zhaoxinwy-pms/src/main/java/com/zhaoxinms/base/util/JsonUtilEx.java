package com.zhaoxinms.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhaoxinms.base.exception.DataException;

public class JsonUtilEx {


    /**
     * 功能描述：把java对象转换成JSON数据,时间格式化
     * @param object java对象
     * @return JSON数据
     */
    public static String getObjectToStringDateFormat(Object object,String dateFormat) {
        return JSON.toJSONStringWithDateFormat(object, dateFormat,SerializerFeature.WriteMapNullValue);
    }

    /**
     * 功能描述：把java对象转换成JSON数据
     * @param object java对象
     * @return JSON数据
     */
    public static String getObjectToString(Object object) {
        return JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 功能描述：把JSON数据转换成指定的java对象
     * @param dto dto对象
     * @param clazz 指定的java对象
     * @return 指定的java对象
     */
    public static <T> T getJsonToBeanEx(Object dto, Class<T> clazz) throws DataException {
        if(dto==null){
            throw new DataException("此条数据不存在");
        }
        return JSON.parseObject(getObjectToString(dto), clazz);
    }


}
