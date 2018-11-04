package com.tl.customclothing.features.main.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tl.customclothing.R;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.adapter.MyOnlyTextHolder;
import com.tl.customclothing.utils.bitmap.DisplayImgOptionUtils;
import com.tl.customclothing.utils.display.PixsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;


/**
 * Created by tianlin on 2017/4/24.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int VIEW_TYPE_SHOP_ITEM = 1;
    public static final int VIEW_TYPE_NO_DATA = 2;
    private Context context;
    private List<CartVo> cartVoList;
    public static final int MODEL_CLICK = 1;
    public static final int MODEL_DELETE = 2;

    private int model = MODEL_CLICK;

    public CartAdapter(Context context, List<CartVo> cartVoList)
    {
        this.cartVoList = cartVoList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == VIEW_TYPE_SHOP_ITEM)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.item_cart_shop_item, parent, false);
            return new MyShopItemHolder(view);
        } else if (viewType == VIEW_TYPE_NO_DATA)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.item_only_one_tv, parent, false);
            return new MyOnlyTextHolder(view);
        } else
            return null;
    }

    public List<CartVo> getCartVoList()
    {
        return cartVoList;
    }

    public void setCartVoList(List<CartVo> cartVoList)
    {
        this.cartVoList = cartVoList;
    }

    public int getModel()
    {
        return model;
    }

    public void setModel(int model)
    {
        this.model = model;
    }

    public interface OnItemClickListener
    {
        void onItemClick(int shopId);
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

    class MyShopItemHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.iv_cart_item_shop_main_img)
        ImageView ivCartItemShopMainImg;
        @BindView(R.id.iv_cart_item_shop_price)
        TextView ivCartItemShopPrice;
        @BindView(R.id.iv_cart_item_shop_sales_count)
        TextView ivCartItemShopSalesCount;
        @BindView(R.id.iv_cart_item_shop_type)
        TextView ivCartItemShopType;
        @BindView(R.id.rl_cart_shop_item)
        RelativeLayout rlCartShopItem;
        @BindView(R.id.iv_cart_item_is_selected)
        ImageView ivCartItemIsSelected;

        public MyShopItemHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.rl_cart_shop_item)
        public void onViewClicked()
        {
            if (onItemClickListener != null && MODEL_CLICK == model)
            {
                CartVo cartVo = cartVoList.get(getLayoutPosition());
                onItemClickListener.onItemClick(cartVo.getShopId());
            } else if (MODEL_DELETE == model)
            {
                CartVo cartVo = cartVoList.get(getLayoutPosition());
                cartVo.setSelect(!cartVo.isSelect());
                notifyItemChanged(getLayoutPosition());
            }
        }

        @OnLongClick(R.id.rl_cart_shop_item)
        public boolean onLongClick()
        {
            if (model == MODEL_DELETE)
            {
                model = MODEL_CLICK;
                for (int i = 0; i < cartVoList.size(); i++)
                {
                    cartVoList.get(i).setSelect(false);
                }
            } else if (model == MODEL_CLICK)
            {
                model = MODEL_DELETE;
                cartVoList.get(getLayoutPosition()).setSelect(true);
            }
            notifyItemRangeChanged(0, cartVoList.size());
            return true;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        int viewType = getItemViewType(position);
        CartVo cartVo = cartVoList.get(position);

        if (viewType == VIEW_TYPE_SHOP_ITEM)
        {
            MyShopItemHolder viewHolder = (MyShopItemHolder) holder;

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//                    DensityUtils.getScreenWidth(context) / Constant.GRID_COL_COUNT,   // 取屏幕的一般
                    PixsUtils.dp2px(context, Constant.SHOP_IMG_WIDTH),
                    PixsUtils.dp2px(context, Constant.SHOP_IMG_HEIGHT));
            viewHolder.ivCartItemShopMainImg.setLayoutParams(layoutParams);
            ImageLoader.getInstance().displayImage(Constant.BASE_URL + cartVo.getShopMainImg(), viewHolder.ivCartItemShopMainImg, DisplayImgOptionUtils.getCartShopDio());

            viewHolder.ivCartItemShopPrice.setText(context.getString(R.string.price) + "\n\t" + cartVo.getShopPrice());

            viewHolder.ivCartItemShopSalesCount.setText(context.getString(R.string.sale_count) + "\n\t" + cartVo.getShopSalesCount());

            if (!TextUtils.isEmpty(cartVo.getShopGender()) && Constant.MALE.equals(cartVo.getShopGender()))
                viewHolder.ivCartItemShopType.setText(
                        new StringBuilder()
                                .append(context.getString(R.string.type))
                                .append("\n\t")
                                .append(context.getString(R.string.male))
                                .append(cartVo.getShopType())
                                .toString()
                );
            else
            {
                viewHolder.ivCartItemShopType.setText(new StringBuilder()
                        .append(context.getString(R.string.type))
                        .append("\n\t")
                        .append(context.getString(R.string.female))
                        .append(cartVo.getShopType())
                        .toString());
            }

            if (model == MODEL_DELETE)
            {
                viewHolder.ivCartItemIsSelected.setVisibility(View.VISIBLE);
                if (cartVo.isSelect())
                    viewHolder.ivCartItemIsSelected.setImageResource(R.drawable.ic_selected);
                else
                    viewHolder.ivCartItemIsSelected.setImageResource(R.drawable.ic_un_selected);
            } else
            {
                viewHolder.ivCartItemIsSelected.setVisibility(View.GONE);
            }

        } else if (viewType == VIEW_TYPE_NO_DATA)
        {
            MyOnlyTextHolder viewHolder = (MyOnlyTextHolder) holder;
            viewHolder.tvDesc.setText(cartVo.getNoDataDesc());
        }
    }

    @Override
    public int getItemCount()
    {
        return cartVoList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return cartVoList.get(position).getViewType();
    }
}
