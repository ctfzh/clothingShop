package com.tl.customclothing.features.login;

import android.content.Context;
import android.text.TextUtils;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BasePresenter;
import com.tl.customclothing.http.CCHttpHelper;
import com.tl.customclothing.http.config.LoginConfig;
import com.tl.customclothing.http.request.LoginRequest;
import com.tl.customclothing.http.response.LoginResponse;
import com.tl.customclothing.http.response.ResponseTemplate;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.data.DataProvider;
import com.tl.customclothing.utils.emotion.TextEmotion;
import com.tl.customclothing.utils.eventbus.BusEvents;
import com.tl.customclothing.utils.ui.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by tianlin on 2017/4/7.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class LoginPresenter extends BasePresenter<LoginActivity>
{
    @Override
    public void initData()
    {

    }

    // 重新获取用户数据,要求密码为空
    public static void refreshUserData(final Context context, final LoginRequest loginRequest, final boolean isNeedRefresh)
    {
        if (!TextUtils.isEmpty(loginRequest.getPassword()))
            return;

        CCHttpHelper
                .newInstance()
                .setThisReadLocal(false)
                .setBaseConfig(LoginConfig.class)
                .setRequest(loginRequest)
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
                        ToastUtils.show(context, context.getString(R.string.network_error));
                    }

                    @Override
                    public void onNext(ResponseTemplate responseTemplate)
                    {
                        if (responseTemplate == null)
                        {
                            ToastUtils.show(context, context.getString(R.string.network_error));
                            return;
                        }
                        ResponseTemplate<LoginResponse> response = responseTemplate;

                        LoginResponse loginResponse = response.getContent();

                        if (loginResponse == null)
                        {
                            ToastUtils.show(context, context.getString(R.string.network_error));
                            return;
                        }

                        if (Constant.ASYNC_SUCCESS.equals(loginResponse.getResponse()))
                        {
                            String password = DataProvider.getPwd(context);

                            if (!password.equals(loginResponse.getPassword()))
                            {
                                DataProvider.initUserData(context);
                                ToastUtils.show(context, context.getString(R.string.password_was_mod));
                                if (isNeedRefresh)
                                    EventBus.getDefault().post(new BusEvents.LoginEvent(Constant.USER_UPDATE));
                                return;
                            } else
                            {
                                // 保存用户信息
                                DataProvider.saveUserLoginId(context, loginResponse.getUserId());
                                DataProvider.savePwd(context, loginResponse.getPassword());

                                DataProvider.saveUserName(context, loginResponse.getUserNickName());
                                DataProvider.saveUserIconUrl(context, loginResponse.getUserIcon());
                                DataProvider.saveUserGender(context, loginResponse.getUserGender());
                                DataProvider.saveJifen(context, loginResponse.getJifen());
                                if (isNeedRefresh)
                                    EventBus.getDefault().post(new BusEvents.LoginEvent(Constant.USER_UPDATE));
                            }
                        } else
                        {
                            DataProvider.initUserData(context);
                            ToastUtils.show(context, context.getString(R.string.username_not_exist));
                            if (isNeedRefresh)
                                EventBus.getDefault().post(new BusEvents.LoginEvent(Constant.USER_UPDATE));
                        }
                    }
                });
    }

    public void login(final LoginRequest loginRequest)
    {
        view.showProgressDialog(view.getString(R.string.logining) + TextEmotion.qidai);

        CCHttpHelper
                .newInstance()
                .setThisReadLocal(false)
                .setBaseConfig(LoginConfig.class)
                .setRequest(loginRequest)
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
                        ResponseTemplate<LoginResponse> response = responseTemplate;

                        LoginResponse loginResponse = response.getContent();

                        if (loginResponse == null)
                        {
                            view.hideProgressDialog();
                            view.response(view, view.getString(R.string.network_error) + TextEmotion.ganga);
                            return;
                        }

                        if (Constant.LOGIN_SUCCESS.equals(loginResponse.getResponse()))
                        {
                            view.hideProgressDialog();
                            view.response(view, responseTemplate.getMsg());

                            // 保存用户信息
                            DataProvider.saveUserLoginId(view, loginResponse.getUserId());
                            DataProvider.savePwd(view, loginResponse.getPassword());
                            DataProvider.saveUserName(view, loginResponse.getUserNickName());
                            DataProvider.saveUserIconUrl(view, loginResponse.getUserIcon());
                            DataProvider.saveUserGender(view, loginResponse.getUserGender());
                            DataProvider.saveJifen(view, loginResponse.getJifen());

                            view.finish();
                            EventBus.getDefault().post(new BusEvents.LoginEvent(Constant.USER_UPDATE));
                        } else if (Constant.LOGIN_FAILED.equals(loginResponse.getResponse()))
                        {
                            view.hideProgressDialog();
                            view.response(view, responseTemplate.getMsg());
                        }
                    }
                });

    }
}
