package com.tl.customclothing.features.register;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BaseActivity;
import com.tl.customclothing.http.request.RegisterRequest;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.encode.MD5;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/4/8.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class RegisterActivity extends BaseActivity implements IRegisterView
{
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_reg_username)
    EditText etRegUsername;
    @BindView(R.id.et_reg_password)
    EditText etRegPassword;
    @BindView(R.id.et_reg_re_password)
    EditText etRegRePassword;
    @BindView(R.id.et_reg_rel_name)
    EditText etRegRelName;
    @BindView(R.id.et_reg_nick_name)
    EditText etRegNickName;
    @BindView(R.id.rb_reg_male)
    RadioButton rbRegMale;
    @BindView(R.id.rb_reg_female)
    RadioButton rbRegFemale;
    @BindView(R.id.et_reg_user_addr)
    EditText etRegUserAddr;

    RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        // 请求电话权限
        requestPhonePermissions();

        registerPresenter = new RegisterPresenter();
        registerPresenter.attachView(this);

        initView();

    }

    @Override
    public void goToLoginActivity()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                finish();
            }
        }, 500);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        registerPresenter.detachView();
    }

    @Override
    public void requestPhonePermissions()
    {
        int result = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (result != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else
        {
            TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            etRegUsername.setText(tManager.getLine1Number());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i : grantResults)
        {
            if (i == PackageManager.PERMISSION_GRANTED)
            {
                TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                etRegUsername.setText(tManager.getLine1Number());
            }
        }
    }

    @Override
    public void initView()
    {
        tvToolbarTitle.setText(getString(R.string.register));
        rbRegMale.setChecked(true);
    }

    @OnClick({R.id.ll_toolbar_back, R.id.bt_reg_reg})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.ll_toolbar_back:

                finish();

                break;

            case R.id.bt_reg_reg:
                register();
                break;
        }
    }

    @Override
    public void register()
    {
        String username = etRegUsername.getText().toString();
        if (TextUtils.isEmpty(username))
        {
            response(this, R.string.please_input_username);
            etRegUsername.requestFocus();
            return;
        }

        String password = etRegPassword.getText().toString();
        if (TextUtils.isEmpty(password))
        {
            response(this, R.string.please_input_pwd);
            etRegPassword.requestFocus();
            return;
        }

        String rePassword = etRegRePassword.getText().toString();
        if (TextUtils.isEmpty(rePassword))
        {
            response(this, R.string.please_input_re_pwd);
            etRegRePassword.requestFocus();
            return;
        }

        if (!password.equals(rePassword))
        {
            response(this, R.string.password_are_not_the_same);
            etRegRePassword.requestFocus();
            return;
        }

        String relName = etRegRelName.getText().toString();
        if (TextUtils.isEmpty(relName))
        {
            response(this, R.string.please_input_relname);
            etRegRelName.requestFocus();
            return;
        }

        String nickName = etRegNickName.getText().toString();
        if (TextUtils.isEmpty(nickName))
        {
            response(this, R.string.please_input_nickname);
            etRegNickName.requestFocus();
            return;
        }

        String userAddr = etRegUserAddr.getText().toString();
        if (TextUtils.isEmpty(userAddr))
        {
            response(this, R.string.please_try_to_input_an_addr);
            etRegUserAddr.requestFocus();
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserLoginId(username);
        registerRequest.setUserNickName(nickName);
        registerRequest.setUserRelName(relName);

        if (rbRegMale.isChecked())
            registerRequest.setUserGender(Constant.MALE);
        else if (rbRegFemale.isChecked())
            registerRequest.setUserGender(Constant.FEMALE);
        registerRequest.setUserAddrMain(userAddr);

        registerRequest.setUserExp(Constant.INIT_JIFEN);
        registerRequest.setUserPwd(MD5.stringMD5(password));

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_TIME);

        registerRequest.setRegisterTime(sdf.format(new Date()));
        registerRequest.setLastUpdateTime(sdf.format(new Date()));

        registerPresenter.register(registerRequest);

    }
}
