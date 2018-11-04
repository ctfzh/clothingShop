package com.tl.customclothing.features.main.cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BaseFragment;
import com.tl.customclothing.features.main.MainActivity;
import com.tl.customclothing.features.shopdetail.ShopDetailActivity;
import com.tl.customclothing.model.dao.CartShopDao;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.NullUtils;
import com.tl.customclothing.utils.emotion.TextEmotion;
import com.tl.customclothing.utils.eventbus.BusEvents;
import com.tl.customclothing.utils.eventbus.EventBusUtils;
import com.tl.customclothing.utils.ui.TlDialogFragment;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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

public class CartFragment extends BaseFragment implements
        ICartView,
        PullLoadMoreRecyclerView.PullLoadMoreListener,
        CartAdapter.OnItemClickListener,
        MainActivity.OnOptionMenuSelectListener
{

    @BindView(R.id.rv_cart)
    PullLoadMoreRecyclerView rvCart;
    Unbinder unbinder;
    CartPresenter cartPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();

        cartPresenter = new CartPresenter();
        cartPresenter.attachView(this);
        cartPresenter.initData();

        EventBusUtils.register(this);


        return view;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        cartPresenter.detachView();
        EventBusUtils.unRegister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addToCart(BusEvents.CartRefreshEvent event)
    {
        if (Constant.ADD_TO_CART_EVENT.equals(event.what))
        {
            cartPresenter.initData();
        }
    }

    @Override
    public void setAdapter(List<CartVo> cartVoList)
    {
        CartAdapter cartAdapter = new CartAdapter(getActivity(), cartVoList);
        cartAdapter.setOnItemClickListener(this);
        rvCart.getRecyclerView().setAdapter(cartAdapter);
    }

    @Override
    public void onDeleteClick()
    {
        CartAdapter adapter = (CartAdapter) rvCart.getRecyclerView().getAdapter();

        if (adapter == null)
        {
            response(getActivity(), R.string.no_data);
            return;
        }

        List<CartVo> cartVoList = adapter.getCartVoList();
        if (NullUtils.isEmpty(cartVoList))
        {
            response(getActivity(), R.string.no_data);
            return;
        }

        // 暂时的
        final List<CartVo> temp = new ArrayList<>();
        temp.addAll(cartVoList);

        boolean isNoSelect = true;

        for (int i = 0; i < temp.size(); i++)
        {
            CartVo cartVo = temp.get(i);

            // no data的那个数据
            if (cartVo.getShopId() == 0)
            {
                response(getActivity(), R.string.no_data);
                return;
            }

            if (cartVo.isSelect())
            {
                isNoSelect = false;
            }
        }

        if (isNoSelect)
        {
            response(getActivity(), R.string.no_select);
            return;
        }

        TlDialogFragment
                .newInstance()
                .setTitle(getString(R.string.tips))
                .setMsg(getString(R.string.are_you_sure_to_delete) + TextEmotion.qidai)
                .setOkText(getString(R.string.ok))
                .setNoText(getString(R.string.no))
                .setOkListener(new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        for (int i = 0; i < temp.size(); i++)
                        {
                            CartVo cartVo = temp.get(i);

                            if (cartVo.isSelect())
                            {
                                CartShopDao.delete(cartVo.getShopId());
                            }
                        }
                        cartPresenter.initData();
                    }
                })
                .show(getActivity().getSupportFragmentManager(), Constant.ALERT_DIALOG);

    }

    @Override
    public void onItemClick(int shopId)
    {
        Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
        intent.putExtra(ShopDetailActivity.INTENT_KEY_SHOP_ID, shopId);
        startActivity(intent);
    }

    @Override
    public void setLinear()
    {
        rvCart.setLinearLayout();
    }

    @Override
    public void onRefresh()
    {
        cartPresenter.initData();
        ;
        rvCart.setPullLoadMoreCompleted();
    }

    @Override
    public void onLoadMore()
    {

    }


    @Override
    public void setFlow()
    {
        rvCart.setStaggeredGridLayout(Constant.GRID_COL_COUNT);
    }

    @Override
    public void initView()
    {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setOnOptionMenuSelectListener(this);

        rvCart.setPushRefreshEnable(false);
        rvCart.setOnPullLoadMoreListener(this);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
