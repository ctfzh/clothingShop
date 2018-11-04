package com.tl.customclothing.utils.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tl.customclothing.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2017/4/8.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class MyOnlyTextHolder extends RecyclerView.ViewHolder
{
    @BindView(R.id.tv_desc)
    public TextView tvDesc;

    public MyOnlyTextHolder(View itemView)
    {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
