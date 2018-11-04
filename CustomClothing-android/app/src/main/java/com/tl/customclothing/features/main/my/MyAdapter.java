package com.tl.customclothing.features.main.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tl.customclothing.R;
import com.tl.customclothing.utils.adapter.MyOnlyTextHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2017/4/8.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private Context context;
    private List<MyVo> list;
    private OnMyItemClickListener onMyItemClickListener;

    public static final int VIEW_TYPE_MY_LL_HEAD = 1;
    public static final int VIEW_TYPE_NO_LOGIN = 2;
    public static final int VIEW_TYPE_MY_EXIT = 3;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = null;
        switch (viewType)
        {
            case VIEW_TYPE_MY_LL_HEAD:
            case VIEW_TYPE_MY_EXIT:
                view = LayoutInflater.from(context).inflate(R.layout.item_my_ll_head, parent, false);
                return new MyHeadViewHolder(view);

            case VIEW_TYPE_NO_LOGIN:
                view = LayoutInflater.from(context).inflate(R.layout.item_only_one_tv, parent, false);
                return new MyOnlyTextHolder(view);
            default:
                return null;
        }
    }

    public MyAdapter(Context context, List<MyVo> list)
    {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnMyItemClickListener onMyItemClickListener)
    {
        this.onMyItemClickListener = onMyItemClickListener;
    }

    public interface OnMyItemClickListener
    {
        /**
         * 点击的item项
         *
         * @param clazz 将要打开的Activity的类实例
         */
        void onMyItemClick(Class clazz);

        /**
         * 点击了注销登录
         */
        void onExitClick();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        int viewType = getItemViewType(position);
        final MyVo myVo = list.get(position);

        if (viewType == VIEW_TYPE_MY_LL_HEAD)
        {
            MyHeadViewHolder viewHolder = (MyHeadViewHolder) holder;
            viewHolder.tvItemLeftTitle.setText(myVo.leftTitle);
            viewHolder.tvItemRightTitle.setText(myVo.rightTitle);

            viewHolder.llMyOrder.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (onMyItemClickListener != null)
                        onMyItemClickListener.onMyItemClick(myVo.clazz);
                }
            });

        } else if (viewType == VIEW_TYPE_MY_EXIT)
        {
            MyHeadViewHolder viewHolder = (MyHeadViewHolder) holder;
            viewHolder.tvItemLeftTitle.setText(myVo.leftTitle);
            viewHolder.tvItemRightTitle.setVisibility(View.GONE);
            viewHolder.ivItemRightIcon.setVisibility(View.GONE);

            viewHolder.llMyOrder.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (onMyItemClickListener != null)
                        onMyItemClickListener.onExitClick();
                }
            });
        } else if (viewType == VIEW_TYPE_NO_LOGIN)
        {
            MyOnlyTextHolder viewHolder = (MyOnlyTextHolder) holder;
            viewHolder.tvDesc.setText(myVo.noLoginDesc);
        }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return list.get(position).viewType;
    }

    class MyHeadViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_item_left_title)
        TextView tvItemLeftTitle;
        @BindView(R.id.tv_item_right_title)
        TextView tvItemRightTitle;
        @BindView(R.id.ll_my_order)
        LinearLayout llMyOrder;
        @BindView(R.id.iv_item_right_icon)
        ImageView ivItemRightIcon;

        public MyHeadViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
