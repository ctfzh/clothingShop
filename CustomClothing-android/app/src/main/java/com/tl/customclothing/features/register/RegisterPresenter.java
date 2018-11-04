package com.tl.customclothing.features.register;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BasePresenter;
import com.tl.customclothing.http.CCHttpHelper;
import com.tl.customclothing.http.config.RegisterConfig;
import com.tl.customclothing.http.request.RegisterRequest;
import com.tl.customclothing.http.response.RegisterResponse;
import com.tl.customclothing.http.response.ResponseTemplate;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.emotion.TextEmotion;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by tianlin on 2017/4/11.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class RegisterPresenter extends BasePresenter<RegisterActivity>
{
    @Override
    public void initData()
    {
    }

    public void register(final RegisterRequest registerRequest)
    {
        view.showProgressDialog(view.getString(R.string.registering) + TextEmotion.qidai);

        CCHttpHelper
                .newInstance()
                .setBaseConfig(RegisterConfig.class)
                .setRequest(registerRequest)
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

                        view.hideProgressDialog();
                        view.response(view, view.getString(R.string.network_error) + TextEmotion.ganga);
                    }

                    @Override
                    public void onNext(ResponseTemplate responseTemplate)
                    {

                        if (view == null)
                            return;

                        if (responseTemplate == null)
                        {
                            view.hideProgressDialog();
                            view.response(view, view.getString(R.string.network_error) + TextEmotion.ganga);
                            return;
                        }
                        ResponseTemplate<RegisterResponse> response = responseTemplate;

                        RegisterResponse registerResponse = response.getContent();

                        if (registerResponse == null)
                        {
                            view.hideProgressDialog();
                            view.response(view, view.getString(R.string.network_error) + TextEmotion.ganga);
                            return;
                        }

                        if (Constant.REGISTER_SUCCESS.equals(registerResponse.getResponse()))
                        {
                            view.hideProgressDialog();
                            view.response(view, responseTemplate.getMsg());
                            view.goToLoginActivity();
                        } else if (Constant.REGISTER_FAILED.equals(registerResponse.getResponse()))
                        {
                            view.hideProgressDialog();
                            view.response(view, responseTemplate.getMsg());
                        }
                    }
                });
    }
}
