package com.redis.pricecompareredis.controller;

import com.redis.pricecompareredis.service.LowestPriceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class LowestPriceController {

    private final LowestPriceServiceImpl lowestPriceService;


    @GetMapping("/getZsetValue")
    public Set GetZsetValue(String key) {
        return lowestPriceService.getZsetValue(key);
    }
}
