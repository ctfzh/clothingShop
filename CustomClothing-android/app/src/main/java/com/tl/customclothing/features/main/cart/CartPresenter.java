package com.tl.customclothing.features.main.cart;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BasePresenter;
import com.tl.customclothing.model.dao.CartShopDao;
import com.tl.customclothing.model.database.CartShopRealm;
import com.tl.customclothing.utils.NullUtils;
import com.tl.customclothing.utils.emotion.TextEmotion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlin on 2017/4/24.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class CartPresenter extends BasePresenter<CartFragment>
{
    @Override
    public void initData()
    {
        view.showProgressDialog(R.string.lodding);
        List<CartShopRealm> cartShopRealms = CartShopDao.queryAll();

        List<CartVo> cartVoList = new ArrayList<>();
        if (NullUtils.isEmpty(cartShopRealms))
        {
            view.setLinear();
            CartVo cartVo = new CartVo();
            cartVo.setViewType(CartAdapter.VIEW_TYPE_NO_DATA);
            cartVo.setNoDataDesc(view.getString(R.string.empty_cart) + TextEmotion.bishi);
            cartVoList.add(cartVo);
        } else
        {
            view.setFlow();

            for (int i = 0; i < cartShopRealms.size(); i++)
            {
                CartShopRealm cartShopRealm = cartShopRealms.get(i);
                CartVo cartVo = new CartVo();

                cartVo.setViewType(CartAdapter.VIEW_TYPE_SHOP_ITEM);
                cartVo.setShopMainImg(cartShopRealm.getShopMainImg());
                cartVo.setShopId(cartShopRealm.getShopId());
                cartVo.setShopPrice(cartShopRealm.getShopPrice());
                cartVo.setShopSalesCount(cartShopRealm.getShopSalesCount());
                cartVo.setShopGender(cartShopRealm.getShopGender());
                cartVo.setShopType(cartShopRealm.getShopType());

                cartVoList.add(cartVo);
            }
        }

        view.setAdapter(cartVoList);

        view.hideProgressDialog();

    }
}
