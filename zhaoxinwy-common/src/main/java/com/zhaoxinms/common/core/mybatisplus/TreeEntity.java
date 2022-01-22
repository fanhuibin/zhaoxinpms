package com.zhaoxinms.common.core.mybatisplus;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree基类
 *
 * @author Lion Li
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TreeEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 父菜单名称
	 */
	@TableField(exist = false)
	private String parentName;

	/**
	 * 父菜单ID
	 */
	private Long parentId;

	/**
	 * 子部门
	 */
	@TableField(exist = false)
	private List<?> children = new ArrayList<>();

}
