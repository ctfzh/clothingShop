package com.tl.customclothing.features.ordersearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BaseActivity;
import com.tl.customclothing.features.ordersearch.pager.OrderPageFragment;
import com.tl.customclothing.features.ordersearch.pager.OrderPageVo;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.eventbus.BusEvents;
import com.tl.customclothing.utils.eventbus.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/4/8.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class OrderSearchActivity extends BaseActivity implements
        IOrderSearchView,
        ViewPager.OnPageChangeListener

{
    @BindView(R.id.tab_order_search)
    TabLayout tabOrderSearch;
    @BindView(R.id.vp_order_search)
    ViewPager vpOrderSearch;
    @BindView(R.id.ll_toolbar_back)
    LinearLayout llToolbarBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.ll_toolbar)
    RelativeLayout llToolbar;

    OrderSearchPresenter orderSearchPresenter;

    List<OrderPageFragment> fragments;

    private int currentSelect = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_search);
        ButterKnife.bind(this);

        orderSearchPresenter = new OrderSearchPresenter();
        orderSearchPresenter.attachView(this);
        orderSearchPresenter.initData();

        EventBusUtils.register(this);

        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void orderSearchRefresh(BusEvents.OrderSearchRefreshEvent event)
    {
        if (Constant.ORDER_SEARCH_REFRESH_EVENT.equals(event.what))
        {
            orderSearchPresenter.getData(currentSelect, false);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        orderSearchPresenter.detachView();
        EventBusUtils.unRegister(this);
    }

    @Override
    public void initView()
    {
        tvToolbarTitle.setText(getString(R.string.my_order));

        List<String> titles = new ArrayList<>();
        titles.add(Constant.ORDER_STATUS_TO_DEAL_TITLE);
        titles.add(Constant.ORDER_STATUS_DEALING_TITLE);
        titles.add(Constant.ORDER_STATUS_RECEIVING_TITLE);
        titles.add(Constant.ORDER_STATUS_COMMENTING_TITLE);
        titles.add(Constant.ORDER_STATUS_FINISHING_TITLE);

        fragments = new ArrayList<>();
        OrderPageFragment fragment1 = new OrderPageFragment();
        OrderPageFragment fragment2 = new OrderPageFragment();
        OrderPageFragment fragment3 = new OrderPageFragment();
        OrderPageFragment fragment4 = new OrderPageFragment();
        OrderPageFragment fragment5 = new OrderPageFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);

        MyFragmentViewPagerAdapter adapter = new MyFragmentViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vpOrderSearch.setAdapter(adapter);
        tabOrderSearch.setupWithViewPager(vpOrderSearch);

        vpOrderSearch.addOnPageChangeListener(this);

        vpOrderSearch.setCurrentItem(0);
        orderSearchPresenter.getData(0, true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
    }

    @Override
    public void onPageSelected(int position)
    {
        currentSelect = position;
        orderSearchPresenter.getData(position, true);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
    }

    @Override
    public void setData(int position, List<OrderPageVo> orderPageVos)
    {
        fragments.get(position).setAdapter(orderPageVos);
    }

    @OnClick(R.id.ll_toolbar_back)
    public void onViewClicked()
    {
        finish();
    }
}
