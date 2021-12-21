package com.zhaoxinms.statistics.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.baseconfig.service.impl.ConfigFeeItemServiceImpl;
import com.zhaoxinms.payment.entity.PaymentPreAccountEntity;
import com.zhaoxinms.payment.model.paymentpreaccount.PaymentPreAccountListVO;
import com.zhaoxinms.payment.model.paymentpreaccount.PaymentPreAccountPagination;
import com.zhaoxinms.payment.service.PaymentPreAccountService;
import com.zhaoxinms.statistics.mapper.PaymentStatisticsMapper;
import com.zhaoxinms.statistics.model.DailyFeeReportModel;
import com.zhaoxinms.statistics.model.DailyReportModel;
import com.zhaoxinms.statistics.model.PayLogStatisticsModel;
import com.zhaoxinms.statistics.model.PaymentHouseAndFeeStatisticsModel;
import com.zhaoxinms.statistics.model.PaymentHouseStatisticsModel;
import com.zhaoxinms.statistics.service.PaymentStatisticsService;
import com.zhaoxinms.util.CalculationUtil;
import com.zhaoxinms.util.ConstantsUtil;
import com.zhaoxinms.util.DateUtils;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "收费报表（收费项目）")
@RequestMapping("/statistics/preAccount")
public class PreAccountController {

    @Autowired
    private PaymentPreAccountService paymentPreAccountService;
    @Autowired
    private ConfigFeeItemService configFeeItemService;

    /**
     * 列表
     *
     * @param paymentPreAccountPagination
     * @return
     */
    @PreAuthorize("@ss.hasPermi('statistics:preAccount:list')")
    @GetMapping
    public ActionResult list(PaymentPreAccountPagination paymentPreAccountPagination) throws IOException {
        List<PaymentPreAccountEntity> list = paymentPreAccountService.getList(paymentPreAccountPagination);

        // 查询收费项名
        QueryWrapper<ConfigFeeItemEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(t -> t.eq(ConfigFeeItemEntity::getType, ConfigFeeItemServiceImpl.TYPE_HOUSE));
        queryWrapper.lambda().orderByDesc(ConfigFeeItemEntity::getCreatorTime);
        List<ConfigFeeItemEntity> houseFees = configFeeItemService.list(queryWrapper);

        for (PaymentPreAccountEntity account : list) {
            for (ConfigFeeItemEntity fee : houseFees) {
                if (StringUtils.isNotEmpty(account.getFeeItemId()) && fee.getId().equals(account.getFeeItemId())) {
                    account.setFeeItemId(fee.getName());
                } else if (StringUtils.isEmpty(account.getFeeItemId())) {
                    account.setFeeItemId("不指定");
                }
            }
        }

        List<PaymentPreAccountListVO> listVO = JsonUtil.getJsonToList(list, PaymentPreAccountListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(paymentPreAccountPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }
}
