package com.zhaoxinms.baseconfig.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.zhaoxinms.common.core.mybatisplus.BaseEntity;

/**
 * 楼栋管理对象 config_building
 *
 * @author fanhuibin
 * @date 2022-06-22
 */
@Data
@Accessors(chain = true)
@TableName("config_building")
public class ConfigBuilding extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 商铺区域
     */
    private String block;
    /**
     * 楼栋名
     */
    private String name;
    /**
     * 门牌号
     */
    private String number;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

}
