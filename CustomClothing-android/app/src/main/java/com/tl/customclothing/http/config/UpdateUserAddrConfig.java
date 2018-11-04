package com.tl.customclothing.http.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.customclothing.http.response.ResponseTemplate;
import com.tl.customclothing.http.response.UpdateUserAddrResponse;

/**
 * Created by tianlin on 2017/4/27.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class UpdateUserAddrConfig extends BaseConfig
{

    public UpdateUserAddrConfig()
    {
        setCached(false);
        setCacheTime(0);
        setServlet("UpdateAddr");
    }

    @Override
    public ResponseTemplate parseToObject(String json)
    {
        ResponseTemplate<UpdateUserAddrResponse> responseTemplate =
                JSON.parseObject(json, new TypeReference<ResponseTemplate<UpdateUserAddrResponse>>()
                {
                }.getType());

        return responseTemplate;
    }
}
