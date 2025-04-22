package com.redis.pricecompareredis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.pricecompareredis.vo.Keyword;
import com.redis.pricecompareredis.vo.Product;
import com.redis.pricecompareredis.vo.ProductGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

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
        redisTemplate.opsForZSet().add(prod.getProductGroupId(), prod.getProductId(), prod.getPrice());
        return redisTemplate.opsForZSet().rank(prod.getProductId(), prod.getProductId()).intValue();
    }

    @Override
    public int setNewProductGroup(ProductGroup prodGrp) {
        List<Product> product = prodGrp.getProductList();
        String productId = product.get(0).getProductId();
        double price = product.get(0).getPrice();
        redisTemplate.opsForZSet().add(prodGrp.getProductGroupId(), productId, price);
        return redisTemplate.opsForZSet().zCard(prodGrp.getProductGroupId()).intValue();
    }

    @Override
    public int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score) {
        redisTemplate.opsForZSet().add(keyword, prodGrpId, score);
        return redisTemplate.opsForZSet().rank(keyword, prodGrpId).intValue();
    }

    @Override
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        Keyword returnInfo = new Keyword();
        List<ProductGroup> tempProdGrp = getProdGroupUsingKeyword(keyword);
        // 가져온 정보들을 Return 할 Object 에 넣기
        returnInfo.setKeyword(keyword);
        returnInfo.setProductGroupList(tempProdGrp);
        // 해당 Object return
        return returnInfo;
    }

    //
    public List<ProductGroup> getProdGroupUsingKeyword(String keyword) {

        List<ProductGroup> returnInfo = new ArrayList<>();

        // input 받은 keyword 로 productGrpId를 조회
        List<Object> prodGrpIdList;
        prodGrpIdList = List.copyOf(redisTemplate.opsForZSet().reverseRange(keyword, 0, 9));
        //Product tempProduct = new Product();
        List<Product> tempProdList = new ArrayList<>();

        //10개 prodGrpId로 loop
        for (Object prodGrpId : prodGrpIdList) {
            // Loop 타면서 ProductGrpID 로 Product:price 가져오기 (10개)

            ProductGroup tempProdGrp = new ProductGroup();

            Set prodAndPriceList;
            prodAndPriceList = redisTemplate.opsForZSet().rangeWithScores(prodGrpId.toString(), 0, 9);
            Iterator<Object> prodPricObj = prodAndPriceList.iterator();

            // loop 타면서 product obj에 bind (10개)
            while (prodPricObj.hasNext()) {
                ObjectMapper objMapper = new ObjectMapper();
                // {"value":00-10111-}, {"score":11000}
                Map<String, Object> prodPriceMap = objMapper.convertValue(prodPricObj.next(), Map.class);
                Product tempProduct = new Product();
                // Product Obj bind
                tempProduct.setProductId(prodPriceMap.get("value").toString()); // prod_id
                tempProduct.setPrice(Double.valueOf(prodPriceMap.get("score").toString()).intValue()); //es 검색된 score
                tempProduct.setProductGroupId(prodGrpId.toString());

                tempProdList.add(tempProduct);
            }
            // 10개 product price 입력완료
            tempProdGrp.setProductGroupId(prodGrpId.toString());
            tempProdGrp.setProductList(tempProdList);
            returnInfo.add(tempProdGrp);
        }

        return returnInfo;
    }
}
