package com.mci.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mci.gulimall.product.entity.BaseAttrValueEntity;
import com.mci.gulimall.product.service.BaseAttrValueService;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.R;



/**
 * 属性值表
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 16:25:42
 */
@RestController
@RequestMapping("product/baseattrvalue")
public class BaseAttrValueController {
    @Autowired
    private BaseAttrValueService baseAttrValueService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:baseattrvalue:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseAttrValueService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:baseattrvalue:info")
    public R info(@PathVariable("id") Long id){
		BaseAttrValueEntity baseAttrValue = baseAttrValueService.getById(id);

        return R.ok().put("baseAttrValue", baseAttrValue);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:baseattrvalue:save")
    public R save(@RequestBody BaseAttrValueEntity baseAttrValue){
		baseAttrValueService.save(baseAttrValue);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:baseattrvalue:update")
    public R update(@RequestBody BaseAttrValueEntity baseAttrValue){
		baseAttrValueService.updateById(baseAttrValue);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:baseattrvalue:delete")
    public R delete(@RequestBody Long[] ids){
		baseAttrValueService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
