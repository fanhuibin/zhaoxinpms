package com.zhaoxinms.workflow.business.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.workflow.business.entity.FlowComplaints;
import com.zhaoxinms.workflow.business.entity.bo.FlowComplaintsBo;
import com.zhaoxinms.workflow.business.entity.pagination.FlowComplaintsPagination;

/**
 * 投诉工单Service接口
 * 
 * @author ruoyi
 * @date 2022-01-24
 */
public interface IFlowComplaintsService extends IService<FlowComplaints>
{

    List<FlowComplaints> getList(FlowComplaintsPagination pagination);

    FlowComplaints getInfo(String id);

    void delete(FlowComplaints entity);

    void create(FlowComplaints entity);

    boolean update(String id, FlowComplaintsBo bo);

    FlowComplaints getInfoByInstanceId(String instanceId);
}
