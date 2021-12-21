package com.zhaoxinms.baseconfig.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@TableName("config_fee_setting")
public class ConfigFeeSettingEntity  {
    /** 主键 */
    @TableId("ID")
    private String id;

    /** 收费项分类 */
    @TableField("FEE_ITEM_ID")
    private String feeItemId;

    /** 收费项目名 */
    @TableField("TYPE")
    private String type;

}
