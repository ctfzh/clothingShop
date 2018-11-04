package com.tl.customclothing.http.config;

import com.tl.customclothing.http.response.ResponseTemplate;
import com.tl.customclothing.utils.Constant;

/**
 * Created by tianlin on 2017/4/14.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public abstract class BaseConfig
{
    public BaseConfig()
    {
    }

    /**
     * 本次请求的servlet
     */
    public String servlet;

    /**
     * 是否缓存,默认不缓存
     */
    public boolean isCached = false;

    /**
     * 缓存时间
     */
    public long cacheTime = 0;

    public String getServlet()
    {
        return servlet;
    }

    public void setServlet(String servlet)
    {
        this.servlet = servlet + Constant.URL_SUFFIX;
    }

    /**
     * 将返回的json字符串转化为ResponseTemplate
     *
     * @param json
     * @return
     */
    public abstract ResponseTemplate parseToObject(String json);

    public boolean isCached()
    {
        return isCached;
    }

    public void setCached(boolean cached)
    {
        isCached = cached;
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
