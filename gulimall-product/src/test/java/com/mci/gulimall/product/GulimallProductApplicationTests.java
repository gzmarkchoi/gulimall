package com.mci.gulimall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mci.gulimall.product.entity.BrandEntity;
import com.mci.gulimall.product.service.BrandService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    public void contextLoads() {
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setName("Huawei");
//
//        brandService.save(brandEntity);
//        System.out.println("Save success...");

//        List<BrandEntity> listBrand = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
//        listBrand.forEach((item) -> {
//            System.out.println(item);
//        });
    }

}
