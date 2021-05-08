package com.mci.gulimall.warehouse.vo;

import lombok.Data;

@Data
public class SkuHasStockVo {
    private Long skuId;

    private Boolean hasStock;
}
