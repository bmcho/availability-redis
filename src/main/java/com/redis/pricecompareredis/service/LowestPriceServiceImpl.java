package com.redis.pricecompareredis.service;

import com.redis.pricecompareredis.vo.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LowestPriceServiceImpl implements LowestPriceService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Set getZsetProduct(String key) {
        return redisTemplate.opsForZSet().rangeWithScores(key, 0, 9);
    }

    @Override
    public int setNewProduct(Product prod) {
        redisTemplate.opsForZSet().add(prod.getProductId(), prod.getProductId(), prod.getPrice());
        return redisTemplate.opsForZSet().rank(prod.getProductId(), prod.getProductId()).intValue();
    }
}
