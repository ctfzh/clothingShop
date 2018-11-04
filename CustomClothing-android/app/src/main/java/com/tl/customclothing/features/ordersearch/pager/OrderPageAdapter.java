package com.tl.customclothing.features.ordersearch.pager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tl.customclothing.R;
import com.tl.customclothing.http.response.OrderSearchResponseItem;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.adapter.MyOnlyTextHolder;
import com.tl.customclothing.utils.bitmap.DisplayImgOptionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/4/25.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class OrderPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int VIEW_TYPE_ORDER_ITEM = 1;
    public static final int VIEW_TYPE_NO_DATA = 2;

    private Context context;
    private List<OrderPageVo> searchVoList;

    public OrderPageAdapter(Context context, List<OrderPageVo> searchVoList)
    {
        this.context = context;
        this.searchVoList = searchVoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = null;

        if (viewType == VIEW_TYPE_ORDER_ITEM)
        {
            view = LayoutInflater.from(context).inflate(R.layout.item_order_page, parent, false);
            return new MyOrderViewHolder(view);
        } else if (viewType == VIEW_TYPE_NO_DATA)
        {
            view = LayoutInflater.from(context).inflate(R.layout.item_only_one_tv, parent, false);
            return new MyOnlyTextHolder(view);
        } else
            return null;
    }

    public interface OnCommentClickListener
    {
        void onCommentClick(int shopId, String userIdBuy, int orderId);
    }

    private OnCommentClickListener onCommentClickListener;

    public OnCommentClickListener getOnCommentClickListener()
    {
        return onCommentClickListener;
    }

    public void setOnCommentClickListener(OnCommentClickListener onCommentClickListener)
    {
        this.onCommentClickListener = onCommentClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        int viewType = getItemViewType(position);
        OrderPageVo orderPageVo = searchVoList.get(position);
        OrderSearchResponseItem item = orderPageVo.getItem();

        if (viewType == VIEW_TYPE_ORDER_ITEM)
        {
            MyOrderViewHolder viewHolder = (MyOrderViewHolder) holder;

            ImageLoader.getInstance().displayImage(Constant.BASE_URL + item.getShopMainImg(), viewHolder.ivOrderPageImg, DisplayImgOptionUtils.getShopDio());

            viewHolder.tvOrderPageOrderNo.setText(item.getOrderNo());
            viewHolder.tvOrderPageShopTag.setText(item.getShopTag());
            viewHolder.tvOrderPageSaleId.setText(item.getUserIdSale());

            String status = item.getOrderStatus();

            if (Constant.ORDER_STATUS_TO_DEAL.equals(status))
            {
                status = Constant.ORDER_STATUS_TO_DEAL_TITLE;
            } else if (Constant.ORDER_STATUS_DEALING.equals(status))
            {
                status = Constant.ORDER_STATUS_DEALING_TITLE;
            } else if (Constant.ORDER_STATUS_RECEIVING.equals(status))
            {
                status = Constant.ORDER_STATUS_RECEIVING_TITLE;
            } else if (Constant.ORDER_STATUS_COMMENTING.equals(status))
            {
                status = Constant.ORDER_STATUS_COMMENTING_TITLE;

                viewHolder.ivOrderPageComment.setVisibility(View.VISIBLE);
            } else if (Constant.ORDER_STATUS_FINISHING.equals(status))
            {
                status = Constant.ORDER_STATUS_FINISHING_TITLE;
            }
            viewHolder.tvOrderPageOrderStatus.setText(context.getString(R.string.order_status) + status);

            viewHolder.tvOrderPageOrderMoney.setText(context.getString(R.string.price) + item.getMoney());
            viewHolder.tvOrderPageUploadTime.setText(context.getString(R.string.upload_time) + item.getUploadTime());

            viewHolder.tvOrderPageBust.setText("" + item.getBust());
            viewHolder.tvOrderPageWaist.setText("" + item.getWaist());
            viewHolder.tvOrderPageHip.setText("" + item.getHip());
            viewHolder.tvOrderPageHand.setText("" + item.getHandLength());
            viewHolder.tvOrderPageLeg.setText("" + item.getLegLength());
        } else if (viewType == VIEW_TYPE_NO_DATA)
        {
            MyOnlyTextHolder viewHolder = (MyOnlyTextHolder) holder;
            viewHolder.tvDesc.setText(orderPageVo.getNoDataDesc());
        }

    }

    class MyOrderViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.iv_order_page_img)
        ImageView ivOrderPageImg;
        @BindView(R.id.tv_order_page_order_no)
        TextView tvOrderPageOrderNo;
        @BindView(R.id.tv_order_page_shopTag)
        TextView tvOrderPageShopTag;
        @BindView(R.id.tv_order_page_order_status)
        TextView tvOrderPageOrderStatus;
        @BindView(R.id.tv_order_page_order_money)
        TextView tvOrderPageOrderMoney;
        @BindView(R.id.tv_order_page_uploadTime)
        TextView tvOrderPageUploadTime;
        @BindView(R.id.tv_order_page_bust)
        TextView tvOrderPageBust;
        @BindView(R.id.tv_order_page_waist)
        TextView tvOrderPageWaist;
        @BindView(R.id.tv_order_page_hip)
        TextView tvOrderPageHip;
        @BindView(R.id.tv_order_page_hand)
        TextView tvOrderPageHand;
        @BindView(R.id.tv_order_page_leg)
        TextView tvOrderPageLeg;
        @BindView(R.id.tv_order_page_sale_id)
        TextView tvOrderPageSaleId;
        @BindView(R.id.iv_order_page_comment)
        TextView ivOrderPageComment;

        public MyOrderViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick(R.id.iv_order_page_comment)
        public void onViewClicked()
        {

            if (onCommentClickListener != null)
            {
                OrderPageVo orderPageVo = searchVoList.get(getLayoutPosition());
                OrderSearchResponseItem item = orderPageVo.getItem();
                if (item == null)
                    return;

                onCommentClickListener.onCommentClick(item.getShopId(), item.getUserIdBuy(), item.getOrderId());
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return searchVoList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return searchVoList.get(position).getViewType();
    }
}
