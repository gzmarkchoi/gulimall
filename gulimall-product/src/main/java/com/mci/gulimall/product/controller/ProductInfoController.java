package com.mci.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mci.gulimall.product.entity.ProductInfoEntity;
import com.mci.gulimall.product.service.ProductInfoService;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.R;



/**
 * 
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 16:25:42
 */
@RestController
@RequestMapping("product/productinfo")
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:productinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:productinfo:info")
    public R info(@PathVariable("id") Long id){
		ProductInfoEntity productInfo = productInfoService.getById(id);

        return R.ok().put("productInfo", productInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:productinfo:save")
    public R save(@RequestBody ProductInfoEntity productInfo){
		productInfoService.save(productInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:productinfo:update")
    public R update(@RequestBody ProductInfoEntity productInfo){
		productInfoService.updateById(productInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:productinfo:delete")
    public R delete(@RequestBody Long[] ids){
		productInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
