package com.redis.pricecompareredis.controller;

import com.redis.pricecompareredis.service.LowestPriceService;
import com.redis.pricecompareredis.vo.Keyword;
import com.redis.pricecompareredis.vo.Product;
import com.redis.pricecompareredis.vo.ProductGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class LowestPriceController {

    private final LowestPriceService lowestPriceService;

    @GetMapping("/product")
    public Set getZsetProduct(String key) {
        return lowestPriceService.getZsetProduct(key);
    }

    @PutMapping("/product")
    public int setNewProduct(@RequestBody Product prod) {
        return lowestPriceService.setNewProduct(prod);
    }

    @PutMapping("/product-group")
    public int setNewProductGroup(@RequestBody ProductGroup prodGrp) {
        return lowestPriceService.setNewProductGroup(prodGrp);
    }

    @PutMapping("/product-group/keyword")
    public int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score){
        return lowestPriceService.setNewProductGrpToKeyword(keyword, prodGrpId, score);
    }

    @GetMapping("/product-price/lowest")
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        return lowestPriceService.getLowestPriceProductByKeyword(keyword);
    }

}
