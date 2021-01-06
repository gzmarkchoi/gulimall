package com.mci.gulimall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mci.gulimall.product.entity.BrandEntity;
import com.mci.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    void contextLoads() {
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setName("Huawei");
//
//        brandService.save(brandEntity);
//        System.out.println("Save success...");

        List<BrandEntity> listBrand = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
        listBrand.forEach((item) -> {
            System.out.println(item);
        });
    }

}
