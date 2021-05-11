package com.mci.gulimall.product.feign;

import com.mci.common.to.SkuHasStockVo;
import com.mci.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("gulimall-warehouse")
public interface WarehouseFeignService {

    /**
     * @param skuIds
     * @return
     */
    @PostMapping("warehouse/waresku/hasstock")
    R getSkuHasStock(@RequestBody List<Long> skuIds);
}
