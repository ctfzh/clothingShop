package com.tl.customclothing.app;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tl.customclothing.features.login.LoginPresenter;
import com.tl.customclothing.http.request.LoginRequest;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.data.DataProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by tianlin on 2017/3/31.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class CustomClothingApplication extends Application
{
    private static final int IMAGE_LOADER_THREAD_SIZE = 3;
    private static final String IMAGE_CACHE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CustomClothing/cache/imgs";
    private static final int DISK_CACHE_SIZE = 500 * 1024 * 1024;

    @Override
    public void onCreate()
    {
        super.onCreate();
        initImageLoader();
        initRealm();

        // 预登陆
        initUser();
    }

    private void initUser()
    {
        String userId = DataProvider.getUserLoginId(this);
        if (!Constant.NULL.equals(userId))
        {
            LoginRequest request = new LoginRequest();
            request.setUsername(userId);
            LoginPresenter.refreshUserData(this, request, false);
        }
    }

    private void initRealm()
    {
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded() // 数据库结构改变时自动删除数据库的表
                .schemaVersion(2)
                .build();

        Realm.setDefaultConfiguration(config);
    }

    private void initImageLoader()
    {
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration
                .Builder(this)
                .threadPoolSize(IMAGE_LOADER_THREAD_SIZE)       // 线程池线程数量
                .threadPriority(Thread.NORM_PRIORITY - 1)         // 线程优先级
                .tasksProcessingOrder(QueueProcessingType.LIFO)     // 任务序列
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())    // 缓存文件名
                .diskCache(new UnlimitedDiskCache(StorageUtils.getOwnCacheDirectory(this, IMAGE_CACHE)))    // 缓存文件位置
                .diskCacheSize(DISK_CACHE_SIZE)                     // 缓存目录大小
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);

    }

}
