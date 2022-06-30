package com.zhaoxinms.payment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaoxinms.payment.entity.SysSmsTemplate;

/**
 * 短信模板Mapper接口
 * 
 * @author fanhuibin
 * @date 2022-06-28
 */
public interface SysSmsTemplateMapper extends BaseMapper<SysSmsTemplate> {
    /**
     * 通过模板CODE查询消息模板
     * 
     * @param code
     *            模板CODE
     * @return List<SysMessageTemplate>
     */
    @Select("SELECT * FROM SYS_SMS_TEMPLATE WHERE TEMPLATE_CODE = #{code}")
    List<SysSmsTemplate> selectByCode(String code);
}
