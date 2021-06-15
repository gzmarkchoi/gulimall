package com.mci.gulimall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mci.gulimall.product.dao.AttrGroupDao;
import com.mci.gulimall.product.dao.SkuSaleAttrValueDao;
import com.mci.gulimall.product.entity.BrandEntity;
import com.mci.gulimall.product.service.BrandService;
import com.mci.gulimall.product.service.CategoryService;
import com.mci.gulimall.product.vo.SpuItemAttrGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Autowired
    SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Test
    public void testRedisson() {
        System.out.println(redissonClient);
    }

    @Test
    public void testStringRedisTemplate() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        // save data
        ops.set("hello", "world " + UUID.randomUUID().toString());

        // get data
        String hello = ops.get("hello");

        System.out.println("Data saved: " + hello);
    }

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

    @Test
    public void test() {
        attrGroupDao.getAttrGroupWithAttrsBySpuId(13L, 225L);

        skuSaleAttrValueDao.getSaleAttrsBySpuId(13L);
    }

}
