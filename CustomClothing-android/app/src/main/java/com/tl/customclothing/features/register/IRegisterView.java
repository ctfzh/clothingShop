package com.tl.customclothing.features.register;

import com.tl.customclothing.features.base.IBaseView;

/**
 * Created by tianlin on 2017/4/8.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public interface IRegisterView extends IBaseView
{
    /**
     * 开始注册
     */
    void register();

    /**
     * 请求电话权限
     */
    void requestPhonePermissions();

    void goToLoginActivity();

}
