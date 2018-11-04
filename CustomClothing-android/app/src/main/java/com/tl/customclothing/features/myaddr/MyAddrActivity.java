package com.tl.customclothing.features.myaddr;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BaseActivity;
import com.tl.customclothing.http.request.UpdateUserAddrRequest;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.data.DataProvider;
import com.tl.customclothing.utils.ui.TlDialogFragment;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/4/27.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class MyAddrActivity extends BaseActivity implements IMyAddrView
        , MyAddrAdapter.OnItemClickListener,
        PullLoadMoreRecyclerView.PullLoadMoreListener
{
    private static final int MODE_VIEW = 1;
    private static final int MODE_EDIT = 2;

    MyAddrPresenter myAddrPresenter;
    @BindView(R.id.ll_toolbar_back)
    LinearLayout llToolbarBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.rv_my_addr)
    PullLoadMoreRecyclerView rvMyAddr;
    @BindView(R.id.tv_toolbar_right_text)
    TextView tvToolbarRightText;
    @BindView(R.id.ll_toolbar_right_text)
    LinearLayout llToolbarRightText;

    private EditText et_addr_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addr);
        ButterKnife.bind(this);

        initView();

        myAddrPresenter = new MyAddrPresenter();
        myAddrPresenter.attachView(this);

        myAddrPresenter.initData();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        myAddrPresenter.detachView();
    }

    @Override
    public void initView()
    {
        tvToolbarRightText.setVisibility(View.VISIBLE);
        tvToolbarRightText.setText(R.string.add_addr);
        tvToolbarTitle.setText(R.string.my_addr);

        rvMyAddr.setLinearLayout();
        rvMyAddr.setPushRefreshEnable(false);
        rvMyAddr.setOnPullLoadMoreListener(this);
    }

    @Override
    public void setAdapter(List<MyAddrVo> list)
    {
        MyAddrAdapter adapter = new MyAddrAdapter(this, list);
        adapter.setOnItemClickListener(this);

        rvMyAddr.setAdapter(adapter);

        if (list.size() == 3)
        {
            tvToolbarRightText.setVisibility(View.GONE);
        } else
        {
            tvToolbarRightText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSetMainClick(int position, List<MyAddrVo> myAddrVos)
    {
        UpdateUserAddrRequest request = new UpdateUserAddrRequest();
        request.setUserId(DataProvider.getUserLoginId(MyAddrActivity.this));

        if (position == 0)
        {
            return;
        } else if (position == 1)
        {
            String preAddr = myAddrVos.get(1).getAddrName();
            String preMain = myAddrVos.get(0).getAddrName();

            request.setUserAddrMain(preAddr);
            request.setUserAddr1(preMain);
        } else if (position == 2)
        {
            String preAddr = myAddrVos.get(2).getAddrName();
            String preMain = myAddrVos.get(0).getAddrName();

            request.setUserAddrMain(preAddr);
            request.setUserAddr2(preMain);
        }

        myAddrPresenter.updateAddr(request);
    }

    @Override
    public void onEditClick(final int position, final List<MyAddrVo> myAddrVos)
    {
        View comment = View.inflate(this, R.layout.comment_content, null);
        et_addr_content = (EditText) comment.findViewById(R.id.et_comment_content);

        final String pre_addr = myAddrVos.get(position).getAddrName();
        et_addr_content.setText(pre_addr);

        et_addr_content.setSelection(et_addr_content.getText().length());

        TlDialogFragment
                .newInstance()
                .setNoText(getString(R.string.no))
                .setOkText(getString(R.string.submit_addr))
                .setTitle(getString(R.string.comment_addr))
                .setView(comment)
                .setOkListener(new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        if (et_addr_content == null)
                            return;

                        String addr = et_addr_content.getText().toString();

                        if (TextUtils.isEmpty(addr))
                        {
                            response(MyAddrActivity.this, R.string.addr_cannot_be_empty);
                            return;
                        }

                        if (addr.equals(pre_addr))
                        {
                            response(MyAddrActivity.this, R.string.no_change);
                            return;
                        }

                        UpdateUserAddrRequest request = new UpdateUserAddrRequest();
                        request.setUserId(DataProvider.getUserLoginId(MyAddrActivity.this));

                        if (position == 0)
                        {
                            request.setUserAddrMain(addr);
                        } else if (position == 1)
                        {
                            request.setUserAddrMain(myAddrVos.get(0).getAddrName());
                            request.setUserAddr1(addr);
                        } else if (position == 2)
                        {
                            request.setUserAddrMain(myAddrVos.get(0).getAddrName());
                            request.setUserAddr2(addr);
                        }
                        myAddrPresenter.updateAddr(request);

                    }
                })
                .show(this.getSupportFragmentManager(), Constant.ALERT_DIALOG);
    }

    @Override
    public void onRefresh()
    {
        myAddrPresenter.getData();
        rvMyAddr.setPullLoadMoreCompleted();
    }

    @Override
    public void onLoadMore()
    {
    }

    @OnClick(R.id.ll_toolbar_back)
    public void ll_toolbar_back()
    {
        finish();
    }

    @OnClick(R.id.ll_toolbar_right_text)
    public void addAddr()
    {
        View comment = View.inflate(this, R.layout.comment_content, null);
        et_addr_content = (EditText) comment.findViewById(R.id.et_comment_content);

        TlDialogFragment
                .newInstance()
                .setNoText(getString(R.string.no))
                .setOkText(getString(R.string.submit_addr))
                .setTitle(getString(R.string.comment_addr))
                .setView(comment)
                .setOkListener(new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        if (et_addr_content == null)
                            return;

                        MyAddrAdapter myAddrAdapter = (MyAddrAdapter) rvMyAddr.getRecyclerView().getAdapter();

                        if (myAddrAdapter == null)
                            return;

                        List<MyAddrVo> list = myAddrAdapter.getMyAddrVos();

                        if (list == null)
                            return;

                        UpdateUserAddrRequest request = new UpdateUserAddrRequest();
                        request.setUserId(DataProvider.getUserLoginId(MyAddrActivity.this));

                        String addr = et_addr_content.getText().toString();

                        if (list.size() == 1)
                        {
                            request.setUserAddrMain(list.get(0).getAddrName());
                            request.setUserAddr1(addr);
                        } else if (list.size() == 2)
                        {
                            request.setUserAddrMain(list.get(0).getAddrName());
                            request.setUserAddr2(addr);
                        }
                        myAddrPresenter.updateAddr(request);
                    }
                })
                .show(this.getSupportFragmentManager(), Constant.ALERT_DIALOG);
    }
}
