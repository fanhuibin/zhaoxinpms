package com.zhaoxinms.statistics.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.mapper.ConfigHouseMapper;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.baseconfig.service.impl.ConfigFeeItemServiceImpl;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractFeeListVO;
import com.zhaoxinms.payment.service.PaymentContractFeeService;
import com.zhaoxinms.statistics.mapper.PaymentStatisticsMapper;
import com.zhaoxinms.statistics.service.DashboardService;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.DateUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "待生成收费数据", description = "")
@RequestMapping("/statistics")
public class NextFeeStatisticsController {

    @Autowired
    private PaymentContractFeeService paymentContractFeeService;
    @Autowired
    private ConfigFeeItemService configFeeItemService;

    @PreAuthorize("@ss.hasPermi('statistics:nextFee:nextFee')")
    @GetMapping("/nextFee")
    public ActionResult list(HttpServletRequest request) {
        String feeItemId = request.getParameter("feeItemId");
        String resourceName = request.getParameter("resourceName");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
            throw new DataException("下次缴费时间不能为空");
        }
        beginTime = DateUtils.formatDate(new Date(Long.valueOf(beginTime)), "yyyy-MM-dd");
        endTime = DateUtils.formatDate(new Date(Long.valueOf(endTime)), "yyyy-MM-dd");

        List<PaymentContractFeeListVO> feeList =
            paymentContractFeeService.getCanGenerateData(resourceName, feeItemId, beginTime, endTime);

        // 查询并更新收费项名
        QueryWrapper<ConfigFeeItemEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getType, ConfigFeeItemServiceImpl.TYPE_HOUSE));
        queryWrapper.lambda().orderByDesc(ConfigFeeItemEntity::getCreatorTime);
        List<ConfigFeeItemEntity> fees = configFeeItemService.list(queryWrapper);
        for (PaymentContractFeeListVO vo : feeList) {
            for (ConfigFeeItemEntity fee : fees) {
                if (fee.getId().equals(vo.getFeeItemId())) {
                    vo.setFeeItemName(fee.getName());
                }
            }
        }
        return ActionResult.success(feeList);
    }
}
