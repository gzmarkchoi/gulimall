package com.mci.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mci.gulimall.product.entity.BaseAttrInfoEntity;
import com.mci.gulimall.product.service.BaseAttrInfoService;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.R;



/**
 * 属性表
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 16:25:42
 */
@RestController
@RequestMapping("product/baseattrinfo")
public class BaseAttrInfoController {
    @Autowired
    private BaseAttrInfoService baseAttrInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:baseattrinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseAttrInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:baseattrinfo:info")
    public R info(@PathVariable("id") Long id){
		BaseAttrInfoEntity baseAttrInfo = baseAttrInfoService.getById(id);

        return R.ok().put("baseAttrInfo", baseAttrInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:baseattrinfo:save")
    public R save(@RequestBody BaseAttrInfoEntity baseAttrInfo){
		baseAttrInfoService.save(baseAttrInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:baseattrinfo:update")
    public R update(@RequestBody BaseAttrInfoEntity baseAttrInfo){
		baseAttrInfoService.updateById(baseAttrInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:baseattrinfo:delete")
    public R delete(@RequestBody Long[] ids){
		baseAttrInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
