package com.tl.customclothing.features.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlin on 2017/3/31.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class ActivityList
{

    private static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }

    public static void finishAll()
    {
        for (Activity activity : activities)
        {
            if (activity != null && !activity.isFinishing())
            {
                activity.finish();
            }
        }
    }

}
