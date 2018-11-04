package com.tl.customclothing.features.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BaseActivity;
import com.tl.customclothing.features.shopdetail.ShopDetailActivity;
import com.tl.customclothing.utils.ui.RecyclerDivider;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/5/24.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class ShopQueryActivity extends BaseActivity implements IShopQueryView, ShopAdapter.OnHomeItemClickListener,
        PullLoadMoreRecyclerView.PullLoadMoreListener
{

    @BindView(R.id.ll_toolbar_back)
    LinearLayout llToolbarBack;
    @BindView(R.id.actv_key)
    AutoCompleteTextView actvKey;
    @BindView(R.id.tv_toolbar_right_text)
    TextView tvToolbarRightText;
    @BindView(R.id.ll_toolbar_right_text)
    LinearLayout llToolbarRightText;
    @BindView(R.id.ll_toolbar)
    RelativeLayout llToolbar;
    @BindView(R.id.recycle_home)
    PullLoadMoreRecyclerView recycleQueryShop;

    ShopQueryPresenter shopQueryPresenter;

    String key;

    private ShopAdapter shopAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_query);
        ButterKnife.bind(this);

        initView();
        shopQueryPresenter = new ShopQueryPresenter();
        shopQueryPresenter.attachView(this);

    }

    @Override
    public void initView()
    {
        recycleQueryShop.setLinearLayout();
        recycleQueryShop.setPullRefreshEnable(true);
        recycleQueryShop.setOnPullLoadMoreListener(this);
        RecyclerDivider divider = new RecyclerDivider(this, RecyclerDivider.VERTICAL_LIST);
        divider.setPaddingLeft((int) getResources().getDimension(R.dimen.horizontal_margin));
        divider.setPaddingRight((int) getResources().getDimension(R.dimen.horizontal_margin));
        recycleQueryShop.getRecyclerView().addItemDecoration(divider);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        shopQueryPresenter.detachView();
    }

    @Override
    public void onItemClick(int shopId)
    {
        Intent intent = new Intent(this, ShopDetailActivity.class);
        intent.putExtra(ShopDetailActivity.INTENT_KEY_SHOP_ID, shopId);
        startActivity(intent);
    }

    @Override
    public void setHomeAdapter(List<HomeVo> homeVoList, boolean reloadData)
    {
        if (shopAdapter == null)
        {
            shopAdapter = new ShopAdapter(this, homeVoList);
            shopAdapter.setOnHomeItemClickListener(this);

            recycleQueryShop.setAdapter(shopAdapter);
            recycleQueryShop.setOnPullLoadMoreListener(this);
        } else
        {
            if (reloadData)
            {
                shopAdapter = new ShopAdapter(this, homeVoList);
                shopAdapter.setOnHomeItemClickListener(this);
                recycleQueryShop.setAdapter(shopAdapter);
                recycleQueryShop.setOnPullLoadMoreListener(this);
            } else
            {
                shopAdapter.addData(homeVoList);
            }
        }
    }

    @Override
    public void stopRefresh()
    {
        recycleQueryShop.setPullLoadMoreCompleted();
    }

    @Override
    public void onRefresh()
    {
        shopQueryPresenter.loadHome(key, true);
    }

    @Override
    public void onLoadMore()
    {
        shopQueryPresenter.loadHome(key, false);
    }

    @OnClick({R.id.ll_toolbar_back, R.id.tv_toolbar_right_text, R.id.ll_toolbar_right_text})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.ll_toolbar_back:

                finish();

                break;
            case R.id.tv_toolbar_right_text:
            case R.id.ll_toolbar_right_text:
                String text = actvKey.getText().toString();

                if (text.equals(key))
                    return;

                if (TextUtils.isEmpty(text))
                {
                    response(this, R.string.please_input_key);
                    return;
                }
                key = text;
                shopQueryPresenter.loadHome(key, false);
                break;
        }
    }
}
