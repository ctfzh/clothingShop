package com.tl.customclothing.features.shopdetail;

import com.tl.customclothing.features.base.IBaseView;

import java.util.List;

/**
 * Created by tianlin on 2017/4/17.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public interface IShopDetailView extends IBaseView
{
    void setAdapter(List<ShopDetailVo> shopDetailVos);

    void loadModelImg(String url);
}
