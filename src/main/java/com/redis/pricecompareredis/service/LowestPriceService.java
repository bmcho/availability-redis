package com.redis.pricecompareredis.service;

import java.util.Set;

public interface LowestPriceService {

    public Set getZsetValue(String key);
}
