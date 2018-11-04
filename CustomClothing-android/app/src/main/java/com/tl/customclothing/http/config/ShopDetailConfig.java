package com.tl.customclothing.http.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.customclothing.http.response.ResponseTemplate;
import com.tl.customclothing.http.response.ShopDetailResponse;
import com.tl.customclothing.utils.Constant;

/**
 * Created by tianlin on 2017/4/18.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class ShopDetailConfig extends BaseConfig
{

    public ShopDetailConfig()
    {
        setCached(true);
        setCacheTime(Constant.CACHE_TIME);
        setServlet("ShopDetail");
    }

    @Override
    public ResponseTemplate parseToObject(String json)
    {
        ResponseTemplate<ShopDetailResponse> responseTemplate =
                JSON.parseObject(json, new TypeReference<ResponseTemplate<ShopDetailResponse>>()
                {
                }.getType());
        return responseTemplate;
    }
}
