package com.zhaoxinms.workflow.business.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.workflow.business.entity.FlowRepair;
import com.zhaoxinms.workflow.business.entity.bo.FlowRepairBo;
import com.zhaoxinms.workflow.business.entity.pagination.FlowRepairPagination;

/**
 * 报修工单Service接口
 * 
 * @author ruoyi
 * @date 2022-01-06
 */
public interface IFlowRepairService extends IService<FlowRepair>
{

    List<FlowRepair> getList(FlowRepairPagination pagination);

    FlowRepair getInfo(String id);

    void delete(FlowRepair entity);

    void create(FlowRepair entity);

    boolean update(String id, FlowRepairBo bo);

    FlowRepair getInfoByInstanceId(String instanceId);
}
