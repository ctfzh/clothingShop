package com.tl.customclothing.features.myaddr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tl.customclothing.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/4/27.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class MyAddrAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int VIEW_TYPE_ADDR = 1;
    Context context;

    List<MyAddrVo> myAddrVos;

    public MyAddrAdapter(Context context, List<MyAddrVo> myAddrVos)
    {
        this.context = context;
        this.myAddrVos = myAddrVos;
    }

    public List<MyAddrVo> getMyAddrVos()
    {
        return myAddrVos;
    }

    public interface OnItemClickListener
    {
        void onSetMainClick(int position, List<MyAddrVo> myAddrVos);

        void onEditClick(int position, List<MyAddrVo> myAddrVos);
    }

    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener()
    {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = null;

        if (viewType == VIEW_TYPE_ADDR)
        {
            view = LayoutInflater.from(context).inflate(R.layout.item_my_addr, parent, false);
            return new MyAddrsViewHolder(view);
        } else
            return null;
    }

    class MyAddrsViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_addr_name)
        TextView tvAddrName;
        @BindView(R.id.cb_addr_set_main)
        CheckBox cbAddrSetMain;
        @BindView(R.id.tv_addr_edit)
        TextView tvAddrEdit;

        public MyAddrsViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnCheckedChanged(R.id.cb_addr_set_main)
        void onChecked(boolean checked)
        {
            if (checked && onItemClickListener != null)
            {
                onItemClickListener.onSetMainClick(getLayoutPosition(), myAddrVos);
            }
        }

        @OnClick(R.id.tv_addr_edit)
        public void onViewClicked()
        {
            if (onItemClickListener != null)
            {
                onItemClickListener.onEditClick(getLayoutPosition(), myAddrVos);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        MyAddrVo myAddr = myAddrVos.get(position);

        int viewType = myAddr.getViewType();

        if (viewType == VIEW_TYPE_ADDR)
        {
            MyAddrsViewHolder viewHolder = (MyAddrsViewHolder) holder;
            viewHolder.tvAddrName.setText(myAddr.getAddrName());

            if (position == 0)
            {
                viewHolder.cbAddrSetMain.setChecked(true);
                viewHolder.cbAddrSetMain.setEnabled(false);
            }
        }

    }

    @Override
    public int getItemCount()
    {
        return myAddrVos.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return myAddrVos.get(position).getViewType();
    }
}
