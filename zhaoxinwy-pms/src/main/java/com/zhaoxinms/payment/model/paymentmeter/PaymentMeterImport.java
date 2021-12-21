package com.zhaoxinms.payment.model.paymentmeter;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class PaymentMeterImport {
    @Excel(name = "商业区编号")
    @NotBlank
    private String block;

    @Excel(name = "商铺编号")
    @NotBlank
    private String code;

    @Excel(name = "费用类型")
    private String feeItemName;

    @Excel(name = "上期度数")
    @Pattern(regexp = "(([1-9]{1}\\d{0,7})|(0{1}))(\\.\\d{0,2})?",message = "读数格式不正确")
    private String lastIndex;

    @Excel(name = "上期读表时间")
    private String lastIndexDate;

    @Excel(name = "本期度数")
    @Pattern(regexp = "(([1-9]{1}\\d{0,7})|(0{1}))(\\.\\d{0,2})?",message = "读数格式不正确")
    private String currentIndex;

    @Excel(name = "本期读表时间")
    @NotBlank
    private String currentIndexDate;

    /** 倍率 */
    @Excel(name = "倍率")
    @Pattern(regexp = "(([1-9]{1}\\d{0,1})|(0{1}))(\\.\\d{0,2})?",message = "倍率格式不正确")
    private String multiple;

    /** 损耗 */
    @Excel(name = "损耗")
    @Pattern(regexp = "(([1-9]{1}\\d{0,7})|(0{1}))(\\.\\d{0,2})?",message = "损耗格式不正确")
    private String loss;

}