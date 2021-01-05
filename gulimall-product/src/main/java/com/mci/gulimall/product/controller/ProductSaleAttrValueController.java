package com.mci.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mci.gulimall.product.entity.ProductSaleAttrValueEntity;
import com.mci.gulimall.product.service.ProductSaleAttrValueService;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.R;



/**
 * spu销售属性值
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 16:25:41
 */
@RestController
@RequestMapping("product/productsaleattrvalue")
public class ProductSaleAttrValueController {
    @Autowired
    private ProductSaleAttrValueService productSaleAttrValueService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:productsaleattrvalue:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productSaleAttrValueService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:productsaleattrvalue:info")
    public R info(@PathVariable("id") Long id){
		ProductSaleAttrValueEntity productSaleAttrValue = productSaleAttrValueService.getById(id);

        return R.ok().put("productSaleAttrValue", productSaleAttrValue);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:productsaleattrvalue:save")
    public R save(@RequestBody ProductSaleAttrValueEntity productSaleAttrValue){
		productSaleAttrValueService.save(productSaleAttrValue);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:productsaleattrvalue:update")
    public R update(@RequestBody ProductSaleAttrValueEntity productSaleAttrValue){
		productSaleAttrValueService.updateById(productSaleAttrValue);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:productsaleattrvalue:delete")
    public R delete(@RequestBody Long[] ids){
		productSaleAttrValueService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
