package com.tl.customclothing.model.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tianlin on 2017/4/14.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class RequestCacheRealm extends RealmObject
{
    @PrimaryKey
    private String key = "";

    private String request = "";

    private String response = "";

    private long cacheTime = 0;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getRequest()
    {
        return request;
    }

    public void setRequest(String request)
    {
        this.request = request;
    }

    public String getResponse()
    {
        return response;
    }

    public void setResponse(String response)
    {
        this.response = response;
    }

    public long getCacheTime()
    {
        return cacheTime;
    }

    public void setCacheTime(long cacheTime)
    {
        this.cacheTime = cacheTime;
    }
}
