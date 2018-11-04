package com.tl.customclothing.http.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.customclothing.http.response.LoginResponse;
import com.tl.customclothing.http.response.ResponseTemplate;

/**
 * Created by tianlin on 2017/4/14.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class LoginConfig extends BaseConfig
{
    public LoginConfig()
    {
        setServlet("UserLogin");
        setCacheTime(0);
        setCached(false);
    }

    @Override
    public ResponseTemplate parseToObject(String json)
    {
        ResponseTemplate<LoginResponse> responseTemplate =
                JSON.parseObject(json, new TypeReference<ResponseTemplate<LoginResponse>>()
                {
                }.getType());

        return responseTemplate;
    }
}
