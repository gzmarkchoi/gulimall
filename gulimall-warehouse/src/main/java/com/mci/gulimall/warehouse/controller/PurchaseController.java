package com.mci.gulimall.warehouse.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mci.gulimall.warehouse.vo.MergeVo;
import com.mci.gulimall.warehouse.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mci.gulimall.warehouse.entity.PurchaseEntity;
import com.mci.gulimall.warehouse.service.PurchaseService;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.R;


/**
 * 采购信息
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-03-06 22:47:52
 */
@RestController
@RequestMapping("warehouse/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * Merge purchase list
     * <p>
     * /warehouse/purchase/done
     */
    @PostMapping("/done")
    public R finish(@RequestBody PurchaseDoneVo doneVo) {
        purchaseService.done(doneVo);

        return R.ok();
    }

    /**
     * Merge purchase list
     * <p>
     * /warehouse/purchase/merge
     */
    @PostMapping("/received")
    public R received(@RequestBody List<Long> ids) {
        purchaseService.received(ids);

        return R.ok();
    }

    /**
     * Merge purchase list
     * <p>
     * /warehouse/purchase/merge
     */
    @PostMapping("/merge")
    public R merge(@RequestBody MergeVo mergeVo) {
        purchaseService.mergePurchase(mergeVo);

        return R.ok();
    }

    /**
     * 列表
     * <p>
     * /warehouse/purchase/unreceive/list
     */
    @RequestMapping("/unreceive/list")
    //@RequiresPermissions("warehouse:purchase:list")
    public R unreceiveList(@RequestParam Map<String, Object> params) {
        PageUtils page = purchaseService.queryPageUnreceivePurchase(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     * <p>
     * /warehouse/purchase/list
     */
    @RequestMapping("/list")
    //@RequiresPermissions("warehouse:purchase:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     * <p>
     * /warehouse/purchase/info/{id}
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("warehouse:purchase:info")
    public R info(@PathVariable("id") Long id) {
        PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     * <p>
     * /warehouse/purchase/save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("warehouse:purchase:save")
    public R save(@RequestBody PurchaseEntity purchase) {
        purchase.setUpdateTime(new Date());
        purchase.setCreateTime(new Date());

        purchaseService.save(purchase);

        return R.ok();
    }

    /**
     * 修改
     * <p>
     * /warehouse/purchase/update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("warehouse:purchase:update")
    public R update(@RequestBody PurchaseEntity purchase) {
        purchaseService.updateById(purchase);

        return R.ok();
    }

    /**
     * 删除
     * <p>
     * /warehouse/purchase/delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("warehouse:purchase:delete")
    public R delete(@RequestBody Long[] ids) {
        purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
