package com.tl.customclothing.features.myaddr;

import android.text.TextUtils;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BasePresenter;
import com.tl.customclothing.http.CCHttpHelper;
import com.tl.customclothing.http.config.QueryUserAddrConfig;
import com.tl.customclothing.http.config.UpdateUserAddrConfig;
import com.tl.customclothing.http.request.QueryUserAddrRequest;
import com.tl.customclothing.http.request.UpdateUserAddrRequest;
import com.tl.customclothing.http.response.QueryUserAddrResponse;
import com.tl.customclothing.http.response.ResponseTemplate;
import com.tl.customclothing.http.response.UpdateUserAddrResponse;
import com.tl.customclothing.utils.data.DataProvider;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by tianlin on 2017/4/27.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class MyAddrPresenter extends BasePresenter<MyAddrActivity>
{
    @Override
    public void initData()
    {
        getData();
    }

    public void updateAddr(UpdateUserAddrRequest request)
    {

        view.showProgressDialog(R.string.lodding);
        CCHttpHelper
                .newInstance()
                .setThisReadLocal(false)
                .setRequest(request)
                .setBaseConfig(UpdateUserAddrConfig.class)
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

                        ResponseTemplate<UpdateUserAddrResponse> response = responseTemplate;

                        UpdateUserAddrResponse updateUserAddrResponse = response.getContent();

                        if (updateUserAddrResponse == null)
                        {
                            view.response(view, R.string.network_error);
                            view.hideProgressDialog();
                            return;
                        }

                        view.response(view, updateUserAddrResponse.getResponse());
                        view.hideProgressDialog();
                        getData();

                    }
                });
    }

    public void getData()
    {
        view.showProgressDialog(R.string.lodding);

        QueryUserAddrRequest queryUserAddrRequest = new QueryUserAddrRequest();
        queryUserAddrRequest.setUserId(DataProvider.getUserLoginId(view));

        CCHttpHelper
                .newInstance()
                .setRequest(queryUserAddrRequest)
                .setBaseConfig(QueryUserAddrConfig.class)
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

                        ResponseTemplate<QueryUserAddrResponse> response = responseTemplate;

                        QueryUserAddrResponse queryUserAddrResponse = response.getContent();

                        if (queryUserAddrResponse == null)
                        {
                            view.response(view, R.string.network_error);
                            view.hideProgressDialog();
                            return;
                        }

                        List<MyAddrVo> myAddrVos = new ArrayList<>();

                        if (!TextUtils.isEmpty(queryUserAddrResponse.getUserAddrMain()))
                        {
                            MyAddrVo myAddrVo = new MyAddrVo();
                            myAddrVo.setViewType(MyAddrAdapter.VIEW_TYPE_ADDR);
                            myAddrVo.setAddrName(queryUserAddrResponse.getUserAddrMain());
                            myAddrVos.add(myAddrVo);
                        }

                        if (!TextUtils.isEmpty(queryUserAddrResponse.getUserAddr1()))
                        {
                            MyAddrVo myAddrVo = new MyAddrVo();
                            myAddrVo.setViewType(MyAddrAdapter.VIEW_TYPE_ADDR);
                            myAddrVo.setAddrName(queryUserAddrResponse.getUserAddr1());
                            myAddrVos.add(myAddrVo);
                        }

                        if (!TextUtils.isEmpty(queryUserAddrResponse.getUserAddr2()))
                        {
                            MyAddrVo myAddrVo = new MyAddrVo();
                            myAddrVo.setViewType(MyAddrAdapter.VIEW_TYPE_ADDR);
                            myAddrVo.setAddrName(queryUserAddrResponse.getUserAddr2());
                            myAddrVos.add(myAddrVo);
                        }

                        view.setAdapter(myAddrVos);
                        view.hideProgressDialog();

                    }
                });
    }

}
