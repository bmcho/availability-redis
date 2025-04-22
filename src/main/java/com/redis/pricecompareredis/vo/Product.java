package com.redis.pricecompareredis.vo;

import lombok.Data;

@Data
public class Product {

    private String productGroupId;
    private String productId;
    private int price;

}
