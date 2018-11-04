package com.tl.customclothing.features.tryon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tl.customclothing.R;
import com.tl.customclothing.model.database.CartShopRealm;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.bitmap.DisplayImgOptionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/5/20.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class TryOnCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    Context context;
    List<CartShopRealm> shopRealms;

    OnItemShopClickListener onItemShopClickListener;

    public OnItemShopClickListener getOnItemShopClickListener()
    {
        return onItemShopClickListener;
    }

    public void setOnItemShopClickListener(OnItemShopClickListener onItemShopClickListener)
    {
        this.onItemShopClickListener = onItemShopClickListener;
    }

    public interface OnItemShopClickListener
    {
        void onItemClick(CartShopRealm cartShopRealm);
    }

    public TryOnCartAdapter(Context context, List<CartShopRealm> shopRealms)
    {
        this.context = context;
        this.shopRealms = shopRealms;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_only_img, parent, false);
        return new MyCartImgHolder(view);

    }

    class MyCartImgHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.iv_item_cart_img)
        ImageView ivItemCartImg;
        @BindView(R.id.ll_item_cart_img)
        LinearLayout llItemCartImg;

        public MyCartImgHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ll_item_cart_img)
        public void onViewClicked()
        {

            if (onItemShopClickListener != null)
            {
                onItemShopClickListener.onItemClick(shopRealms.get(getLayoutPosition()));
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        CartShopRealm cartShop = shopRealms.get(position);
        MyCartImgHolder viewHolder = (MyCartImgHolder) holder;
        ImageLoader.getInstance().displayImage(Constant.BASE_URL + cartShop.getShopMainImg(), viewHolder.ivItemCartImg, DisplayImgOptionUtils.getShopDio());

    }

    @Override
    public int getItemCount()
    {
        return shopRealms.size();
    }
}
