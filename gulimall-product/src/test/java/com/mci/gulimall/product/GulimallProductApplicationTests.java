package com.mci.gulimall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mci.gulimall.product.entity.BrandEntity;
import com.mci.gulimall.product.service.BrandService;
import com.mci.gulimall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Test
    public void testFindPath() {
        Long[] catelogPath = categoryService.findCatelogPath(225L);
        log.info("complete path: {}", Arrays.asList(catelogPath));
    }

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
