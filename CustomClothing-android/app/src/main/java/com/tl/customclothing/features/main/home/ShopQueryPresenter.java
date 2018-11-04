package com.tl.customclothing.features.main.home;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BasePresenter;
import com.tl.customclothing.http.CCHttpHelper;
import com.tl.customclothing.http.config.QueryShopConfig;
import com.tl.customclothing.http.request.QueryShopRequest;
import com.tl.customclothing.http.response.QueryShopResponse;
import com.tl.customclothing.http.response.QueryShopResponseItem;
import com.tl.customclothing.http.response.ResponseTemplate;
import com.tl.customclothing.utils.NullUtils;
import com.tl.customclothing.utils.emotion.TextEmotion;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by tianlin on 2017/4/10.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class ShopQueryPresenter extends BasePresenter<ShopQueryActivity>
{
    public int cur_page = 0;

    public int pageCount = 1;

    @Override
    public void initData()
    {
    }

    /**
     * @param key 查询的关键字
     */
    public void loadHome(String key, final boolean reloadData)
    {
        if (view == null)
            return;

        if (reloadData)
        {
            cur_page = 1;
        } else if (cur_page++ > pageCount)
        {
            view.response(view, R.string.no_more_data);
            view.stopRefresh();
            return;
        }

        QueryShopRequest queryShopRequest = new QueryShopRequest();
        queryShopRequest.setKey(key);
        queryShopRequest.setPage(String.valueOf(cur_page));

        CCHttpHelper
                .newInstance()
                .setBaseConfig(QueryShopConfig.class)
                .setRequest(queryShopRequest)
                .setThisReadLocal(true)
                .getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseTemplate>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        if (view == null)
                            return;
                        view.response(view, view.getString(R.string.network_error) + TextEmotion.ganga);
                        view.stopRefresh();
                    }

                    @Override
                    public void onNext(ResponseTemplate responseTemplate)
                    {
                        if (view == null)
                            return;

                        List<HomeVo> homeVos = new ArrayList<>();

                        if (responseTemplate == null)
                        {
                            view.response(view, view.getString(R.string.network_error) + TextEmotion.ganga);
                            view.stopRefresh();
                            return;
                        }

                        ResponseTemplate<QueryShopResponse> shopResponseTemplate = responseTemplate;

                        QueryShopResponse queryShopResponse = shopResponseTemplate.getContent();

                        if (queryShopResponse == null)
                        {
                            view.stopRefresh();
                            view.response(view, view.getString(R.string.no_more_data) + TextEmotion.ganga);
                            return;
                        }

                        List<QueryShopResponseItem> responseItemList = queryShopResponse.getShops();

                        if (NullUtils.isEmpty(responseItemList))
                        {
                            view.stopRefresh();
                            view.response(view, view.getString(R.string.no_more_data) + TextEmotion.ganga);
                            return;
                        }

                        for (int i = 0; i < responseItemList.size(); i++)
                        {
                            HomeVo homeVo = new HomeVo();
                            homeVo.viewType = ShopAdapter.VIEW_TYPE_ITEM_SHOP;
                            homeVo.queryShopResponseItem = responseItemList.get(i);
                            homeVos.add(homeVo);
                        }

                        pageCount = queryShopResponse.getPageCount();

                        view.setHomeAdapter(homeVos, reloadData);
                        view.stopRefresh();

                    }
                });

    }
}
