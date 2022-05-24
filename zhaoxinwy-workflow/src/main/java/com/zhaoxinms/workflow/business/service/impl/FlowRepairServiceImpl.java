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
import com.zhaoxinms.util.ValidateUtil;
import com.zhaoxinms.workflow.business.entity.FlowRepair;
import com.zhaoxinms.workflow.business.entity.bo.FlowRepairBo;
import com.zhaoxinms.workflow.business.entity.pagination.FlowRepairPagination;
import com.zhaoxinms.workflow.business.mapper.FlowRepairMapper;
import com.zhaoxinms.workflow.business.service.IFlowRepairService;
import com.zhaoxinms.workflow.engine.event.WorkflowEvent;
import com.zhaoxinms.workflow.engine.service.IProcessService;

/**
 * 报修工单Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-06
 */
@Service
public class FlowRepairServiceImpl extends ServiceImpl<FlowRepairMapper, FlowRepair> implements IFlowRepairService {
    @Autowired
    private FlowRepairMapper flowRepairMapper;
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private IProcessService processService;
    @Autowired
    private BillRuleService billRuleService;
    @Autowired
    private ConfigHouseService configHouseService;
    @Autowired
    private PaymentContractService paymentContractService;

    private final static String FLOW_ID = "repair";
    public static final String STATE_APPLY = "apply";
    public static final String STATE_COMPLETE = "complete";
    public static final String STATE_UNCONFIRMED = "unconfirmed";
    public static final String STATE_REPAIRING = "repairing";
    public static final String STATE_SCORE = "score";
    public static final String STATE_CANCEL = "cancel";

    @Override
    public List<FlowRepair> getList(FlowRepairPagination pagination) {
        LambdaQueryWrapper<FlowRepair> lqw = buildQueryWrapper(pagination);
        lqw.orderByDesc(FlowRepair::getCreateTime);

        Page<FlowRepair> page = new Page<>(pagination.getCurrentPage(), pagination.getPageSize());
        IPage<FlowRepair> userIPage = this.page(page, lqw);
        return pagination.setData(userIPage.getRecords(), userIPage.getTotal());
    }

    @Override
    public FlowRepair getInfo(String id) {
        QueryWrapper<FlowRepair> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FlowRepair::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    public FlowRepair getInfoByInstanceId(String instanceId) {
        QueryWrapper<FlowRepair> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FlowRepair::getInstanceId, instanceId);
        return this.getOne(queryWrapper);
    }

