package com.tl.customclothing.features.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BaseFragment;
import com.tl.customclothing.features.shopdetail.ShopDetailActivity;
import com.tl.customclothing.utils.ui.RecyclerDivider;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tianlin on 2017/4/10.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class HomeFragment extends BaseFragment implements
        IHomeView,
        ShopAdapter.OnHomeItemClickListener,
        PullLoadMoreRecyclerView.PullLoadMoreListener
{

    @BindView(R.id.recycle_home)
    PullLoadMoreRecyclerView recycleHome;
    Unbinder unbinder;

    private ShopAdapter shopAdapter;

    HomePresenter homePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        homePresenter = new HomePresenter();
        homePresenter.attachView(this);

        initView();
        homePresenter.initData();

        return view;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        homePresenter.detachView();
    }

    @Override
    public void setHomeAdapter(List<HomeVo> homeVoList, boolean reloadData)
    {
        if (shopAdapter == null)
        {
            shopAdapter = new ShopAdapter(getActivity(), homeVoList);
            shopAdapter.setOnHomeItemClickListener(this);

            recycleHome.setAdapter(shopAdapter);
            recycleHome.setOnPullLoadMoreListener(this);
        } else
        {

            if (reloadData)
            {
                shopAdapter = new ShopAdapter(getActivity(), homeVoList);
                shopAdapter.setOnHomeItemClickListener(this);
                recycleHome.setAdapter(shopAdapter);
                recycleHome.setOnPullLoadMoreListener(this);
            } else
            {
                shopAdapter.addData(homeVoList);
            }
        }

    }

    @Override
    public void onRefresh()
    {
        homePresenter.loadHome("", true);
    }

    @Override
    public void onLoadMore()
    {
        homePresenter.loadHome("", false);
    }

    @Override
    public void stopRefresh()
    {
        recycleHome.setPullLoadMoreCompleted();
    }

    @Override
    public void onItemClick(int shopId)
    {
        Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
        intent.putExtra(ShopDetailActivity.INTENT_KEY_SHOP_ID, shopId);
        startActivity(intent);
    }

    @Override
    public void initView()
    {
        recycleHome.setLinearLayout();
        recycleHome.setPullRefreshEnable(true);
        recycleHome.setOnPullLoadMoreListener(this);
        RecyclerDivider divider = new RecyclerDivider(getActivity(), RecyclerDivider.VERTICAL_LIST);
        divider.setPaddingLeft((int) getActivity().getResources().getDimension(R.dimen.horizontal_margin));
        divider.setPaddingRight((int) getActivity().getResources().getDimension(R.dimen.horizontal_margin));
        recycleHome.getRecyclerView().addItemDecoration(divider);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
