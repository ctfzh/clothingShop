package com.tl.customclothing.features.tryon;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BasePresenter;
import com.tl.customclothing.http.CCHttpHelper;
import com.tl.customclothing.http.config.SubmitOrderConfig;
import com.tl.customclothing.http.request.SubmitOrderRequest;
import com.tl.customclothing.http.response.ResponseTemplate;
import com.tl.customclothing.http.response.SubmitOrderResponse;
import com.tl.customclothing.model.dao.CartShopDao;
import com.tl.customclothing.model.database.CartShopRealm;
import com.tl.customclothing.utils.emotion.TextEmotion;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by tianlin on 2017/4/21.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class TryOnPresenter extends BasePresenter<TryOnActivity>
{
    @Override
    public void initData()
    {
        List<CartShopRealm> shopRealms = CartShopDao.queryAll();
        view.setCartAdapter(shopRealms);
    }

    public void submitOrder(SubmitOrderRequest request)
    {
        view.response(view, R.string.order_uploading_now);
        CCHttpHelper
                .newInstance()
                .setBaseConfig(SubmitOrderConfig.class)
                .setRequest(request)
                .setThisReadLocal(false)
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
                    }

                    @Override
                    public void onNext(ResponseTemplate responseTemplate)
                    {
                        if (view == null)
                            return;

                        if (responseTemplate == null)
                        {
                            view.response(view, view.getString(R.string.network_error) + TextEmotion.ganga);
                            return;
                        }

                        ResponseTemplate<SubmitOrderResponse> response = responseTemplate;

                        SubmitOrderResponse submitOrderResponse = response.getContent();

                        if (submitOrderResponse == null)
                        {
                            view.response(view, view.getString(R.string.network_error) + TextEmotion.ganga);
                            return;
                        }

                        view.response(view, submitOrderResponse.getResponse());
                    }
                });

    }
}
