package com.redis.pricecompareredis.controller;

import com.redis.pricecompareredis.service.LowestPriceServiceImpl;
import com.redis.pricecompareredis.vo.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class LowestPriceController {

    private final LowestPriceServiceImpl lowestPriceService;

    @GetMapping("/product")
    public Set GetZsetProduct(String key) {
        return lowestPriceService.getZsetProduct(key);
    }

    @PutMapping("/product")
    public int SetNewProduct(@RequestBody Product prod) {
        return lowestPriceService.setNewProduct(prod);
    }

}
