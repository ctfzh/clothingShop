package com.tl.customclothing.http.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.customclothing.http.response.OrderSearchResponse;
import com.tl.customclothing.http.response.ResponseTemplate;


/**
 * Created by tianlin on 2017/4/25.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class OrderSearchConfig extends BaseConfig
{
    private static final long ORDER_SEARCH_CACHE_TIME = 30 * 1000;

    public OrderSearchConfig()
    {
        setCached(true);
        setCacheTime(ORDER_SEARCH_CACHE_TIME);
        setServlet("OrderSearch");
    }

    @Override
    public ResponseTemplate parseToObject(String json)
    {
        ResponseTemplate<OrderSearchResponse> response =
                JSON.parseObject(json, new TypeReference<ResponseTemplate<OrderSearchResponse>>()
                {
                }.getType());

        return response;
    }
}
