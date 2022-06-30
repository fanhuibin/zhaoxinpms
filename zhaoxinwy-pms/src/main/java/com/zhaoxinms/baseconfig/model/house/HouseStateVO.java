package com.zhaoxinms.baseconfig.model.house;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhaoxinms.base.util.DateUtil;
import com.zhaoxinms.util.ConstantsUtil;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 租控图视图
 * 
 * @author fanhuibin
 * @date 2022/06/25
 */
@Data
public class HouseStateVO {
    public static final String STATE_EMPTY = "empty"; // 空置
    public static final String STATE_ERROR = "error"; // 逾期
    public static final String STATE_WARN = "warn"; // 预警
    public static final String STATE_RENTED = "rented"; // 出租中
    public static final String STATE_SELLED = "selled"; // 已出售

    /** 商铺编号 */
    @JsonProperty("code")
    @Excel(name = "商铺编号")
    private String code;
    
    private String name;

    /** 商业区 */
    @JsonProperty("building")
    @Excel(name = "楼栋编号")
    private String building;

    /** 商业区 */
    @JsonProperty("block")
    @Excel(name = "商业区编号")
    private String block;

    /** 使用状态 */
    @JsonProperty("state")
    private String state;

    /**
     * 显示的状态
     */
    private String showState;

    /** 楼层 */
    @JsonProperty("floor")
    @Excel(name = "楼层")
    private String floor;

    /** 占地面积 */
    @JsonProperty("buildingsquare")
    private String buildingSquare;

    /** 租金 */
    @JsonProperty("rentFee")
    private String rentFee;

    /** 使用面积 */
    @JsonProperty("usesquare")
    private String useSquare;

    /** 备注 */
    @JsonProperty("remark")
    private String remark;

    private Date stateEndTime;
    
    private String stateCompany;

    public String getShowState() {
        if (this.getState().equals(ConstantsUtil.HOUSE_STATE_RENTED)) {
            if (stateEndTime != null) {

                // 超过现在，显示逾期
                if (stateEndTime.getTime() < System.currentTimeMillis()) {
                    return STATE_ERROR;
                }
                // 到期前90天提示可以续签
                if (stateEndTime.getTime() - System.currentTimeMillis() < 1l * 90l * 24l * 60l * 60l * 1000l) {
                    return STATE_WARN;
                }
                return STATE_RENTED;
            } else {
                return STATE_RENTED;
            }
        } else if (this.getState().equals(ConstantsUtil.HOUSE_STATE_EMPTY)) {
            return STATE_EMPTY;
        } else if (this.getState().equals(ConstantsUtil.HOUSE_STATE_SELLED)) {
            return STATE_SELLED;
        } else {
            return "";
        }
    }
    
    public String getRemark() {
        if(this.getState().equals(ConstantsUtil.HOUSE_STATE_EMPTY)){
            if(this.getStateEndTime() == null) {
                return "空置";
            }else {
                int days = DateUtil.getDiffDays(this.getStateEndTime(), new Date());
                return "空置"+days+"天";
            }
        }
        return "";
    }
}