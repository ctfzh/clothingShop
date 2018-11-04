package com.tl.customclothing.features.shopdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.tl.customclothing.R;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.bitmap.DisplayImgOptionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianlin on 2017/4/18.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class ShopDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;

    private List<ShopDetailVo> shopDetailList;

    public static final int VIEW_TYPE_VIEW_PAGER = 1;
    public static final int VIEW_TYPE_BASE_INFO = 2;
    public static final int VIEW_TYPE_COMMENT = 3;

    public ShopDetailAdapter(Context context, List<ShopDetailVo> shopDetailList)
    {
        this.context = context;
        this.shopDetailList = shopDetailList;
    }

    public List<ShopDetailVo> getShopDetailList()
    {
        return shopDetailList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = null;
        if (viewType == VIEW_TYPE_VIEW_PAGER)
        {
            view = LayoutInflater.from(context).inflate(R.layout.item_shop_detail_vp, parent, false);
            return new MyViewPagerHolder(view);
        } else if (viewType == VIEW_TYPE_BASE_INFO)
        {
            view = LayoutInflater.from(context).inflate(R.layout.item_shop_detail_bi, parent, false);
            return new MyBaseInfoHolder(view);
        } else if (viewType == VIEW_TYPE_COMMENT)
        {
            view = LayoutInflater.from(context).inflate(R.layout.item_shop_detail_comment, parent, false);
            return new MyCommentHolder(view);
        } else
            return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        ShopDetailVo shopDetailVo = shopDetailList.get(position);

        int viewType = shopDetailVo.getViewType();

        if (viewType == VIEW_TYPE_VIEW_PAGER)
        {
            MyViewPagerHolder viewPagerHolder = (MyViewPagerHolder) holder;

            final List<String> imgs = shopDetailVo.getShopImgs();

            if (imgs != null)
            {
                viewPagerHolder.cvShopDetail.setPageCount(imgs.size());
                viewPagerHolder.cvShopDetail.setImageListener(new ImageListener()
                {
                    @Override
                    public void setImageForPosition(int position, ImageView imageView)
                    {
                        ImageLoader.getInstance().displayImage(Constant.BASE_URL + imgs.get(position), imageView, DisplayImgOptionUtils.getAdsDio());
                    }
                });
            }

        } else if (viewType == VIEW_TYPE_BASE_INFO)
        {
            MyBaseInfoHolder baseInfoHolder = (MyBaseInfoHolder) holder;

            baseInfoHolder.tvShopDetailTag.setText(shopDetailVo.getShopTag());
            baseInfoHolder.tvShopDetailAddr.setText(shopDetailVo.getShopSalesAddr());
            baseInfoHolder.tvShopDetailPrice.setText(context.getString(R.string.shop_price) + shopDetailVo.getShopPrice());
            baseInfoHolder.tvShopDetailSaleCount.setText(context.getString(R.string.sale_count) + shopDetailVo.getShopSalesCount());

            if (!TextUtils.isEmpty(shopDetailVo.getShopGender()) && Constant.MALE.equals(shopDetailVo.getShopGender()))
                baseInfoHolder.tvShopDetailType.setText(context.getString(R.string.male) + shopDetailVo.getShopType());
            else
            {
                baseInfoHolder.tvShopDetailType.setText(context.getString(R.string.female) + shopDetailVo.getShopType());
            }
        } else if (viewType == VIEW_TYPE_COMMENT)
        {
            MyCommentHolder commentHolder = (MyCommentHolder) holder;
            commentHolder.tvCommentItemName.setText(shopDetailVo.getCommentUserNickName());
            commentHolder.tvCommentItemContent.setText(shopDetailVo.getContent());
            commentHolder.tvCommentItemTime.setText(shopDetailVo.getCommentTime());
        }

    }

    class MyViewPagerHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.cv_shop_detail)
        CarouselView cvShopDetail;

        public MyViewPagerHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MyBaseInfoHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_shop_detail_tag)
        TextView tvShopDetailTag;
        @BindView(R.id.tv_shop_detail_price)
        TextView tvShopDetailPrice;
        @BindView(R.id.tv_shop_detail_sale_count)
        TextView tvShopDetailSaleCount;
        @BindView(R.id.tv_shop_detail_type)
        TextView tvShopDetailType;
        @BindView(R.id.tv_shop_detail_addr)
        TextView tvShopDetailAddr;

        public MyBaseInfoHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MyCommentHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_comment_item_name)
        TextView tvCommentItemName;
        @BindView(R.id.tv_comment_item_content)
        TextView tvCommentItemContent;
        @BindView(R.id.tv_comment_item_time)
        TextView tvCommentItemTime;

        public MyCommentHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount()
    {
        return shopDetailList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return shopDetailList.get(position).getViewType();
    }
}
