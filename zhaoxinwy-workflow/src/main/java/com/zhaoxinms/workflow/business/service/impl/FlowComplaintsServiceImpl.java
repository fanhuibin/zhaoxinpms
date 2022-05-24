package com.zhaoxinms.workflow.business.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.service.BillRuleService;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.UserProvider;
import com.zhaoxinms.baseconfig.entity.ConfigHouseEntity;
import com.zhaoxinms.baseconfig.service.ConfigHouseService;
import com.zhaoxinms.common.exception.ServiceException;
import com.zhaoxinms.common.utils.StringUtils;
import com.zhaoxinms.payment.entity.PaymentContractEntity;
import com.zhaoxinms.payment.service.PaymentContractService;
import com.zhaoxinms.workflow.business.entity.FlowComplaints;
import com.zhaoxinms.workflow.business.entity.FlowRepair;
import com.zhaoxinms.workflow.business.entity.bo.FlowComplaintsBo;
import com.zhaoxinms.workflow.business.entity.pagination.FlowComplaintsPagination;
import com.zhaoxinms.workflow.business.mapper.FlowComplaintsMapper;
import com.zhaoxinms.workflow.business.service.IFlowComplaintsService;
import com.zhaoxinms.workflow.engine.event.WorkflowEvent;
import com.zhaoxinms.workflow.engine.service.IProcessService;

/**
 * 投诉工单Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-24
 */
@Service
public class FlowComplaintsServiceImpl extends ServiceImpl<FlowComplaintsMapper, FlowComplaints> implements IFlowComplaintsService 
{
    @Autowired
    private FlowComplaintsMapper flowComplaintsMapper;
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private ConfigHouseService configHouseService;
    @Autowired
    private BillRuleService billRuleService;
    @Autowired
    private IProcessService processService;
    @Autowired
    private PaymentContractService paymentContractService;
    
    private final static String FLOW_ID = "complaints";
    
    public static final String STATE_APPLY = "apply";
    public static final String STATE_PROCESSING = "processing";
    public static final String STATE_COMPLETE = "complete";
    public static final String STATE_SCORE = "score";
    public static final String STATE_CANCEL = "cancel";
    
    @Override
    public List<FlowComplaints> getList(FlowComplaintsPagination pagination) {
    	LambdaQueryWrapper<FlowComplaints> lqw = buildQueryWrapper(pagination);
    	lqw.orderByDesc(FlowComplaints::getCreateTime);
    	
        Page<FlowComplaints> page =
            new Page<>(pagination.getCurrentPage(), pagination.getPageSize());
        IPage<FlowComplaints> userIPage = this.page(page, lqw);
        return pagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }
    
