package com.redis.pricecompareredis.service;

import com.redis.pricecompareredis.vo.Product;

import java.util.Set;

public interface LowestPriceService {

    Set getZsetProduct(String key);
    int setNewProduct(Product prod);
}
