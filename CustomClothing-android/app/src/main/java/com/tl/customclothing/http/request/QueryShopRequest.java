package com.tl.customclothing.http.request;

public class QueryShopRequest implements BaseRequest
{
    private String key;

    private String page;

    public String getPage()
    {
        return page;
    }

    public void setPage(String page)
    {
        this.page = page;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }


}
