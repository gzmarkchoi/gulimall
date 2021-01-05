package com.mci.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 库存单元图片表
 * 
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
@Data
@TableName("pms_sku_image")
public class SkuImageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Long id;
	/**
	 * 商品id
	 */
	private Long skuId;
	/**
	 * 图片名称（冗余）
	 */
	private String imgName;
	/**
	 * 图片路径(冗余)
	 */
	private String imgUrl;
	/**
	 * 商品图片id
	 */
	private Long productImgId;
	/**
	 * 是否默认
	 */
	private String isDefault;

}
