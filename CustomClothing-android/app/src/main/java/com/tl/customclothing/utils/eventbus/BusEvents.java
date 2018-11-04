package com.tl.customclothing.utils.eventbus;

/**
 * Created by tianlin on 2017/4/14.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class BusEvents
{
    public static class LoginEvent
    {
        public String what = "";

        public LoginEvent(String what)
        {
            this.what = what;
        }
    }

    public static class CartRefreshEvent
    {
        public String what = "";

        public CartRefreshEvent(String what)
        {
            this.what = what;
        }
    }

    public static class OrderSearchRefreshEvent
    {
        public String what = "";

        public OrderSearchRefreshEvent(String what)
        {
            this.what = what;
        }
    }
}
