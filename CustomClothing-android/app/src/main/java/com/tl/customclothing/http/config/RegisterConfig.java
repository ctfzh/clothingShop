package com.tl.customclothing.http.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.customclothing.http.response.RegisterResponse;
import com.tl.customclothing.http.response.ResponseTemplate;

/**
 * Created by tianlin on 2017/4/14.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class RegisterConfig extends BaseConfig
{
    public RegisterConfig()
    {
        setServlet("RegisterUser");
        setCached(false);
        setCacheTime(0);
    }

    @Override
    public ResponseTemplate parseToObject(String json)
    {
        ResponseTemplate<RegisterResponse> responseTemplate =
                JSON.parseObject(json, new TypeReference<ResponseTemplate<RegisterResponse>>()
                {
                }.getType());
        return responseTemplate;
    }
}
