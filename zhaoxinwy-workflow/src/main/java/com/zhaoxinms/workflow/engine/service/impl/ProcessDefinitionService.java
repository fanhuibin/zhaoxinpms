package com.zhaoxinms.workflow.engine.service.impl;

import java.io.FileInputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.zhaoxinms.common.core.page.PageDomain;
import com.zhaoxinms.common.core.page.TableSupport;
import com.zhaoxinms.common.core.text.Convert;
import com.zhaoxinms.common.utils.StringUtils;
import com.zhaoxinms.workflow.engine.service.IProcessDefinitionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProcessDefinitionService implements IProcessDefinitionService {

    private RuntimeService runtimeService;
    private RepositoryService repositoryService;

    /**
     * 分页查询流程定义文件
     * 
     * @return
     */
    @Override
    public Page<com.zhaoxinms.workflow.engine.entity.ProcessDefinition> listProcessDefinition(com.zhaoxinms.workflow.engine.entity.ProcessDefinition processDefinition) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();

        Page<com.zhaoxinms.workflow.engine.entity.ProcessDefinition> list = new Page<>();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.orderByProcessDefinitionId().orderByProcessDefinitionId().desc();
        if (StringUtils.isNotBlank(processDefinition.getName())) {
            processDefinitionQuery.processDefinitionNameLike("%" + processDefinition.getName() + "%");
        }
        if (StringUtils.isNotBlank(processDefinition.getKey())) {
            processDefinitionQuery.processDefinitionKeyLike("%" + processDefinition.getKey() + "%");
        }
        if (StringUtils.isNotBlank(processDefinition.getCategory())) {
            processDefinitionQuery.processDefinitionCategoryLike("%" + processDefinition.getCategory() + "%");
        }

        List<ProcessDefinition> processDefinitionList;
        if (pageNum != null && pageSize != null) {
            processDefinitionList = processDefinitionQuery.listPage((pageNum - 1) * pageSize, pageSize);
            list.setTotal(processDefinitionQuery.count());
            list.setPageNum(pageNum);
            list.setPageSize(pageSize);
        } else {
            processDefinitionList = processDefinitionQuery.list();
        }
        for (ProcessDefinition definition : processDefinitionList) {
            ProcessDefinitionEntityImpl entityImpl = (ProcessDefinitionEntityImpl)definition;
            com.zhaoxinms.workflow.engine.entity.ProcessDefinition entity = new com.zhaoxinms.workflow.engine.entity.ProcessDefinition();
            entity.setId(definition.getId());
            entity.setKey(definition.getKey());
            entity.setName(definition.getName());
            entity.setCategory(definition.getCategory());
            entity.setVersion(definition.getVersion());
            entity.setDescription(definition.getDescription());
            entity.setDeploymentId(definition.getDeploymentId());
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(definition.getDeploymentId()).singleResult();
            entity.setDeploymentTime(deployment.getDeploymentTime());
            entity.setDiagramResourceName(definition.getDiagramResourceName());
            entity.setResourceName(definition.getResourceName());
            entity.setSuspendState(entityImpl.getSuspensionState() + "");
            if (entityImpl.getSuspensionState() == 1) {
                entity.setSuspendStateName("已激活");
            } else {
                entity.setSuspendStateName("已挂起");
            }
            list.add(entity);
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deployProcessDefinition(String filePath) {
        try {
            if (StringUtils.isNotBlank(filePath)) {
                if (filePath.endsWith(".zip")) {
                    ZipInputStream inputStream = new ZipInputStream(new FileInputStream(filePath));
                    repositoryService.createDeployment().addZipInputStream(inputStream).deploy();
                } else if (filePath.endsWith(".bpmn")) {
                    repositoryService.createDeployment().addInputStream(filePath, new FileInputStream(filePath)).deploy();
                }
            }
        } catch (Exception e) {
            System.out.println("流程定义模块异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deployXMLProcessDefinition(String processName, String xml) {
        try {
            String fileName = processName + ".bpmn20.xml";
            repositoryService.createDeployment().name(processName).addString(fileName, xml).deploy();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteProcessDeploymentByIds(String deploymentIds) throws Exception {
        String[] deploymentIdsArr = Convert.toStrArray(deploymentIds);
        int counter = 0;
        for (String deploymentId : deploymentIdsArr) {
            List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery().deploymentId(deploymentId).list();
            if (!CollectionUtils.isEmpty(instanceList)) {
                // 存在流程实例的流程定义
                throw new Exception("删除失败，存在运行中的流程实例");
            }
            repositoryService.deleteDeployment(deploymentId, true); // true 表示级联删除引用，比如 act_ru_execution 数据
            counter++;
        }
        return counter;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void suspendOrActiveDefinition(String id, String suspendState) {
        if ("1".equals(suspendState)) {
            // 当流程定义被挂起时，已经发起的该流程定义的流程实例不受影响（如果选择级联挂起则流程实例也会被挂起）。
            // 当流程定义被挂起时，无法发起新的该流程定义的流程实例。
            // 直观变化：act_re_procdef 的 SUSPENSION_STATE_ 为 2
            repositoryService.suspendProcessDefinitionById(id);
        } else if ("2".equals(suspendState)) {
            repositoryService.activateProcessDefinitionById(id);
        }
    }

}
