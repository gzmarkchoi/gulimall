package com.mci.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 属性值表
 * 
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
@Data
@TableName("pms_base_attr_value")
public class BaseAttrValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Long id;
	/**
	 * 属性值名称
	 */
	private String valueName;
	/**
	 * 属性id
	 */
	private Long attrId;
	/**
	 * 启用：1 停用：0 1
	 */
	private String isEnabled;

}
