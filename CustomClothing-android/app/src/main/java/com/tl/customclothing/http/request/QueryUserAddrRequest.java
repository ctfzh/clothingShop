package com.tl.customclothing.http.request;

public class QueryUserAddrRequest implements BaseRequest
{
    private String userId;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

}
