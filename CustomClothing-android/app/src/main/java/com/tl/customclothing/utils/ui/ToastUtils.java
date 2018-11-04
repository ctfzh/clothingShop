package com.tl.customclothing.utils.ui;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by tianlin on 2017/4/24.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class ToastUtils
{
    public static void show(Context context, String smg)
    {
        Toast.makeText(context, smg, Toast.LENGTH_SHORT).show();
    }

}
