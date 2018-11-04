package com.tl.customclothing.http;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.tl.customclothing.http.config.BaseConfig;
import com.tl.customclothing.http.request.BaseRequest;
import com.tl.customclothing.http.request.RequestTemplate;
import com.tl.customclothing.http.response.ResponseTemplate;
import com.tl.customclothing.model.dao.RequestCacheDao;
import com.tl.customclothing.model.database.RequestCacheRealm;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.json.ConverterFastJson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by tianlin on 2017/4/14.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class CCHttpHelper
{
    // 读取本地是否成功，用于看本次是否有必要缓存到本地
    private boolean isGetLocalSuccess = false;

    // http服务
    private static CCHttpService httpService;

    // 该请求的基本配置
    private BaseConfig baseConfig;
    // 本次请求书否读取本地，用于强制刷新
    private boolean isThisReadLocal = false;
    // 请求参数模板
    private RequestTemplate requestTemplate;

    public CCHttpHelper setThisReadLocal(boolean thisReadLocal)
    {
        isThisReadLocal = thisReadLocal;
        return this;
    }

    public CCHttpHelper setBaseConfig(Class<? extends BaseConfig> clazz)
    {
        try
        {
            this.baseConfig = clazz.newInstance();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return this;
    }

    public CCHttpHelper setRequest(BaseRequest request)
    {
        this.requestTemplate = new RequestTemplate();
        requestTemplate.setContent(request);
        return this;
    }

    private static CCHttpService getCCHttpService()
    {
        if (httpService == null)
        {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);    // 监听请求的body

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)   // 最大连接时长
                    .readTimeout(60, TimeUnit.SECONDS)      // 最大读取时长
                    .addInterceptor(interceptor)             // 监听body
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(new ConverterFastJson())           // 添加两个转换器，依次执行
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            httpService = retrofit.create(CCHttpService.class);

        }
        return httpService;
    }

    public static CCHttpHelper newInstance()
    {
        CCHttpHelper helper = new CCHttpHelper();
        return helper;
    }

    public Observable<ResponseTemplate> getData()
    {
        requestTemplate.setServlet(baseConfig.servlet);

        Observable<String> observableLocal =
                Observable
                        .just(baseConfig.servlet)
                        .subscribeOn(Schedulers.io())
                        .map(new Func1<String, String>()
                        {
                            @Override
                            public String call(String s)
                            {
                                Log.e("my", "observableLocal 接口请求开始 " + requestTemplate.toString());

                                if (!baseConfig.isCached)
                                {
                                    // 该接口的数据不保存到本地
                                    Log.e("my", "observableLocal 接口不緩存到本地");
                                    return null;
                                }

                                if (!isThisReadLocal)
                                {
                                    // 本次访问不获取本地数据
                                    Log.e("my", "observableLocal 接口緩存緩存到本地，但本次不读取缓存");
                                    return null;
                                }

                                Log.e("my", "observableLocal 接口开始读取缓存");

                                String localJson = null;

                                // 请求的具体内容
                                String request = JSON.toJSONString(requestTemplate.getContent());
                                // 请求对应的servlet + 请求内容作为key
                                String key = baseConfig.servlet + request;

                                RequestCacheRealm requestCacheRealm = RequestCacheDao.queryByKey(key);

                                if (requestCacheRealm != null)
                                {
                                    long cacheTime = baseConfig.cacheTime;
                                    long savedTime = requestCacheRealm.getCacheTime();
                                    long timePeriod = System.currentTimeMillis() - savedTime;

                                    if (cacheTime > timePeriod)
                                    {
                                        localJson = requestCacheRealm.getResponse();
                                        Log.e("my", "observableLocal 读取缓存成功 = " + localJson);
                                    } else
                                    {
                                        Log.e("my", "observableLocal 缓存时间已过期");
                                    }
                                } else
                                {
                                    Log.e("my", "observableLocal 本地还没有缓存");
                                }
                                return localJson;
                            }
                        });

        Observable<String> observableNet = observableLocal
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<String, Observable<String>>()
                {
                    @Override
                    public Observable<String> call(String localJson)
                    {
                        if (TextUtils.isEmpty(localJson))
                        {
                            Log.e("my", "observableNet 本地没有数据,从网上请求数据");
                            isGetLocalSuccess = false;

                            // 将整个requestTemplate发送给服务端
                            String requestJson = JSON.toJSONString(requestTemplate);
                            Observable<String> observable = getCCHttpService().post(
                                    baseConfig.servlet,
                                    requestJson
                            );
                            return observable;
                        } else
                        {
                            Log.e("my", "observableNet 加载本地成功，不用网络请求数据");
                            isGetLocalSuccess = true;
                            Observable<String> observable = Observable.just(localJson);
                            return observable;
                        }
                    }
                });

        Observable<ResponseTemplate> observable = observableNet
                .map(new Func1<String, ResponseTemplate>()
                {
                    @Override
                    public ResponseTemplate call(String json)
                    {
                        if (TextUtils.isEmpty(json))
                            return null;

                        // 获取本地失败，说明此次不获取本地，需要从网络请求数据
                        if (!isGetLocalSuccess)
                        {
                            if (baseConfig.isCached)
                            {
                                // 如果本次请求需要缓存到本地，就缓存
                                RequestCacheRealm requestCacheRealm = new RequestCacheRealm();

                                String request = JSON.toJSONString(requestTemplate.getContent());
                                String key = baseConfig.servlet + request;

                                requestCacheRealm.setKey(key);
                                requestCacheRealm.setCacheTime(System.currentTimeMillis());
                                requestCacheRealm.setRequest(request);
                                requestCacheRealm.setResponse(json);

                                RequestCacheDao.insert(requestCacheRealm);
                                Log.e("my", "observable 接口需要缓存到本地 = " + json);
                            } else
                            {
                                Log.e("my", "observable 接口不缓存到本地 = " + json);
                            }
                        } else
                        {
                            Log.e("my", "observable 本接口加载缓存成功，不用重新缓存");
                        }
                        // 重写该方法获取最终得到的对象
                        ResponseTemplate responseTemplate = baseConfig.parseToObject(json);

                        return responseTemplate;
                    }
                });
        return observable;
    }

}
