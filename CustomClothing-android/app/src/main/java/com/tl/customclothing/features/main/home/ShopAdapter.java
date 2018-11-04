package com.tl.customclothing.features.main.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tl.customclothing.R;
import com.tl.customclothing.http.response.QueryShopResponseItem;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.adapter.MyOnlyTextHolder;
import com.tl.customclothing.utils.bitmap.DisplayImgOptionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/4/9.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int VIEW_TYPE_ITEM_SHOP = 2;
    public static final int VIEW_TYPE_NO_DATA = 3;

    private Context context;
    private List<HomeVo> homeVoList;

    private OnHomeItemClickListener onHomeItemClickListener;

    public interface OnHomeItemClickListener
    {
        void onItemClick(int shopId);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = null;

        if (viewType == VIEW_TYPE_ITEM_SHOP)
        {
            view = LayoutInflater.from(context).inflate(R.layout.item_home_shop, parent, false);
            return new MyHomeItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_NO_DATA)
        {
            view = LayoutInflater.from(context).inflate(R.layout.item_only_one_tv, parent, false);
            return new MyOnlyTextHolder(view);
        }
        return null;
    }

    public void addData(List<HomeVo> newData)
    {
        homeVoList.addAll(newData);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        final HomeVo homeVo = homeVoList.get(position);
        int viewType = homeVo.viewType;

        if (viewType == VIEW_TYPE_ITEM_SHOP)
        {
            MyHomeItemViewHolder viewHolder = (MyHomeItemViewHolder) holder;

            QueryShopResponseItem queryShopResponseItem = homeVo.queryShopResponseItem;

            if (queryShopResponseItem != null)
            {
                ImageLoader.getInstance().displayImage(
                        Constant.BASE_URL + queryShopResponseItem.getShopMainImg(),
                        viewHolder.ivHomeItemShopImg,
                        DisplayImgOptionUtils.getShopDio()
                );

                viewHolder.tvHomeItemShopTag.setText(queryShopResponseItem.getShopTag());
                viewHolder.tvHomeItemShopAddr.setText(queryShopResponseItem.getShopSalesAddr());
                viewHolder.tvHomeItemShopPrice.setText(context.getString(R.string.shop_price) + queryShopResponseItem.getShopPrice());
                viewHolder.tvHomeItemShopSaleCount.setText(context.getString(R.string.sale_count) + queryShopResponseItem.getShopSalesCount());

                if (!TextUtils.isEmpty(queryShopResponseItem.getShopGender()) && Constant.MALE.equals(queryShopResponseItem.getShopGender()))
                    viewHolder.tvHomeItemShopType.setText(context.getString(R.string.male) + queryShopResponseItem.getShopType());
                else
                {
                    viewHolder.tvHomeItemShopType.setText(context.getString(R.string.female) + queryShopResponseItem.getShopType());
                }
            }

        } else
        {
            MyOnlyTextHolder viewHolder = (MyOnlyTextHolder) holder;
            viewHolder.tvDesc.setText(homeVo.noDataDesc);
        }

    }

    public OnHomeItemClickListener getOnHomeItemClickListener()
    {
        return onHomeItemClickListener;
    }

    public void setOnHomeItemClickListener(OnHomeItemClickListener onHomeItemClickListener)
    {
        this.onHomeItemClickListener = onHomeItemClickListener;
    }

    class MyHomeItemViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.iv_home_item_shop_img)
        ImageView ivHomeItemShopImg;
        @BindView(R.id.tv_home_item_shop_tag)
        TextView tvHomeItemShopTag;
        @BindView(R.id.tv_home_item_shop_addr)
        TextView tvHomeItemShopAddr;
        @BindView(R.id.tv_home_item_shop_price)
        TextView tvHomeItemShopPrice;
        @BindView(R.id.tv_home_item_shop_sale_count)
        TextView tvHomeItemShopSaleCount;
        @BindView(R.id.ll_home_item_shop)
        LinearLayout llHomeItemShop;
        @BindView(R.id.tv_home_item_shop_type)
        TextView tvHomeItemShopType;

        public MyHomeItemViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ll_home_item_shop)
        public void onViewClicked()
        {
            if (onHomeItemClickListener != null)
            {
                QueryShopResponseItem queryShopResponseItem = homeVoList.get(getLayoutPosition()).queryShopResponseItem;
                if (queryShopResponseItem != null)
                    onHomeItemClickListener.onItemClick(queryShopResponseItem.getShopId());
            }
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return homeVoList.get(position).viewType;
    }

    public ShopAdapter(Context context, List<HomeVo> homeVoList)
    {
        this.context = context;
        this.homeVoList = homeVoList;
    }

    @Override
    public int getItemCount()
    {
        return homeVoList.size();
    }
}
