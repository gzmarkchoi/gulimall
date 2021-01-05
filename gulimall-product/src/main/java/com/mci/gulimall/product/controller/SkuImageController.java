package com.mci.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mci.gulimall.product.entity.SkuImageEntity;
import com.mci.gulimall.product.service.SkuImageService;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.R;



/**
 * 库存单元图片表
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 16:25:41
 */
@RestController
@RequestMapping("product/skuimage")
public class SkuImageController {
    @Autowired
    private SkuImageService skuImageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:skuimage:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = skuImageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:skuimage:info")
    public R info(@PathVariable("id") Long id){
		SkuImageEntity skuImage = skuImageService.getById(id);

        return R.ok().put("skuImage", skuImage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:skuimage:save")
    public R save(@RequestBody SkuImageEntity skuImage){
		skuImageService.save(skuImage);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:skuimage:update")
    public R update(@RequestBody SkuImageEntity skuImage){
		skuImageService.updateById(skuImage);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:skuimage:delete")
    public R delete(@RequestBody Long[] ids){
		skuImageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
