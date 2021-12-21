package com.zhaoxinms.base.service;


import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoxinms.base.entity.DictionaryDataEntity;

public interface DictionaryDataService extends IService<DictionaryDataEntity> {

    /**
     * 列表
     *
     * @return
     */
    List<DictionaryDataEntity> getList();

    /**
     * 列表
     *
     * @param dictionaryTypeId 类别主键
     * @return
     */
    List<DictionaryDataEntity> getList(String dictionaryTypeId);

    /**
     * 信息
     *
     * @param id 主键值
     * @return
     */
    DictionaryDataEntity getInfo(String id);

    /**
     * 验证名称
     *
     * @param dictionaryTypeId 类别主键
     * @param fullName         名称
     * @param id               主键值
     * @return
     */
    boolean isExistByFullName(String dictionaryTypeId, String fullName, String id);

    /**
     * 验证编码
     *
     * @param dictionaryTypeId 类别主键
     * @param enCode           编码
     * @param id               主键值
     * @return
     */
    boolean isExistByEnCode(String dictionaryTypeId, String enCode, String id);

    /**
     * 删除
     *
     * @param entity 实体对象
     */
    void delete(DictionaryDataEntity entity);

    /**
     * 创建
     *
     * @param entity 实体对象
     */
    void create(DictionaryDataEntity entity);

    /**
     * 更新
     * @param id        主键值
     * @param entity    实体对象
     * @return
     */
    boolean update(String id, DictionaryDataEntity entity);

    /**
     * 上移
     * @param id    主键值
     * @return
     */
    boolean first(String id);

    /**
     * 下移
     * @param id    主键值
     * @return
     */
    boolean next(String id);

    /**
     * 获取名称
     * @param id
     * @return
     */
    List<DictionaryDataEntity> getDictionName(List<String> id);
}
