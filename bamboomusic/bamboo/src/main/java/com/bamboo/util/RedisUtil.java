package com.bamboo.util;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: kk
 * @Description: redis工具类
 **/
@Component
public class RedisUtil {
    @Autowired
    RedissonClient redissonClient;

    /**
     * 布隆过滤器初始化
     *
     * @param bloomFilterName 过滤器名称
     */
    public RBloomFilter<String> bloomFilterInit(String bloomFilterName) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(bloomFilterName);
        bloomFilter.tryInit(100_967_256L, 0.000001);
        return bloomFilter;
    }

    /**
     * 布隆过滤器添加数据
     *
     * @param bloomFilterName 过滤器名称
     * @param value           要添加的值
     */
    public void bloomFilterAdd(String bloomFilterName, String value) {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(bloomFilterName);
        bloomFilter.add(value);
    }

    /**
     * 布隆过滤器数据统计
     *
     * @param bloomFilterName
     * @param value
     */
    public boolean bloomFilterContains(String bloomFilterName, String value) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(bloomFilterName);
        return bloomFilter.contains(value);
    }
}
