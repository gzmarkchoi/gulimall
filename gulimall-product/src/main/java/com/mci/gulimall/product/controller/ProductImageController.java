package com.mci.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mci.gulimall.product.entity.ProductImageEntity;
import com.mci.gulimall.product.service.ProductImageService;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.R;



/**
 * 商品图片表
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 16:25:42
 */
@RestController
@RequestMapping("product/productimage")
public class ProductImageController {
    @Autowired
    private ProductImageService productImageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:productimage:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productImageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:productimage:info")
    public R info(@PathVariable("id") Long id){
		ProductImageEntity productImage = productImageService.getById(id);

        return R.ok().put("productImage", productImage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:productimage:save")
    public R save(@RequestBody ProductImageEntity productImage){
		productImageService.save(productImage);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:productimage:update")
    public R update(@RequestBody ProductImageEntity productImage){
		productImageService.updateById(productImage);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:productimage:delete")
    public R delete(@RequestBody Long[] ids){
		productImageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
