package com.zhaoxinms.workflow.engine.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.workflow.engine.entity.FlowDesignerEntity;
import com.zhaoxinms.workflow.engine.model.flowDesigner.PaginationFlowDesigner;

public interface FlowDesignerService extends IService<FlowDesignerEntity> {

    /**
     * 列表
     * @return
     */
    List<FlowDesignerEntity> getList(PaginationFlowDesigner pagination);

    /**
     * 信息
     *
     * @param id 主键值
     * @return
     */
    FlowDesignerEntity getInfo(String id);

    /**
     * 信息
     *
     * @param enCode 流程编码
     * @return
     */
    FlowDesignerEntity getInfoByEnCode(String enCode);

    /**
     * 验证名称
     *
     * @param fullName 名称
     * @param id       主键值
     * @return
     */
    boolean isExistByFullName(String fullName, String id);

    /**
     * 验证编码
     *
     * @param enCode 编码
     * @param id     主键值
     * @return
     */
    boolean isExistByEnCode(String enCode, String id);

    /**
     * 删除
     *
     * @param entity 实体对象
     */
    void delete(FlowDesignerEntity entity);

    /**
     * 更新
     *
     * @param id     主键值
     * @param entity 实体对象
     */
    boolean update(String id, FlowDesignerEntity entity);
    
    /**
     * 创建
     *
     * @param id     主键值
     * @param entity 实体对象
     */
    void create(FlowDesignerEntity entity);
}
