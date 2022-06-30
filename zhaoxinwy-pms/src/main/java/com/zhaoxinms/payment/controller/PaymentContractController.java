/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms website：http://pms.zhaoxinms.com wx： zhaoxinms
 *
 */
package com.zhaoxinms.payment.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.DynDicUtil;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.base.vo.PageListVO;
import com.zhaoxinms.base.vo.PaginationVO;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.entity.ConfigHouseEntity;
import com.zhaoxinms.baseconfig.model.configfeeitem.ConfigFeeItemPagination;
import com.zhaoxinms.baseconfig.model.house.HousePagination;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.common.annotation.Log;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.enums.BusinessType;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.payment.entity.PaymentContractFeeEntity;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractCrForm;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractFeeForm;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractInfoVO;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractListVO;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractPagination;
import com.zhaoxinms.payment.service.PaymentContractFeeService;
import com.zhaoxinms.payment.service.PaymentContractService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "合约管理", description = "")
@RequestMapping("/payment/PaymentContract")
public class PaymentContractController {
    @Autowired
    private DynDicUtil dynDicUtil;
    @Autowired
    private UserProvider userProvider;

    @Autowired
    private PaymentContractService paymentContractService;
    @Autowired
    private PaymentContractFeeService paymentContractFeeService;
    @Autowired
    private ConfigFeeItemService configFeeItemService;
    @Autowired
    private ConfigHouseService configHouseService;

    /**
     * 列表
     *
     * @param paymentContractPagination
     * @return
     */
    @PreAuthorize("@ss.hasPermi('payment:paymentContract:list')")
    @GetMapping
    public ActionResult list(PaymentContractPagination paymentContractPagination) throws IOException {
        List<PaymentContractEntity> list = paymentContractService.getList(paymentContractPagination);

        List<PaymentContractListVO> listVO = JsonUtil.getJsonToList(list, PaymentContractListVO.class);
        PageListVO vo = new PageListVO();
        vo.setList(listVO);
        PaginationVO page = JsonUtil.getJsonToBean(paymentContractPagination, PaginationVO.class);
        vo.setPagination(page);
        return ActionResult.success(vo);
    }

    /**
     * 创建
     *
     * @param paymentContractCrForm
     * @return
     */
    @PreAuthorize("@ss.hasPermi('payment:paymentContract:insert')")
    @Log(title = "创建租售合约", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public ActionResult create(@RequestBody @Valid PaymentContractCrForm paymentContractCrForm) throws DataException {
        SysUser userInfo = userProvider.get();
        paymentContractService.create(paymentContractCrForm);
        return ActionResult.success("新建成功");
    }

    /**
     * 更新
     *
     * @param paymentContractCrForm
     * @return
     */
    @PreAuthorize("@ss.hasPermi('payment:paymentContract:update')")
    @Log(title = "更新租售合约", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    @Transactional
    public ActionResult update(@PathVariable("id") String id,
        @RequestBody @Valid PaymentContractCrForm paymentContractCrForm) throws DataException {
        SysUser userInfo = userProvider.get();
        paymentContractService.update(id, paymentContractCrForm);
        return ActionResult.success("更新成功");
    }

    /**
     * 信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ActionResult<PaymentContractInfoVO> info(@PathVariable("id") String id) {
        PaymentContractEntity entity = paymentContractService.getInfo(id);
        PaymentContractInfoVO vo = JsonUtil.getJsonToBean(entity, PaymentContractInfoVO.class);

        List<PaymentContractFeeEntity> fees = paymentContractFeeService.getbyContractId(id);
        List<PaymentContractFeeForm> feeEntitys = JsonUtil.getJsonToList(fees, PaymentContractFeeForm.class);

        // 查询收费项信息
        List<ConfigFeeItemEntity> items = configFeeItemService.getTypeList(new ConfigFeeItemPagination(), "1");
        for (PaymentContractFeeForm form : feeEntitys) {
            for (ConfigFeeItemEntity item : items) {
                if (form.getFeeItemId().equals(item.getId())) {
                    form.setName(item.getName());
                    form.setPrice(item.getPrice());
                }
            }
        }
        vo.setContractFees(feeEntitys);
        return ActionResult.success(vo);
    }

    /**
     * 信息
     *
     * @param resourceName
     * @return
     */
    @GetMapping("/resourceName/{resourceName}")
    public ActionResult<PaymentContractInfoVO> getByResourceName(@PathVariable("resourceName") String resourceName) {
        ConfigHouseEntity house = configHouseService.getByName(resourceName);
        if (house == null) {
            throw new DataException("请检查商铺编号是否正确，没有找到该编号的商铺");
        }

        PaymentContractEntity entity = paymentContractService.getByResourceName(resourceName);
        if (entity == null) {
            throw new DataException("该商铺处于空置状态");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("house", house);
        map.put("contract", entity);
        return ActionResult.success(map);
    }
    
    /**
     * 搜索合约的autocomplete
     *
     * @param resourceName
     * @return
     */
    @GetMapping("/resourceNameTips/{resourceName}")
    public ActionResult<PaymentContractInfoVO> getByResourceNameTips(@PathVariable("resourceName") String resourceName) {

        List<PaymentContractEntity> list = paymentContractService.getByResourceNameTips(resourceName);
        return ActionResult.success(list);
    }

    /**
     * 收回商铺
     *
     * @param id
     * @return
     * @throws DataException
     */
    @PreAuthorize("@ss.hasPermi('payment:paymentContract:delete')")
    @Log(title = "删除租售合约", businessType = BusinessType.DELETE)
    @DeleteMapping("/{houseid}")
    @Transactional
    public ActionResult delete(@PathVariable("houseid") String id) throws DataException {
        paymentContractService.cancelContract(id);
        return ActionResult.success("收回商铺成功");
    }
}
