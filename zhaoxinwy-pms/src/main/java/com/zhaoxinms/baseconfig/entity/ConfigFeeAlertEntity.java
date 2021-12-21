package com.zhaoxinms.baseconfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("config_fee_alert")
public class ConfigFeeAlertEntity  {
    /** 主键 */
    @TableId("ID")
    private String id;

    /** 收费项id */
    @TableField("FEE_ID")
    private String feeId;

    /** 天数 */
    @TableField("DAY")
    private String day;

}
