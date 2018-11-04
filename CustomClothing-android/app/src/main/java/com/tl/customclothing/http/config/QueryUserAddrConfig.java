package com.tl.customclothing.http.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.customclothing.http.response.QueryUserAddrResponse;
import com.tl.customclothing.http.response.ResponseTemplate;

/**
 * Created by tianlin on 2017/4/27.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class QueryUserAddrConfig extends BaseConfig
{
    public QueryUserAddrConfig()
    {
        setCached(false);
        setCacheTime(0);
        setServlet("QueryAddr");
    }

    @Override
    public ResponseTemplate parseToObject(String json)
    {
        ResponseTemplate<QueryUserAddrResponse> responseTemplate =
                JSON.parseObject(json, new TypeReference<ResponseTemplate<QueryUserAddrResponse>>()
                {
                }.getType());
        return responseTemplate;
    }
}
