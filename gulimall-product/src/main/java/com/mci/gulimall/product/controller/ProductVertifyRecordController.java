package com.mci.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mci.gulimall.product.entity.ProductVertifyRecordEntity;
import com.mci.gulimall.product.service.ProductVertifyRecordService;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.R;



/**
 * 商品审核记录
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 16:25:41
 */
@RestController
@RequestMapping("product/productvertifyrecord")
public class ProductVertifyRecordController {
    @Autowired
    private ProductVertifyRecordService productVertifyRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:productvertifyrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productVertifyRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:productvertifyrecord:info")
    public R info(@PathVariable("id") Long id){
		ProductVertifyRecordEntity productVertifyRecord = productVertifyRecordService.getById(id);

        return R.ok().put("productVertifyRecord", productVertifyRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:productvertifyrecord:save")
    public R save(@RequestBody ProductVertifyRecordEntity productVertifyRecord){
		productVertifyRecordService.save(productVertifyRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:productvertifyrecord:update")
    public R update(@RequestBody ProductVertifyRecordEntity productVertifyRecord){
		productVertifyRecordService.updateById(productVertifyRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:productvertifyrecord:delete")
    public R delete(@RequestBody Long[] ids){
		productVertifyRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
