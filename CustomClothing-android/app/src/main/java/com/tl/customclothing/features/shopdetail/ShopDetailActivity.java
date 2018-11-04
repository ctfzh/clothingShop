package com.tl.customclothing.features.shopdetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BaseActivity;
import com.tl.customclothing.features.tryon.TryOnActivity;
import com.tl.customclothing.model.dao.CartShopDao;
import com.tl.customclothing.model.database.CartShopRealm;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.NullUtils;
import com.tl.customclothing.utils.bitmap.DisplayImgOptionUtils;
import com.tl.customclothing.utils.data.DataProvider;
import com.tl.customclothing.utils.eventbus.BusEvents;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/4/17.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class ShopDetailActivity extends BaseActivity implements
        IShopDetailView,
        ImageLoadingListener
{
    public static final String INTENT_KEY_SHOP_ID = "INTENT_KEY_SHOP_ID";
    @BindView(R.id.rv_shop_detail)
    PullLoadMoreRecyclerView rvShopDetail;
    @BindView(R.id.ll_shop_detail_try_on)
    LinearLayout llShopDetailTryOn;
    @BindView(R.id.ll_shop_detail_add_to_cart)
    LinearLayout llShopDetailAddToCart;

    ShopDetailPresenter shopDetailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);

        initView();

        shopDetailPresenter = new ShopDetailPresenter();
        shopDetailPresenter.attachView(this);

        shopDetailPresenter.initData();
    }

    @Override
    public void setAdapter(List<ShopDetailVo> shopDetailVos)
    {
        ShopDetailAdapter adapter = new ShopDetailAdapter(this, shopDetailVos);
        rvShopDetail.setAdapter(adapter);


    }

    @Override
    protected void onDestroy()
    {
        shopDetailPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void initView()
    {
        rvShopDetail.setLinearLayout();
        rvShopDetail.setPullRefreshEnable(false);
        rvShopDetail.setPushRefreshEnable(false);
    }

    @Override
    public void onLoadingStarted(String s, View view)
    {
    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason)
    {
    }

    /**
     * @param s      url of the img
     * @param view
     * @param bitmap
     */
    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap)
    {
        Log.d("my", "s = " + s);
        Log.d("my", "bitmap w = " + bitmap.getWidth() + ", h = " + bitmap.getHeight());
    }

    @Override
    public void onLoadingCancelled(String s, View view)
    {
    }

    @Override
    public void loadModelImg(String url)
    {
        ImageLoader.getInstance().loadImage(Constant.BASE_URL + url, DisplayImgOptionUtils.loadModel(), this);
    }

    @OnClick({R.id.ll_shop_detail_try_on, R.id.ll_shop_detail_add_to_cart})
    public void onViewClicked(View view)
    {

        Log.d("my", "onViewClicked");
        ShopDetailAdapter shopDetailAdapter = (ShopDetailAdapter) rvShopDetail.getRecyclerView().getAdapter();
        if (shopDetailAdapter == null)
            return;
        List<ShopDetailVo> shopDetailVos = shopDetailAdapter.getShopDetailList();
        if (NullUtils.isEmpty(shopDetailVos))
            return;
        ShopDetailVo shopDetailVo = null;
        try
        {
            shopDetailVo = shopDetailVos.get(1);
        } catch (Exception e)
        {

        }
        if (shopDetailVo == null)
            return;

        switch (view.getId())
        {
            case R.id.ll_shop_detail_try_on:

                Intent intent = new Intent(this, TryOnActivity.class);
                intent.putExtra(TryOnActivity.INTENT_KEY_SHOPID, shopDetailVo.getShopId());
                intent.putExtra(TryOnActivity.INTENT_KEY_USERID_SALE, shopDetailVo.getUserIdSale());
                intent.putExtra(TryOnActivity.INTENT_KEY_IMG_ON_MODEL, shopDetailVo.getShopImgOnModel());
                intent.putExtra(TryOnActivity.INTENT_KEY_SHOP_TYPE, shopDetailVo.getShopType());
                intent.putExtra(TryOnActivity.INTENT_KEY_SHOP_GENDER, shopDetailVo.getShopGender());
                intent.putExtra(TryOnActivity.INTENT_KEY_SHOP_PRICE, shopDetailVo.getShopPrice());

                startActivity(intent);
                break;
            case R.id.ll_shop_detail_add_to_cart:

                String userGender = DataProvider.getUserGender(this);

                if (!userGender.equals(shopDetailVo.getShopGender()))
                {
                    response(this, R.string.not_match_your_gender);
                    return;
                }

                CartShopRealm cartShopRealm = new CartShopRealm();

                cartShopRealm.setShopId(shopDetailVo.getShopId());
                cartShopRealm.setShopType(shopDetailVo.getShopType());
                cartShopRealm.setShopGender(shopDetailVo.getShopGender());

                cartShopRealm.setShopMainImg(shopDetailVo.getShopMainImg());
                cartShopRealm.setShopPrice(shopDetailVo.getShopPrice());
                cartShopRealm.setShopSalesCount(shopDetailVo.getShopSalesCount());
                cartShopRealm.setTime(System.currentTimeMillis());

                cartShopRealm.setImgOnModel(shopDetailVo.getShopImgOnModel());
                cartShopRealm.setUserIdSale(shopDetailVo.getUserIdSale());

                CartShopDao.insert(cartShopRealm);

                EventBus.getDefault().post(new BusEvents.CartRefreshEvent(Constant.ADD_TO_CART_EVENT));

                response(this, R.string.add_to_cart_success);

                break;
        }
    }
}
