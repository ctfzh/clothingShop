package com.tl.customclothing.features.ordersearch.pager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BaseFragment;
import com.tl.customclothing.http.request.PostCommentRequest;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.ui.RecyclerDivider;
import com.tl.customclothing.utils.ui.TlDialogFragment;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tianlin on 2017/4/15.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class OrderPageFragment extends BaseFragment implements
        IOrderPageView,
        OrderPageAdapter.OnCommentClickListener
{
    @BindView(R.id.rv_order_search_page)
    PullLoadMoreRecyclerView rvOrderSearchPage;
    Unbinder unbinder;

    OrderPagePresenter pagePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_order_page, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();

        pagePresenter = new OrderPagePresenter();
        pagePresenter.attachView(this);

        return view;
    }

    @Override
    public void initView()
    {
        rvOrderSearchPage.setLinearLayout();
        rvOrderSearchPage.setPullRefreshEnable(false);
        rvOrderSearchPage.setPushRefreshEnable(false);

        RecyclerDivider divider = new RecyclerDivider(getActivity(), RecyclerDivider.VERTICAL_LIST);
        rvOrderSearchPage.getRecyclerView().addItemDecoration(divider);
    }

    @Override
    public void setAdapter(List<OrderPageVo> orderPageVos)
    {
        OrderPageAdapter adapter = new OrderPageAdapter(getActivity(), orderPageVos);
        adapter.setOnCommentClickListener(this);
        rvOrderSearchPage.setAdapter(adapter);
    }

    private EditText et_comment_content;

    @Override
    public void onCommentClick(final int shopId, final String userIdBuy, final int orderId)
    {
        View comment = View.inflate(getActivity(), R.layout.comment_content, null);

        et_comment_content = (EditText) comment.findViewById(R.id.et_comment_content);

        TlDialogFragment
                .newInstance()
                .setNoText(getString(R.string.no))
                .setOkText(getString(R.string.submit_comment))
                .setTitle(getString(R.string.comment_content))
                .setView(comment)
                .setOkListener(new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (et_comment_content != null)
                        {
                            String content = et_comment_content.getText().toString();

                            PostCommentRequest request = new PostCommentRequest();
                            request.setShopId(shopId);
                            request.setUserId(userIdBuy);
                            request.setContent(content);
                            request.setOrderId(orderId);

                            SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_TIME);
                            request.setCommentTime(sdf.format(new Date()));
                            request.setLastUpdateTime(sdf.format(new Date()));

                            pagePresenter.postComment(request);

                        }
                    }
                })
                .show(getActivity().getSupportFragmentManager(), Constant.ALERT_DIALOG);
        ;
    }

    @Override
    public void stopRefresh()
    {
        rvOrderSearchPage.setPullLoadMoreCompleted();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        pagePresenter.detachView();
    }
}