    @Override
    public void create(FlowRepair entity) {
        validEntityBeforeCreate(entity);
        String no = billRuleService.getBillNumber(FLOW_ID, false);
        entity.setNo(no);
        
        String prefix = entity.getApplyName();
        if(StringUtils.isNotEmpty(entity.getApplyHouse())) {
           prefix = "商铺（"+entity.getApplyHouse()+")";
        }
        entity.setTitle(prefix + "发起的报修工单");
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

    private void validEntityBeforeCreate(FlowRepair entity) {
        if (StringUtils.isNotEmpty(entity.getApplyHouse())) {
            ConfigHouseEntity house = configHouseService.getByName(entity.getApplyHouse());
            if (house == null) {
                throw new ServiceException("该商铺编号不存在");
            }
        }
    }

    @Override
    //TODO 优化代码，采用流程设计器的字段权限判断哪些字段可以修改
    public boolean update(String id, FlowRepairBo bo) {
        // 获取当前流程的状态
        FlowRepair entity = JsonUtil.getJsonToBean(bo, FlowRepair.class);
        FlowRepair oldEntity = this.getById(bo.getId());
        Map<String, Object> variables = new HashMap<>();
        if (entity.getState().equals(STATE_APPLY)) {
            if (StringUtils.isEmpty(bo.getRepairUser())) {
                throw new DataException("请选择维修人员");
            }
            if (bo.getRepairUser().length() > 200 || bo.getRepairUserName().length() > 200) {
                throw new DataException("选择的维修人员过多，最多选择5个左右的维修人员");
            }
            entity.setState("unconfirmed");
            entity.setRepairUser(bo.getRepairUser());
            this.updateById(entity);
            variables.put("repairAssignee", entity.getRepairUser());
            variables.put("comment", "完成工单分配");
        } else if (entity.getState().equals(STATE_COMPLETE)) {

        } else if (entity.getState().equals(STATE_UNCONFIRMED)) {

            if ("repaired".equals(bo.getRepairState())) {
                entity.setState("score");
                entity.setRepairMaterialsFee(bo.getRepairMaterialsFee());
                entity.setRepairServiceFee(bo.getRepairServiceFee());
                entity.setRepairState(bo.getRepairState());
                variables.put("comment", "维修完成");
            }else if("repairing".equals(bo.getRepairState())) {
                entity.setState("repairing");
                entity.setRepairContent(bo.getRepairContent());
                entity.setRepairState(bo.getRepairState());
                this.updateById(entity);
                variables.put("comment", "现场确认完成");
            }
            variables.put("repairState", bo.getRepairState());
        } else if (entity.getState().equals(STATE_REPAIRING)) {
            entity.setState("score");
            entity.setRepairMaterialsFee(bo.getRepairMaterialsFee());
            entity.setRepairServiceFee(bo.getRepairServiceFee());
            variables.put("comment", "维修完成");
            variables.put("repairState", bo.getRepairState());
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
    public void delete(FlowRepair entity) {
        if (entity != null) {
            this.delete(entity);
        }
    }

    private LambdaQueryWrapper<FlowRepair> buildQueryWrapper(FlowRepairPagination pagination) {
        LambdaQueryWrapper<FlowRepair> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(pagination.getInstanceId()), FlowRepair::getInstanceId, pagination.getInstanceId());
        lqw.eq(StringUtils.isNotBlank(pagination.getNo()), FlowRepair::getNo, pagination.getNo());
        lqw.eq(StringUtils.isNotBlank(pagination.getTitle()), FlowRepair::getTitle, pagination.getTitle());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyPhone()), FlowRepair::getApplyPhone, pagination.getApplyPhone());
        lqw.like(StringUtils.isNotBlank(pagination.getApplyName()), FlowRepair::getApplyName, pagination.getApplyName());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyCategory()), FlowRepair::getApplyCategory, pagination.getApplyCategory());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyContent()), FlowRepair::getApplyContent, pagination.getApplyContent());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyHouse()), FlowRepair::getApplyHouse, pagination.getApplyHouse());
        lqw.eq(pagination.getApplyTime() != null, FlowRepair::getApplyTime, pagination.getApplyTime());
        lqw.eq(StringUtils.isNotBlank(pagination.getApplyImg()), FlowRepair::getApplyImg, pagination.getApplyImg());
        lqw.eq(StringUtils.isNotBlank(pagination.getAppContent()), FlowRepair::getAppContent, pagination.getAppContent());
        lqw.eq(pagination.getAppointmentTime() != null, FlowRepair::getAppointmentTime, pagination.getAppointmentTime());
        lqw.eq(pagination.getAppTime() != null, FlowRepair::getAppTime, pagination.getAppTime());
        lqw.eq(StringUtils.isNotBlank(pagination.getRepairUser()), FlowRepair::getRepairUser, pagination.getRepairUser());
        lqw.eq(pagination.getRepairMaterialsFee() != null, FlowRepair::getRepairMaterialsFee, pagination.getRepairMaterialsFee());
        lqw.eq(pagination.getRepairServiceFee() != null, FlowRepair::getRepairServiceFee, pagination.getRepairServiceFee());
        lqw.eq(StringUtils.isNotBlank(pagination.getRepairContent()), FlowRepair::getRepairContent, pagination.getRepairContent());
        lqw.eq(StringUtils.isNotBlank(pagination.getReturnState()), FlowRepair::getReturnState, pagination.getReturnState());
        lqw.eq(StringUtils.isNotBlank(pagination.getReturnResult()), FlowRepair::getReturnResult, pagination.getReturnResult());
        lqw.eq(StringUtils.isNotBlank(pagination.getReturnRemark()), FlowRepair::getReturnRemark, pagination.getReturnRemark());
        lqw.eq(pagination.getEndTime() != null, FlowRepair::getEndTime, pagination.getEndTime());
        lqw.eq(pagination.getPriority() != null, FlowRepair::getPriority, pagination.getPriority());
        lqw.eq(StringUtils.isNotBlank(pagination.getState()), FlowRepair::getState, pagination.getState());
        lqw.eq(StringUtils.isNotBlank(pagination.getClient()), FlowRepair::getClient, pagination.getClient());
        lqw.eq(StringUtils.isNotBlank(pagination.getOwnerId()), FlowRepair::getOwnerId, pagination.getOwnerId());
        return lqw;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity
     *            实体类数据
     */
    private void validEntityBeforeSave(FlowRepair entity) {
        // TODO 做一些数据校验,如唯一约束
    }
    
    @EventListener
    public void cancel(WorkflowEvent event) {
        if (event.getEventName().equals(WorkflowEvent.EVENT_CANCEL_APPLY)) {
            //1.通过instanceId查询到业务数据
            if(event.getProcessInstance().getProcessDefinitionKey().equals(FLOW_ID)){
                 FlowRepair repair = this.getInfoByInstanceId(event.getProcessInstance().getProcessInstanceId());
                
                //2.设置流程的状态为已取消
                repair.setState(STATE_CANCEL);
                this.updateById(repair);
            }
        }
    }
}
