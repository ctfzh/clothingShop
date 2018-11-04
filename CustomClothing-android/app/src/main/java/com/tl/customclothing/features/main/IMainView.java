package com.tl.customclothing.features.main;

import android.support.v4.app.FragmentTransaction;

import com.tl.customclothing.features.base.IBaseView;

import java.util.List;

/**
 * Created by tianlin on 2017/4/6.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public interface IMainView extends IBaseView
{

    /**
     * 设置广告图片
     *
     * @param imgs
     */
    void setImages(List<String> imgs);

    /**
     * 先隐藏所有的fragment
     *
     * @param fragmentTransaction
     */
    void hideAllFragment(FragmentTransaction fragmentTransaction);

    /**
     * 请求存贮权限
     */
    void requestSdcardPermission();

    /**
     * 跳转到指定的fragment
     *
     * @param id
     */
    void switchToFragment(FragmentTransaction fragmentTransaction, int id);

}
