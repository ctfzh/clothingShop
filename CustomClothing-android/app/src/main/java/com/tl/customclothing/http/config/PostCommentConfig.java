package com.tl.customclothing.http.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tl.customclothing.http.response.PostCommentResponse;
import com.tl.customclothing.http.response.ResponseTemplate;

/**
 * Created by tianlin on 2017/4/26.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class PostCommentConfig extends BaseConfig
{
    public PostCommentConfig()
    {
        setCached(false);
        setCacheTime(0);
        setServlet("PostComment");
    }

    @Override
    public ResponseTemplate parseToObject(String json)
    {
        ResponseTemplate<PostCommentResponse> responseTemplate =
                JSON.parseObject(json, new TypeReference<ResponseTemplate<PostCommentResponse>>()
                {
                }.getType());

        return responseTemplate;
    }
}
