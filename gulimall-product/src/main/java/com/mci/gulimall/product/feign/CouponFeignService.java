package com.mci.gulimall.product.feign;

import com.mci.common.to.SkuReductionTo;
import com.mci.common.to.SpuBoundTo;
import com.mci.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    /**
     * no need to trasfer the same data type in coupon controller, Spring Cloud would do the job,
     * the names must be the same in 2 objects
     *
     * @param spuBoundTo
     * @return
     */
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}

