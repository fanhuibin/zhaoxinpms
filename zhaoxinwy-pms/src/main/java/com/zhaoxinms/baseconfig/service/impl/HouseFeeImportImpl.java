package com.zhaoxinms.baseconfig.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.util.DynDicUtil;
import com.zhaoxinms.baseconfig.entity.ConfigFeeItemEntity;
import com.zhaoxinms.baseconfig.entity.ConfigHouseEntity;
import com.zhaoxinms.baseconfig.model.configfeeitem.ConfigFeeItemPagination;
import com.zhaoxinms.baseconfig.model.house.HousePagination;
import com.zhaoxinms.baseconfig.service.ConfigFeeItemService;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.baseconfig.service.HouseFeeImportService;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.payment.model.paymentbill.HouseFeeImport;
import com.zhaoxinms.payment.model.paymentcontract.PaymentContractPagination;
import com.zhaoxinms.payment.service.PaymentBillService;
import com.zhaoxinms.payment.service.PaymentContractService;
import com.zhaoxinms.util.DateUtils;
import com.zhaoxinms.util.ValidateUtil;

@Service
public class HouseFeeImportImpl implements HouseFeeImportService {

    @Autowired
    private PaymentContractService paymentContractService;
    @Autowired
    private ConfigHouseService houseService;
    @Autowired
    private DynDicUtil dynDicUtil;
    @Autowired
    private PaymentBillService paymentBillService;
    @Autowired
    private ConfigFeeItemService configFeeItemService;

    @Override
    public Map<String, Object> importPreview(List<HouseFeeImport> houseFeeList) {
        List<Map<String, Object>> dataRow = new ArrayList<>();
        for (int i = 0; i < houseFeeList.size(); i++) {
            Map<String, Object> dataRowMap = new HashMap<>();
            HouseFeeImport model = houseFeeList.get(i);
            dataRowMap.put("area", model.getBlock());
            dataRowMap.put("code", model.getCode());
            dataRowMap.put("chargingItemName", model.getChargingItemName());
            dataRowMap.put("beginDate", DateUtils.formatDate(model.getEndDate(), "yyyy-MM-dd 00:00:00"));
            dataRowMap.put("endDate", DateUtils.formatDate(model.getEndDate(), "yyyy-MM-dd 23:59:59"));
            dataRowMap.put("amount", model.getAmount());
            dataRow.add(dataRowMap);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dataRow", dataRow);
        return map;
    }

    @Override
    public void importData(List<HouseFeeImport> houseFeeImportList) {
        List<PaymentBillEntity> billList = new ArrayList<PaymentBillEntity>();
        // 查询所有的商铺
        HousePagination search = new HousePagination();
        List<ConfigHouseEntity> houses = houseService.getTypeList(search, "1");

        // 查询所有的合约信息
        PaymentContractPagination contract = new PaymentContractPagination();
        List<PaymentContractEntity> contractList = paymentContractService.getTypeList(contract, "1");

        // 查询所有的收费项
        ConfigFeeItemPagination itemSearch = new ConfigFeeItemPagination();
        List<ConfigFeeItemEntity> items = configFeeItemService.getTypeList(itemSearch, "1");

        // 第一部分验证数据&数据格式转换
        for (int i = 0; i < houseFeeImportList.size(); i++) {
            HouseFeeImport fee = houseFeeImportList.get(i);
            String resourceId = "";
            String resourceName = "";
            String paymentChargingItemId = "";
            String paymentChargingItemName = "";
            String contractType = "";
            String contractId = "";
            String amount = "";

            // 校验商铺数据
            for (ConfigHouseEntity entity : houses) {
                if (entity.getBlock().equals(fee.getBlock()) && entity.getCode().equals(fee.getCode())) {
                    resourceId = entity.getId();
                }
            }
            if (StringUtils.isEmpty(resourceId) || StringUtils.isEmpty(resourceName)) {
                throw new DataException("导入第" + (i + 1) + "条数据失败，没有找到合适的商铺信息");
            }

            // 校验合同数据
            for (PaymentContractEntity c : contractList) {
                if (c.getResourceId().equals(resourceId)) {
                    contractId = c.getId();
                    contractType = c.getContractType();
                }
            }
            if (StringUtils.isEmpty(contractId) || StringUtils.isEmpty(contractType)) {
                throw new DataException("导入第" + (i + 1) + "条数据失败，请确认该商铺不是空置状态");
            }

            // 校验收费项信息
            for (ConfigFeeItemEntity item : items) {
                if (item.getName().equals(fee.getChargingItemName())) {
                    paymentChargingItemId = item.getId();
                    paymentChargingItemName = item.getName();
                }
            }
            if (StringUtils.isEmpty(paymentChargingItemId) || StringUtils.isEmpty(paymentChargingItemName)) {
                throw new DataException("导入第" + (i + 1) + "条数据失败，解析收费项失败");
            }
            if (!ValidateUtil.Number(fee.getAmount())) {
                throw new DataException("导入第" + (i + 1) + "条数据失败，收费金额不是数字");
            } else {
                amount = fee.getAmount();
            }

            PaymentBillEntity entity = new PaymentBillEntity();
            entity.setType(contractType);
            entity.setResourceId(resourceId);
            entity.setResourceName(resourceName);
            entity.setFeeItemId(paymentChargingItemId);
            entity.setFeeItemName(paymentChargingItemName);
            entity.setContractId(contractId);
            entity.setReceivable(amount);
            entity.setBeginDate(fee.getBeginDate());
            entity.setEndDate(fee.getEndDate());
            entity.setPayLogId("");
            entity.setReceivable(amount);
            billList.add(entity);
        }

        // 第二部分完成数据导入
        paymentBillService.saveBatch(billList);
    }

}
