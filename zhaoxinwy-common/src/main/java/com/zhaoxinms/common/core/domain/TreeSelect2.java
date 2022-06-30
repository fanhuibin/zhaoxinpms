package com.zhaoxinms.common.core.domain;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zhaoxinms.common.core.domain.entity.SysDept;
import com.zhaoxinms.common.core.domain.entity.SysMenu;

/**
 * Treeselect树结构实体类
 * 
 * @author ruoyi
 */
public class TreeSelect2 implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private String value;

    /** 节点名称 */
    private String label;
    
    /** 节点类型*/
    private String type;
    
    /** 节点完整编码 */
    private String fullCode;
    
    private boolean disabled = false;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect2> children;

    public TreeSelect2()
    {

    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public List<TreeSelect2> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeSelect2> children)
    {
        this.children = children;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFullCode() {
        return fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }
}

