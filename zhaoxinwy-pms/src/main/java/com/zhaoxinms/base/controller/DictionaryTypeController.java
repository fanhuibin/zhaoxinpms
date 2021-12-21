package com.zhaoxinms.base.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoxinms.base.ActionResult;
import com.zhaoxinms.base.entity.DictionaryTypeEntity;
import com.zhaoxinms.base.exception.DataException;
import com.zhaoxinms.base.model.dictionarytype.DictionaryTypeCrForm;
import com.zhaoxinms.base.model.dictionarytype.DictionaryTypeInfoVO;
import com.zhaoxinms.base.model.dictionarytype.DictionaryTypeListVO;
import com.zhaoxinms.base.model.dictionarytype.DictionaryTypeModel;
import com.zhaoxinms.base.model.dictionarytype.DictionaryTypeSelectModel;
import com.zhaoxinms.base.model.dictionarytype.DictionaryTypeSelectVO;
import com.zhaoxinms.base.model.dictionarytype.DictionaryTypeUpForm;
import com.zhaoxinms.base.service.DictionaryTypeService;
import com.zhaoxinms.base.util.JsonUtil;
import com.zhaoxinms.base.util.treeutil.SumTree;
import com.zhaoxinms.base.util.treeutil.TreeDotUtils;
import com.zhaoxinms.base.vo.ListVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "数据字典分类", value = "DictionaryType")
@RestController
@RequestMapping("/Base/DictionaryType")
public class DictionaryTypeController {

    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    /**
     * 获取字典分类
     *
     * @return
     */
    @ApiOperation("获取字典分类")
    @GetMapping
    public ActionResult treeView() {
        List<DictionaryTypeEntity> data = dictionaryTypeService.getList();

        List<DictionaryTypeModel> voListVO = JsonUtil.getJsonToList(data,DictionaryTypeModel.class);
        List<SumTree<DictionaryTypeModel>> sumTrees= TreeDotUtils.convertListToTreeDot(voListVO);
        List<DictionaryTypeListVO> list = JsonUtil.getJsonToList(sumTrees, DictionaryTypeListVO.class);
        ListVO<DictionaryTypeListVO> vo=new ListVO<>();
        vo.setList(list);
        return ActionResult.success(vo);
    }


    /**
     * 获取字典分类
     *
     * @return
     */
    @ApiOperation("获取所有字典分类下拉框列表")
    @GetMapping("/Selector")
    public ActionResult selectorTreeView() {
        List<DictionaryTypeEntity> data = dictionaryTypeService.getList();

        List<DictionaryTypeSelectModel> voListVO = JsonUtil.getJsonToList(data,DictionaryTypeSelectModel.class);
        List<SumTree<DictionaryTypeSelectModel>> sumTrees= TreeDotUtils.convertListToTreeDot(voListVO);
        List<DictionaryTypeSelectVO> list = JsonUtil.getJsonToList(sumTrees, DictionaryTypeSelectVO.class);
        ListVO<DictionaryTypeSelectVO> vo=new ListVO<>();
        vo.setList(list);
        return ActionResult.success(vo);
    }




    /**
     * 获取字典分类信息
     *
     * @param id 主键值
     * @return
     */
    @ApiOperation("获取字典分类信息")
    @GetMapping("/{id}")
    public ActionResult info(@PathVariable("id") String id)throws DataException {
        DictionaryTypeEntity entity = dictionaryTypeService.getInfo(id);
        DictionaryTypeInfoVO vo= JsonUtil.getJsonToBeanEx(entity,DictionaryTypeInfoVO.class);
        return ActionResult.success(vo);
    }

    /**
     * 添加字典分类
     *
     * @param dictionaryTypeCrForm 实体对象
     * @return
     */
    @ApiOperation("添加字典分类")
    @PostMapping
    public ActionResult create(@RequestBody @Valid DictionaryTypeCrForm dictionaryTypeCrForm) {
        DictionaryTypeEntity entity = JsonUtil.getJsonToBean(dictionaryTypeCrForm, DictionaryTypeEntity.class);
        entity.setParentId(entity.getParentId());
        if(dictionaryTypeService.isExistByFullName(entity.getFullName(), entity.getId())){
            return ActionResult.fail("名称不能重复");
        }
        if(dictionaryTypeService.isExistByEnCode(entity.getEnCode(), entity.getId())){
            return ActionResult.fail("编码不能重复");
        }
        dictionaryTypeService.create(entity);
        return ActionResult.success("新建成功");
    }

    /**
     * 修改字典分类
     *
     * @param dictionaryTypeUpForm 实体对象
     * @param id  主键值
     * @return
     */
    @ApiOperation("修改字典分类")
    @PutMapping("/{id}")
    public ActionResult update(@PathVariable("id") String id,@RequestBody @Valid DictionaryTypeUpForm dictionaryTypeUpForm) {
        DictionaryTypeEntity entity = JsonUtil.getJsonToBean(dictionaryTypeUpForm, DictionaryTypeEntity.class);
        if(dictionaryTypeService.isExistByFullName(entity.getFullName(), id)){
            return ActionResult.fail("名称不能重复");
        }
        if(dictionaryTypeService.isExistByEnCode(entity.getEnCode(), id)){
            return ActionResult.fail("编码不能重复");
        }
        boolean flag=dictionaryTypeService.update(id, entity);
        if(flag==false){
            return ActionResult.success("更新失败，数据不存在");
        }
        return ActionResult.success("更新成功");
    }

    /**
     * 删除字典分类
     *
     * @param id 主键值
     * @return
     */
    @ApiOperation("删除字典分类")
    @DeleteMapping("/{id}")
    public ActionResult delete(@PathVariable("id") String id) {
        DictionaryTypeEntity entity = dictionaryTypeService.getInfo(id);
        if (entity != null) {
            dictionaryTypeService.delete(entity);
            return ActionResult.success("删除成功");
        }
        return ActionResult.fail("删除失败，数据不存在");
    }

}
