package com.mci.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.product.entity.ProductLadderEntity;

import java.util.Map;

/**
 * 产品阶梯价格表(只针对同商品)
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 15:28:33
 */
public interface ProductLadderService extends IService<ProductLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

