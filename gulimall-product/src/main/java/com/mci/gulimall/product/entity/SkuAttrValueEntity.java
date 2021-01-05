package com.mci.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sku平台属性值关联表
 * 
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
@Data
@TableName("pms_sku_attr_value")
public class SkuAttrValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Long id;
	/**
	 * 属性id（冗余)
	 */
	private Long attrId;
	/**
	 * 属性值id
	 */
	private Long valueId;
	/**
	 * skuid
	 */
	private Long skuId;

}
