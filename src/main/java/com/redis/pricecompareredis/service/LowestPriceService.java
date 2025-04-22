package com.redis.pricecompareredis.service;

import com.redis.pricecompareredis.vo.Keyword;
import com.redis.pricecompareredis.vo.Product;
import com.redis.pricecompareredis.vo.ProductGroup;

import java.util.Set;

public interface LowestPriceService {

    Set getZsetProduct(String key);
    int setNewProduct(Product prod);
    int setNewProductGroup(ProductGroup prodGrp);
    int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score);
    Keyword getLowestPriceProductByKeyword(String keyword);
}
