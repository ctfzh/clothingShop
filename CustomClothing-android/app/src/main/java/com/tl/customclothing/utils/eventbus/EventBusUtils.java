package com.tl.customclothing.utils.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by tianlin on 2017/4/14.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class EventBusUtils
{
    public static void register(Object object)
    {
        if (!EventBus.getDefault().isRegistered(object))
            EventBus.getDefault().register(object);
    }

    public static void unRegister(Object object)
    {
        if (EventBus.getDefault().isRegistered(object))
            EventBus.getDefault().unregister(object);
    }
}
