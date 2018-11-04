package com.tl.customclothing.http.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.customclothing.http.response.ResponseTemplate;
import com.tl.customclothing.http.response.SubmitOrderResponse;

/**
 * Created by tianlin on 2017/4/21.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class SubmitOrderConfig extends BaseConfig
{
    public SubmitOrderConfig()
    {
        setCacheTime(0);
        setServlet("SubmitOrder");
        setCached(false);
    }

    @Override
    public ResponseTemplate parseToObject(String json)
    {
        ResponseTemplate<SubmitOrderResponse> responseTemplate =
                JSON.parseObject(json, new TypeReference<ResponseTemplate<SubmitOrderResponse>>()
                {
                }.getType());

        return responseTemplate;
    }
}
