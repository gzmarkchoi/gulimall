package com.mci.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mci.gulimall.product.entity.BaseSaleAttrEntity;
import com.mci.gulimall.product.service.BaseSaleAttrService;
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
@RequestMapping("product/basesaleattr")
public class BaseSaleAttrController {
    @Autowired
    private BaseSaleAttrService baseSaleAttrService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:basesaleattr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseSaleAttrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:basesaleattr:info")
    public R info(@PathVariable("id") Long id){
		BaseSaleAttrEntity baseSaleAttr = baseSaleAttrService.getById(id);

        return R.ok().put("baseSaleAttr", baseSaleAttr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:basesaleattr:save")
    public R save(@RequestBody BaseSaleAttrEntity baseSaleAttr){
		baseSaleAttrService.save(baseSaleAttr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:basesaleattr:update")
    public R update(@RequestBody BaseSaleAttrEntity baseSaleAttr){
		baseSaleAttrService.updateById(baseSaleAttr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:basesaleattr:delete")
    public R delete(@RequestBody Long[] ids){
		baseSaleAttrService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
