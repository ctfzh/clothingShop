package com.tl.customclothing.features.ordersearch.pager;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BasePresenter;
import com.tl.customclothing.http.CCHttpHelper;
import com.tl.customclothing.http.config.PostCommentConfig;
import com.tl.customclothing.http.request.PostCommentRequest;
import com.tl.customclothing.http.response.PostCommentResponse;
import com.tl.customclothing.http.response.ResponseTemplate;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.eventbus.BusEvents;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by tianlin on 2017/4/26.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class OrderPagePresenter extends BasePresenter<OrderPageFragment>
{
    @Override
    public void initData()
    {

    }

    public void postComment(PostCommentRequest request)
    {

        view.showProgressDialog(R.string.uploading);
        CCHttpHelper
                .newInstance()
                .setThisReadLocal(false)
                .setBaseConfig(PostCommentConfig.class)
                .setRequest(request)
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

                        view.response(view.getActivity(), view.getString(R.string.network_error));
                        view.hideProgressDialog();
                    }

                    @Override
                    public void onNext(ResponseTemplate responseTemplate)
                    {

                        if (view == null)
                            return;

                        if (responseTemplate == null)
                        {
                            view.response(view.getActivity(), view.getString(R.string.network_error));
                            view.hideProgressDialog();
                            return;
                        }

                        ResponseTemplate<PostCommentResponse> response = responseTemplate;

                        PostCommentResponse commentResponse = response.getContent();

                        if (commentResponse == null)
                        {
                            view.response(view.getActivity(), view.getString(R.string.network_error));
                            view.hideProgressDialog();
                            return;
                        }

                        view.response(view.getActivity(), commentResponse.getResponse());
                        view.hideProgressDialog();
                        EventBus.getDefault().post(new BusEvents.OrderSearchRefreshEvent(Constant.ORDER_SEARCH_REFRESH_EVENT));

                    }
                });
    }


}
