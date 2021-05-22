package com.mci.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mci.gulimall.product.entity.CategoryBrandRelationEntity;
import com.mci.gulimall.product.service.CategoryBrandRelationService;
import com.mci.gulimall.product.vo.Catelog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.Query;

import com.mci.gulimall.product.dao.CategoryDao;
import com.mci.gulimall.product.entity.CategoryEntity;
import com.mci.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redisson;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // use BaseMapper instead of CategoryDao, get all categories
        List<CategoryEntity> entities = baseMapper.selectList(null);

        // setup sub categories
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map((menu) -> {
            menu.setChildren(getChildren(menu, entities));
            return menu;
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());

        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        // TODO double check if the to delete menu is used by other menus

        // logic delete
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);

        Collections.reverse(parentPath);

        return parentPath.toArray(new Long[parentPath.size()]);
    }

    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    @Cacheable({"category"})
    @Override
    public List<CategoryEntity> getLevel1Categories() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));

        return categoryEntities;
    }

    // TODO Would have OutOfDirectMemoryError while using JMeter
    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        // 1. get cache
        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
        if (StringUtils.isEmpty(catalogJSON)) {
            Map<String, List<Catelog2Vo>> catalogJsonFromDb = null;
            try {
                catalogJsonFromDb = getCatalogJsonFromDbWithRedisLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return catalogJsonFromDb;
        }

        Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {
        });

        return result;
    }

    private Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedissonLock() throws InterruptedException {

        // 1. use Redisson lock
        RLock lock = redisson.getLock("catalogJson-lock");
        lock.lock();

        // 2. set lock timeout must be initialized at creation

        Map<String, List<Catelog2Vo>> dataFromDb;
        try {
            dataFromDb = getDataFromDb();
        } finally {
            lock.unlock();
        }

        return dataFromDb;

    }

    /**
     * Get catalog data from database by using Redis lock
     *
     * @return
     */
    private Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedisLock() throws InterruptedException {

        // 1. use Redis lock, see Redis documentation: SETNX
        // 2. set lock timeout must be initialized at creation
        String uuid = UUID.randomUUID().toString();
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);
        if (lock) { // locked
            Map<String, List<Catelog2Vo>> dataFromDb;
            try {
                dataFromDb = getDataFromDb();
            } finally {
                // script LUA form Redis documentation
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                // unlock
                Long result = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class),
                        Arrays.asList("lock"), uuid);

            }

            return dataFromDb;
        } else {
            // fail to lock -> retry
            // wait 100ms
            Thread.sleep(200);

            return getCatalogJsonFromDbWithRedisLock();
        }

    }

    private Map<String, List<Catelog2Vo>> getDataFromDb() {
        // 1. get cache again !!
        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
        if (!StringUtils.isEmpty(catalogJSON)) { // data already in cache
            Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {
            });

            return result;
        }
        // Optimizations, get the level 1 categories
        List<CategoryEntity> selectList = baseMapper.selectList(null);

        // get all level 1 categories
        List<CategoryEntity> level1Categories = getParentCid(selectList, 0L);

        Map<String, List<Catelog2Vo>> parentCid = level1Categories.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // get all level 2 categories
            List<CategoryEntity> categoryEntities = getParentCid(selectList, v.getCatId());

            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());

                    // get all level 3 categories
                    List<CategoryEntity> level3Catelog = getParentCid(selectList, l2.getCatId());

                    if (level3Catelog != null) {
                        List<Catelog2Vo.Catelog3Vo> catelog3Vos = level3Catelog.stream().map(l3 -> {
                            Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());

                            return catelog3Vo;
                        }).collect(Collectors.toList());

                        catelog2Vo.setCatalog3List(catelog3Vos);
                    }

                    return catelog2Vo;
                }).collect(Collectors.toList());
            }

            return catelog2Vos;
        }));

        String catalogJsonString = JSON.toJSONString(parentCid);
        // put data into cache, format would be Json in String
        redisTemplate.opsForValue().set("catalogJSON", catalogJsonString, 1, TimeUnit.DAYS);

        return parentCid;
    }

    /**
     * Get catalog data from database by using local lock
     *
     * @return
     */
    private Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithLocalLock() {
        synchronized (this) {
            // 1. get cache again !!
            return getDataFromDb();
        }
    }

    private List<CategoryEntity> getParentCid(List<CategoryEntity> selectList, Long parentCid) {
        List<CategoryEntity> collect = selectList.stream().filter(item -> item.getParentCid() == parentCid).collect(Collectors.toList());

        return collect;
    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        // 1. get current node id
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);

        // 2. get parent node id
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }

        return paths;
    }

    // find all sub categories
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
            categoryEntity.setChildren(getChildren(categoryEntity, all));
            return categoryEntity;
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());

        return children;
    }
}