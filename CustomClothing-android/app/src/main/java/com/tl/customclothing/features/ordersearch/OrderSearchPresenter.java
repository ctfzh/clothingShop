package com.tl.customclothing.features.ordersearch;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BasePresenter;
import com.tl.customclothing.features.ordersearch.pager.OrderPageAdapter;
import com.tl.customclothing.features.ordersearch.pager.OrderPageVo;
import com.tl.customclothing.http.CCHttpHelper;
import com.tl.customclothing.http.config.OrderSearchConfig;
import com.tl.customclothing.http.request.OrderSearchRequest;
import com.tl.customclothing.http.response.OrderSearchResponse;
import com.tl.customclothing.http.response.OrderSearchResponseItem;
import com.tl.customclothing.http.response.ResponseTemplate;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.NullUtils;
import com.tl.customclothing.utils.data.DataProvider;
import com.tl.customclothing.utils.emotion.TextEmotion;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by tianlin on 2017/4/25.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class OrderSearchPresenter extends BasePresenter<OrderSearchActivity>
{
    List<String> keys = null;

    @Override
    public void initData()
    {
        keys = new ArrayList<>();
        keys.add(Constant.ORDER_STATUS_TO_DEAL);
        keys.add(Constant.ORDER_STATUS_DEALING);
        keys.add(Constant.ORDER_STATUS_RECEIVING);
        keys.add(Constant.ORDER_STATUS_COMMENTING);
        keys.add(Constant.ORDER_STATUS_FINISHING);
    }

    public void getData(final int position, boolean isReadLocal)
    {
        if (view == null)
            return;

        if (keys == null)
            return;

        view.showProgressDialog(R.string.lodding);

        final OrderSearchRequest orderSearchRequest = new OrderSearchRequest();
        orderSearchRequest.setUserIdBuy(DataProvider.getUserLoginId(view));
        orderSearchRequest.setOrderStatus(keys.get(position));

        CCHttpHelper
                .newInstance()
                .setThisReadLocal(isReadLocal)
                .setBaseConfig(OrderSearchConfig.class)
                .setRequest(orderSearchRequest)
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

                        view.response(view, R.string.network_error);
                        view.hideProgressDialog();
                    }

                    @Override
                    public void onNext(ResponseTemplate responseTemplate)
                    {
                        if (view == null)
                            return;

                        if (responseTemplate == null)
                        {
                            view.response(view, R.string.network_error);
                            view.hideProgressDialog();
                            return;
                        }

                        ResponseTemplate<OrderSearchResponse> response = responseTemplate;

                        OrderSearchResponse orderSearchResponse = response.getContent();

                        if (orderSearchRequest == null)
                        {
                            view.response(view, R.string.no_data);
                            view.hideProgressDialog();
                            return;
                        }

                        List<OrderSearchResponseItem> orderSearchResponseItems = orderSearchResponse.getOrders();

                        List<OrderPageVo> orderPageVos = new ArrayList<>();

                        if (NullUtils.isEmpty(orderSearchResponseItems))
                        {
                            OrderPageVo orderPageVo = new OrderPageVo();
                            orderPageVo.setViewType(OrderPageAdapter.VIEW_TYPE_NO_DATA);
                            orderPageVo.setNoDataDesc(view.getString(R.string.no_order) + TextEmotion.bishi);
                            orderPageVos.add(orderPageVo);
                        } else
                        {
                            for (int i = 0; i < orderSearchResponseItems.size(); i++)
                            {
                                OrderPageVo orderPageVo = new OrderPageVo();
                                orderPageVo.setViewType(OrderPageAdapter.VIEW_TYPE_ORDER_ITEM);
                                orderPageVo.setItem(orderSearchResponseItems.get(i));
                                orderPageVos.add(orderPageVo);
                            }
                        }

                        view.setData(position, orderPageVos);
                        view.hideProgressDialog();
                    }
                });


    }
}
