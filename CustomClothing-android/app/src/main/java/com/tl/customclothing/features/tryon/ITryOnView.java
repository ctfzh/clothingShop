package com.tl.customclothing.features.tryon;

import com.tl.customclothing.features.base.IBaseView;
import com.tl.customclothing.model.database.CartShopRealm;

import java.util.List;

/**
 * Created by tianlin on 2017/4/20.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public interface ITryOnView extends IBaseView
{
    void initSeekBar();

    void adjustModel();

    void initModel();

    void showCart();

    void hideCart();

    void setCartAdapter(List<CartShopRealm> cartShopRealms);

}
