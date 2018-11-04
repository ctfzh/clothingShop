package com.tl.customclothing.http.request;

public class UpdateUserAddrRequest implements BaseRequest
{

    private String userId;

    private String userAddrMain;

    private String userAddr1;

    private String userAddr2;


    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserAddrMain()
    {
        return userAddrMain;
    }

    public void setUserAddrMain(String userAddrMain)
    {
        this.userAddrMain = userAddrMain;
    }

    public String getUserAddr1()
    {
        return userAddr1;
    }

    public void setUserAddr1(String userAddr1)
    {
        this.userAddr1 = userAddr1;
    }

    public String getUserAddr2()
    {
        return userAddr2;
    }

    public void setUserAddr2(String userAddr2)
    {
        this.userAddr2 = userAddr2;
    }


}
