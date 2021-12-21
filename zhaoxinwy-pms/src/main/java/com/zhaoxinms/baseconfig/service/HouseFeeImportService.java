package com.zhaoxinms.baseconfig.service;

import java.util.List;
import java.util.Map;

import com.zhaoxinms.payment.model.paymentbill.HouseFeeImport;

public interface HouseFeeImportService {
	
	Map<String, Object> importPreview(List<HouseFeeImport> personList);
	void importData(List<HouseFeeImport> houseFeeImportList);
}
