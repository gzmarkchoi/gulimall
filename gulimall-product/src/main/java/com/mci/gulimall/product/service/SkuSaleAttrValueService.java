package com.mci.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.mci.gulimall.product.vo.SkuItemSalesAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-06 15:50:34
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SkuItemSalesAttrsVo> getSaleAttrsBySpuId(Long spuId);
}

