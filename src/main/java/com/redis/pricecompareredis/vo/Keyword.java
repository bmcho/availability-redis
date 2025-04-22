package com.redis.pricecompareredis.vo;

import lombok.Data;

import java.util.List;

@Data
public class Keyword {

    private String keyword;
    private List<ProductGroup> productGroupList;
}
