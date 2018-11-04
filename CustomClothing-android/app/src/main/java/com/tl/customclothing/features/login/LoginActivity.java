package com.tl.customclothing.features.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BaseActivity;
import com.tl.customclothing.features.register.RegisterActivity;
import com.tl.customclothing.http.request.LoginRequest;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.data.DataProvider;
import com.tl.customclothing.utils.encode.MD5;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/4/7.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class LoginActivity extends BaseActivity implements ILoginView
{
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;
    @BindView(R.id.ll_toolbar_back)
    LinearLayout llToolbarBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.iv_login_user_icon)
    ImageView ivLoginUserIcon;

    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);

        initView();
    }

    @Override
    public void initView()
    {
        tvToolbarTitle.setText(getString(R.string.login));
        etLoginUsername.requestFocus();

        String userId = DataProvider.getUserLoginId(this);
        if (!Constant.NULL.equals(userId))
        {
            etLoginUsername.setText(userId);
            etLoginPassword.requestFocus();
        }
    }

    @OnClick(R.id.tv_login_register)
    public void tv_login_register()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.ll_toolbar_back, R.id.bt_login_login})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.ll_toolbar_back:
                finish();
                break;
            case R.id.bt_login_login:

                String userId = etLoginUsername.getText().toString();
                String password = etLoginPassword.getText().toString();

                if (TextUtils.isEmpty(userId))
                {
                    response(this, R.string.please_input_username);
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    response(this, R.string.please_input_pwd);
                    return;
                }

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUsername(userId);
                loginRequest.setPassword(MD5.stringMD5(password));
                loginPresenter.login(loginRequest);

                break;
        }
    }
}
