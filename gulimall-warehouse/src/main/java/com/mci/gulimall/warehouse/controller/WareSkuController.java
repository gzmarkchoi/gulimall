package com.mci.gulimall.warehouse.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.mci.gulimall.warehouse.vo.SkuHasStockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mci.gulimall.warehouse.entity.WareSkuEntity;
import com.mci.gulimall.warehouse.service.WareSkuService;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.R;


/**
 * 商品库存
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-06 18:39:15
 */
@RestController
@RequestMapping("warehouse/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    /**
     * check sku stock
     */
    @PostMapping("/hasstock")
    public R getSkuHasStock(@RequestBody List<Long> skuIds) {
        // sku_id, stock
        List<SkuHasStockVo> vos = wareSkuService.getSkuHasStock(skuIds);

        return R.ok().setData(vos);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("warehouse:waresku:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = wareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("warehouse:waresku:info")
    public R info(@PathVariable("id") Long id) {
        WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("warehouse:waresku:save")
    public R save(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.save(wareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("warehouse:waresku:update")
    public R update(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.updateById(wareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("warehouse:waresku:delete")
    public R delete(@RequestBody Long[] ids) {
        wareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
