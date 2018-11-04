package com.tl.customclothing.utils.bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.tl.customclothing.R;

/**
 * Created by tianlin on 2017/4/8.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class DisplayImgOptionUtils
{
    public static DisplayImageOptions getUserIconDio()
    {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)                       // 内存缓存
                .imageScaleType(ImageScaleType.EXACTLY)     // 图片放缩类型
                .cacheOnDisk(true)                          // 文件缓存
                .displayer(new RoundedBitmapDisplayer(20))
                .showImageOnFail(R.drawable.ic_no_login)
                .build();
        return displayImageOptions;
    }

    public static DisplayImageOptions getShopDio()
    {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)                       // 内存缓存
                .imageScaleType(ImageScaleType.EXACTLY)     // 图片放缩类型
                .cacheOnDisk(true)                          // 文件缓存
                .showImageOnFail(R.drawable.ic_error_red_500_36dp)
                .showImageOnLoading(R.drawable.shop_lodding)
                .build();
        return displayImageOptions;
    }

    public static DisplayImageOptions getCartShopDio()
    {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)                       // 内存缓存
                .imageScaleType(ImageScaleType.EXACTLY)     // 图片放缩类型
                .cacheOnDisk(true)                          // 文件缓存
                .build();
        return displayImageOptions;
    }

    public static DisplayImageOptions getAdsDio()
    {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)                       // 内存缓存
                .imageScaleType(ImageScaleType.EXACTLY)     // 图片放缩类型
                .cacheOnDisk(true)                          // 文件缓存
                .showImageOnFail(R.drawable.ic_error_red_500_36dp)
                .build();
        return displayImageOptions;
    }

    public static DisplayImageOptions loadModel()
    {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)                       // 内存缓存
                .cacheOnDisk(true)                          // 文件缓存
                .build();
        return displayImageOptions;
    }

}