    @Override
    public FlowComplaints getInfo(String id) {
        QueryWrapper<FlowComplaints> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FlowComplaints::getId, id);
        return this.getOne(queryWrapper);
    }
    
    @Override
    public void create(FlowComplaints entity) {
		validEntityBeforeSave(entity);
		String no = billRuleService.getBillNumber(FLOW_ID, false);
        entity.setNo(no);
        
        String prefix = entity.getApplyName();
        if(StringUtils.isNotEmpty(entity.getApplyHouse())) {
           prefix = "商铺（"+entity.getApplyHouse()+")";
        }
        entity.setTitle(prefix + "发起的投诉工单");
        entity.setState(STATE_APPLY);
        entity.setApplyTime(new Date());
        entity.setReturnState("0");
        
        if(StringUtils.isNotEmpty(entity.getApplyHouse())){
            //通过applyHouse获取当前的业主信息
            PaymentContractEntity contract = paymentContractService.getByResourceName(entity.getApplyHouse());
            entity.setOwnerId(contract.getOwnerId());
        }
        
        this.save(entity);

        // 发起流程
        Map<String, Object> variables = new HashMap<>();
        try {
            processService.submitApply(entity, FLOW_ID, entity.getTitle(), no, variables);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("发起流程失败");
        }
        this.updateById(entity);
    }
    
    @Override
    public boolean update(String id, FlowComplaintsBo bo) {
        // 获取当前流程的状态
        FlowComplaints entity = JsonUtil.getJsonToBean(bo, FlowComplaints.class);
        FlowComplaints oldEntity = this.getById(bo.getId());
        Map<String, Object> variables = new HashMap<>();
        if (entity.getState().equals(STATE_APPLY)) {
            if (StringUtils.isEmpty(bo.getAssigneeUser())) {
                throw new DataException("请选择维修人员");
            }
            if (bo.getAssigneeUser().length() > 200 || bo.getAssigneeUserName().length() > 200) {
                throw new DataException("选择的维修人员过多，最多选择5个左右的维修人员");
            }
            entity.setState("processing");
            entity.setAssigneeUser(bo.getAssigneeUser());
            this.updateById(entity);
            variables.put("assignee", entity.getAssigneeUser());
            variables.put("comment", "完成工单分配");
        } else if (entity.getState().equals(STATE_COMPLETE)) {

        } else if (entity.getState().equals(STATE_PROCESSING)) {
            entity.setState("score");
            variables.put("comment", "维修完成");
        } else if (entity.getState().equals(STATE_SCORE)) {
            entity.setReturnState("1");
            entity.setReturnResult(bo.getReturnResult());
            entity.setReturnRemark(bo.getReturnRemark());
            entity.setState("complete");
            variables.put("comment", "录入回访数据");
        }

        // 执行流程
        variables.put("pass", "true");
        processService.complete(bo.getTaskId(), oldEntity.getInstanceId(), JsonUtil.getObjectToString(variables));

        entity.setId(id);
        validEntityBeforeSave(entity);
        return this.updateById(entity);
    }
    
    @Override
    public void delete(FlowComplaints entity) {
        if (entity != null) {
        	 this.delete(entity);
        }
    }

	private LambdaQueryWrapper<FlowComplaints> buildQueryWrapper(FlowComplaintsPagination pagination) {
        LambdaQueryWrapper<FlowComplaints> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(pagination.getInstanceId()), FlowComplaints::getInstanceId, pagination.getInstanceId());
        lqw.eq(StringUtils.isNotBlank(pagination.getNo()), FlowComplaints::getNo, pagination.getNo());
        lqw.eq(StringUtils.isNotBlank(pagination.getTitle()), FlowComplaints::getTitle, pagination.getTitle());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyPhone()), FlowComplaints::getApplyPhone, pagination.getApplyPhone());
        lqw.like(StringUtils.isNotBlank(pagination.getApplyName()), FlowComplaints::getApplyName, pagination.getApplyName());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyCategory()), FlowComplaints::getApplyCategory, pagination.getApplyCategory());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyContent()), FlowComplaints::getApplyContent, pagination.getApplyContent());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyRequirements()), FlowComplaints::getApplyRequirements, pagination.getApplyRequirements());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyHouse()), FlowComplaints::getApplyHouse, pagination.getApplyHouse());
        lqw.eq(pagination.getApplyTime() != null, FlowComplaints::getApplyTime, pagination.getApplyTime());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyImg()), FlowComplaints::getApplyImg, pagination.getApplyImg());
        lqw.eq(StringUtils.isNotBlank(pagination.getAppContent()), FlowComplaints::getAppContent, pagination.getAppContent());
        lqw.eq(pagination.getAppTime() != null, FlowComplaints::getAppTime, pagination.getAppTime());
        lqw.eq(StringUtils.isNotBlank(pagination.getConfirmContent()), FlowComplaints::getConfirmContent, pagination.getConfirmContent());
        lqw.eq(StringUtils.isNotBlank(pagination.getAssigneeUser()), FlowComplaints::getAssigneeUser, pagination.getAssigneeUser());
        lqw.like(StringUtils.isNotBlank(pagination.getAggigneeUserName()), FlowComplaints::getAssigneeUserName, pagination.getAggigneeUserName());
        lqw.eq(StringUtils.isNotBlank(pagination.getAssigneeContent()), FlowComplaints::getAssigneeContent, pagination.getAssigneeContent());
        lqw.eq(StringUtils.isNotBlank(pagination.getReturnState()), FlowComplaints::getReturnState, pagination.getReturnState());
        lqw.eq(StringUtils.isNotBlank(pagination.getReturnResult()), FlowComplaints::getReturnResult, pagination.getReturnResult());
        lqw.eq(StringUtils.isNotBlank(pagination.getReturnRemark()), FlowComplaints::getReturnRemark, pagination.getReturnRemark());
        lqw.eq(pagination.getEndTime() != null, FlowComplaints::getEndTime, pagination.getEndTime());
        lqw.eq(pagination.getPriority() != null, FlowComplaints::getPriority, pagination.getPriority());
        lqw.eq(StringUtils.isNotBlank(pagination.getState()), FlowComplaints::getState, pagination.getState());
        lqw.eq(StringUtils.isNotBlank(pagination.getClient()), FlowComplaints::getClient, pagination.getClient());
        return lqw;
    }


    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(FlowComplaints entity){
        if (StringUtils.isNotEmpty(entity.getApplyHouse())) {
            ConfigHouseEntity house = configHouseService.getByName(entity.getApplyHouse());
            if (house == null) {
                throw new ServiceException("该商铺编号不存在");
            }
        }
    }

    @Override
    public FlowComplaints getInfoByInstanceId(String instanceId) {
        QueryWrapper<FlowComplaints> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FlowComplaints::getInstanceId, instanceId);
        return this.getOne(queryWrapper);
    }
    
    @EventListener
    public void cancel(WorkflowEvent event) {
        if (event.getEventName().equals(WorkflowEvent.EVENT_CANCEL_APPLY)) {
            //1.通过instanceId查询到业务数据
            if(event.getProcessInstance().getProcessDefinitionKey().equals(FLOW_ID)){
                FlowComplaints complaints = this.getInfoByInstanceId(event.getProcessInstance().getProcessInstanceId());
                //2.设置流程的状态为已取消
                complaints.setState(STATE_CANCEL);
                this.updateById(complaints);
            }
        }
    }
}
