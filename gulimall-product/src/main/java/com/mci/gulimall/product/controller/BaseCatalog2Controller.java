package com.mci.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mci.gulimall.product.entity.BaseCatalog2Entity;
import com.mci.gulimall.product.service.BaseCatalog2Service;
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
@RequestMapping("product/basecatalog2")
public class BaseCatalog2Controller {
    @Autowired
    private BaseCatalog2Service baseCatalog2Service;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:basecatalog2:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseCatalog2Service.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:basecatalog2:info")
    public R info(@PathVariable("id") Integer id){
		BaseCatalog2Entity baseCatalog2 = baseCatalog2Service.getById(id);

        return R.ok().put("baseCatalog2", baseCatalog2);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:basecatalog2:save")
    public R save(@RequestBody BaseCatalog2Entity baseCatalog2){
		baseCatalog2Service.save(baseCatalog2);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:basecatalog2:update")
    public R update(@RequestBody BaseCatalog2Entity baseCatalog2){
		baseCatalog2Service.updateById(baseCatalog2);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:basecatalog2:delete")
    public R delete(@RequestBody Integer[] ids){
		baseCatalog2Service.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
