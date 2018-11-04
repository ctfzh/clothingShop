package com.tl.customclothing.features.myaddr;

/**
 * Created by tianlin on 2017/4/27.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class MyAddrVo
{
    private int viewType;

    private String addrName;

    private boolean isMain = false;

    public boolean isMain()
    {
        return isMain;
    }

    public void setMain(boolean main)
    {
        isMain = main;
    }

    public int getViewType()
    {
        return viewType;
    }

    public void setViewType(int viewType)
    {
        this.viewType = viewType;
    }

    public String getAddrName()
    {
        return addrName;
    }

    public void setAddrName(String addrName)
    {
        this.addrName = addrName;
    }
}
