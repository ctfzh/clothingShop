package com.tl.customclothing.http.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.customclothing.http.response.QueryShopResponse;
import com.tl.customclothing.http.response.ResponseTemplate;
import com.tl.customclothing.utils.Constant;

/**
 * Created by tianlin on 2017/4/17.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class QueryShopConfig extends BaseConfig
{
    public QueryShopConfig()
    {
        setServlet("QueryShop");
        setCached(true);
        setCacheTime(Constant.CACHE_TIME);
    }

    @Override
    public ResponseTemplate parseToObject(String json)
    {
        ResponseTemplate<QueryShopResponse> responseTemplate = JSON
                .parseObject(json, new TypeReference<ResponseTemplate<QueryShopResponse>>()
                {
                }.getType());
        return responseTemplate;
    }
}
