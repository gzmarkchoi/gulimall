package com.mci.gulimall.product.web;

import com.mci.gulimall.product.entity.CategoryEntity;
import com.mci.gulimall.product.service.CategoryService;
import com.mci.gulimall.product.vo.Catelog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    RedissonClient redisson;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {

        // 1. get all level 1 categories
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categories();

        // classpath:/templates/ + return value + .html
        model.addAttribute("categories", categoryEntities);
        return "index";
    }

    //index/catalog.json
    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        Map<String, List<Catelog2Vo>> catalogJson = categoryService.getCatalogJson();

        return catalogJson;
    }

    @GetMapping("/writeUuid")
    @ResponseBody
    public String writeUuidValue() {
        String uuid = "";
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        RLock rLock = lock.writeLock();

        try {
            // modify data, add write lock
            rLock.lock();
            uuid = UUID.randomUUID().toString();
            Thread.sleep(30000);

            redisTemplate.opsForValue().set("uuidValue", uuid);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }

        return uuid;
    }

    @GetMapping("/readUuid")
    @ResponseBody
    public String readUuidValue() {
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");

        String uuid = "";
        // read data, add read lock
        RLock rLock = lock.readLock();
        rLock.lock();

        try {
            uuid = redisTemplate.opsForValue().get("uuidValue");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }

        return uuid;
    }

}
