package com.mci.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mci.gulimall.product.entity.ProductSaleAttrEntity;
import com.mci.gulimall.product.service.ProductSaleAttrService;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.R;



/**
 * 
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 16:25:41
 */
@RestController
@RequestMapping("product/productsaleattr")
public class ProductSaleAttrController {
    @Autowired
    private ProductSaleAttrService productSaleAttrService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:productsaleattr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productSaleAttrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:productsaleattr:info")
    public R info(@PathVariable("id") Long id){
		ProductSaleAttrEntity productSaleAttr = productSaleAttrService.getById(id);

        return R.ok().put("productSaleAttr", productSaleAttr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:productsaleattr:save")
    public R save(@RequestBody ProductSaleAttrEntity productSaleAttr){
		productSaleAttrService.save(productSaleAttr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:productsaleattr:update")
    public R update(@RequestBody ProductSaleAttrEntity productSaleAttr){
		productSaleAttrService.updateById(productSaleAttr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:productsaleattr:delete")
    public R delete(@RequestBody Long[] ids){
		productSaleAttrService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
