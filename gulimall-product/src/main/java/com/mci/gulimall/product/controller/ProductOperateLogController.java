package com.mci.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mci.gulimall.product.entity.ProductOperateLogEntity;
import com.mci.gulimall.product.service.ProductOperateLogService;
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
@RequestMapping("product/productoperatelog")
public class ProductOperateLogController {
    @Autowired
    private ProductOperateLogService productOperateLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:productoperatelog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productOperateLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:productoperatelog:info")
    public R info(@PathVariable("id") Long id){
		ProductOperateLogEntity productOperateLog = productOperateLogService.getById(id);

        return R.ok().put("productOperateLog", productOperateLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:productoperatelog:save")
    public R save(@RequestBody ProductOperateLogEntity productOperateLog){
		productOperateLogService.save(productOperateLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:productoperatelog:update")
    public R update(@RequestBody ProductOperateLogEntity productOperateLog){
		productOperateLogService.updateById(productOperateLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:productoperatelog:delete")
    public R delete(@RequestBody Long[] ids){
		productOperateLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
