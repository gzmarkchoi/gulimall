package com.mci.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mci.gulimall.product.entity.ProductLadderEntity;
import com.mci.gulimall.product.service.ProductLadderService;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.R;



/**
 * 产品阶梯价格表(只针对同商品)
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-05 16:25:42
 */
@RestController
@RequestMapping("product/productladder")
public class ProductLadderController {
    @Autowired
    private ProductLadderService productLadderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:productladder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productLadderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:productladder:info")
    public R info(@PathVariable("id") Long id){
		ProductLadderEntity productLadder = productLadderService.getById(id);

        return R.ok().put("productLadder", productLadder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:productladder:save")
    public R save(@RequestBody ProductLadderEntity productLadder){
		productLadderService.save(productLadder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:productladder:update")
    public R update(@RequestBody ProductLadderEntity productLadder){
		productLadderService.updateById(productLadder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:productladder:delete")
    public R delete(@RequestBody Long[] ids){
		productLadderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
