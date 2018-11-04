package com.tl.customclothing.features.main.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BaseFragment;
import com.tl.customclothing.features.login.LoginActivity;
import com.tl.customclothing.features.login.LoginPresenter;
import com.tl.customclothing.http.request.LoginRequest;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.data.DataProvider;
import com.tl.customclothing.utils.emotion.TextEmotion;
import com.tl.customclothing.utils.eventbus.BusEvents;
import com.tl.customclothing.utils.eventbus.EventBusUtils;
import com.tl.customclothing.utils.ui.RecyclerDivider;
import com.tl.customclothing.utils.ui.TlDialogFragment;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

public class MyFragment extends BaseFragment implements
        IMyView,
        MyAdapter.OnMyItemClickListener,
        PullLoadMoreRecyclerView.PullLoadMoreListener
{

    @BindView(R.id.recycle_my)
    PullLoadMoreRecyclerView recycleMy;
    Unbinder unbinder;

    MyPresenter myPresenter;

    MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Log.d("my", "MyFragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);

        EventBusUtils.register(this);

        myPresenter = new MyPresenter();
        myPresenter.attachView(this);

        initView();
        myPresenter.initData();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUser(BusEvents.LoginEvent event)
    {
        if (Constant.USER_UPDATE.equals(event.what))
        {
            myPresenter.loadMy();
        }
    }

    @Override
    public void onDestroy()
    {
        myPresenter.detachView();
        EventBusUtils.unRegister(this);
        super.onDestroy();
    }

    @Override
    public void initView()
    {
        recycleMy.setLinearLayout();

        RecyclerDivider divider = new RecyclerDivider(getActivity(), RecyclerDivider.VERTICAL_LIST);
        divider.setPaddingLeft((int) getActivity().getResources().getDimension(R.dimen.horizontal_margin));
        divider.setPaddingRight((int) getActivity().getResources().getDimension(R.dimen.horizontal_margin));

        recycleMy.getRecyclerView().addItemDecoration(divider);
    }

    @Override
    public void setMyAdapter(List<MyVo> myVoList)
    {
        myAdapter = new MyAdapter(getActivity(), myVoList);
        myAdapter.setOnItemClickListener(this);

        recycleMy.setAdapter(myAdapter);
        recycleMy.setPushRefreshEnable(false);

        recycleMy.setOnPullLoadMoreListener(this);
    }

    @Override
    public void onMyItemClick(Class clazz)
    {
        if (clazz != null)
        {
            Intent intent = new Intent(getActivity(), clazz);
            startActivity(intent);
        }
    }

    @Override
    public void onExitClick()
    {
        TlDialogFragment
                .newInstance()
                .setTitle(getString(R.string.tips))
                .setMsg(getString(R.string.are_you_sure_to_exit) + TextEmotion.qidai)
                .setOkText(getString(R.string.ok))
                .setNoText(getString(R.string.no))
                .setOkListener(new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // 注销用户数据
                        DataProvider.initUserData(getActivity());
                        // 先刷新界面
                        EventBus.getDefault().post(new BusEvents.LoginEvent(Constant.USER_UPDATE));

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .show(getActivity().getSupportFragmentManager(), Constant.ALERT_DIALOG);

    }

    @Override
    public void onRefresh()
    {
        // 主要用于刷新用户信息
        String userId = DataProvider.getUserLoginId(getActivity());
        if (!Constant.NULL.equals(userId))
        {
            LoginRequest request = new LoginRequest();
            request.setUsername(userId);
            LoginPresenter.refreshUserData(getActivity(), request, true);
        }

        recycleMy.setPullLoadMoreCompleted();
    }

    @Override
    public void onLoadMore()
    {
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
