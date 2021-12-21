package com.zhaoxinms.payment.model.paymentbill;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.afterturn.easypoi.excel.annotation.Excel;

@Data
public class HouseFeeImport {
    @Excel(name = "商业区")
    private String block;

    @Excel(name = "商铺编号")
    private String code;

    @Excel(name = "收费项名称")
    private String chargingItemName;

    @Excel(name = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginDate;

    @Excel(name = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;

    @Excel(name = "收费金额")
    private String amount;

    List<HouseFeeImport> list;
}