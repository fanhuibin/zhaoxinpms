package com.zhaoxinms.base.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhaoxinms.base.entity.DictionaryDataEntity;
import com.zhaoxinms.base.service.DictionaryDataService;

@Component
public class DynDicUtil {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CacheKeyUtil cacheKeyUtil;
    @Autowired
    private DictionaryDataService dictionaryDataService;


    public final String regEx="[\\[\\]\"]";

    /**
     * 获取数据字典数据
     * @param feild
     * @return
     */
    public  String getDicName(String feild){
        if(redisUtil.exists(cacheKeyUtil.getDictionary()+feild)){
            return redisUtil.getString(cacheKeyUtil.getDictionary()+feild).toString();
        }
        //去除中括号以及双引号
        feild=feild.replaceAll(regEx,"");
        //判断多选框
        String[] feilds=feild.split(",");
        if(feilds.length>1){
            StringBuilder feildsValue=new StringBuilder();
            DictionaryDataEntity dictionaryDataEntity;
            for(String feil:feilds){
                dictionaryDataEntity=dictionaryDataService.getInfo(feil);
                if(dictionaryDataEntity!=null){
                    feildsValue.append(dictionaryDataEntity.getFullName()+"/");
                }
            }
            String finalValue= feildsValue.substring(0,feildsValue.length()-1);
            redisUtil = SpringContext.getBean(RedisUtil.class);
            redisUtil.insert(cacheKeyUtil.getDictionary()+feild,finalValue,20);
            return finalValue;
        }
        DictionaryDataEntity dictionaryDataentity=dictionaryDataService.getInfo(feild);
        if(dictionaryDataentity!=null){
            redisUtil = SpringContext.getBean(RedisUtil.class);
            redisUtil.insert(cacheKeyUtil.getDictionary()+feild,dictionaryDataentity.getFullName(),20);
            return dictionaryDataentity.getFullName();
        }
        return feild;
    }
}
