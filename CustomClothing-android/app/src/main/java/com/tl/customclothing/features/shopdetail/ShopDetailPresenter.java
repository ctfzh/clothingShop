package com.tl.customclothing.features.shopdetail;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BasePresenter;
import com.tl.customclothing.http.CCHttpHelper;
import com.tl.customclothing.http.config.ShopDetailConfig;
import com.tl.customclothing.http.request.ShopDetailRequest;
import com.tl.customclothing.http.response.CommentItem;
import com.tl.customclothing.http.response.ResponseTemplate;
import com.tl.customclothing.http.response.ShopDetailResponse;
import com.tl.customclothing.utils.NullUtils;
import com.tl.customclothing.utils.emotion.TextEmotion;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static com.tl.customclothing.features.shopdetail.ShopDetailActivity.INTENT_KEY_SHOP_ID;

/**
 * Created by tianlin on 2017/4/18.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class ShopDetailPresenter extends BasePresenter<ShopDetailActivity>
{
    @Override
    public void initData()
    {
        int shopId = view.getIntent().getIntExtra(INTENT_KEY_SHOP_ID, 0);
        ShopDetailRequest shopDetailRequest = new ShopDetailRequest();
        shopDetailRequest.setShopId(shopId);

        getDetail(shopDetailRequest);
    }

    public void getDetail(ShopDetailRequest shopDetailRequest)
    {
        view.showProgressDialog(view.getString(R.string.lodding) + TextEmotion.qidai);

        CCHttpHelper
                .newInstance()
                .setBaseConfig(ShopDetailConfig.class)
                .setRequest(shopDetailRequest)
                .setThisReadLocal(true)
                .getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseTemplate>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        if (view == null)
                            return;

                        view.hideProgressDialog();
                        view.response(view, view.getString(R.string.network_error) + TextEmotion.ganga);
                    }

                    @Override
                    public void onNext(ResponseTemplate responseTemplate)
                    {
                        if (view == null)
                            return;

                        if (responseTemplate == null)
                        {
                            view.hideProgressDialog();
                            view.response(view, view.getString(R.string.network_error) + TextEmotion.ganga);
                            return;
                        }
                        ResponseTemplate<ShopDetailResponse> response = responseTemplate;

                        ShopDetailResponse shopDetailResponse = response.getContent();

                        if (shopDetailResponse == null)
                        {
                            view.hideProgressDialog();
                            view.response(view, view.getString(R.string.no_data) + TextEmotion.ganga);
                            return;
                        }

                        List<ShopDetailVo> shopDetailVos = makeShopDetailVo(shopDetailResponse);

                        view.hideProgressDialog();
                        view.setAdapter(shopDetailVos);

                    }
                });
    }

    private List<ShopDetailVo> makeShopDetailVo(final ShopDetailResponse shopDetailResponse)
    {
        List<ShopDetailVo> shopDetailVos = new ArrayList<>();

        // 创建ViewPager的item
        ShopDetailVo shopDetailVo = new ShopDetailVo();
        List<String> imgList = new ArrayList<>();
        if (!NullUtils.isEmpty(shopDetailResponse.getShopImg1()))
            imgList.add(shopDetailResponse.getShopImg1());
        if (!NullUtils.isEmpty(shopDetailResponse.getShopImg2()))
            imgList.add(shopDetailResponse.getShopImg2());
        if (!NullUtils.isEmpty(shopDetailResponse.getShopImg3()))
            imgList.add(shopDetailResponse.getShopImg3());
        shopDetailVo.setViewType(ShopDetailAdapter.VIEW_TYPE_VIEW_PAGER);
        shopDetailVo.setShopImgs(imgList);
        shopDetailVos.add(shopDetailVo);

        // 创建基本信息的item
        shopDetailVo = new ShopDetailVo();
        shopDetailVo.setViewType(ShopDetailAdapter.VIEW_TYPE_BASE_INFO);
        shopDetailVo.setShopTag(shopDetailResponse.getShopTag());
        shopDetailVo.setShopPrice(shopDetailResponse.getShopPrice());
        shopDetailVo.setShopSalesCount(shopDetailResponse.getShopSalesCount());
        shopDetailVo.setShopType(shopDetailResponse.getShopType());
        shopDetailVo.setShopGender(shopDetailResponse.getShopGender());
        shopDetailVo.setShopSalesAddr(shopDetailResponse.getShopSalesAddr());
        shopDetailVo.setShopImgOnModel(shopDetailResponse.getShopImgOnModel());
        shopDetailVo.setUserIdSale(shopDetailResponse.getUserIdSale());
        shopDetailVo.setShopId(shopDetailResponse.getShopId());
        shopDetailVo.setShopMainImg(shopDetailResponse.getShopMainImg());
        shopDetailVos.add(shopDetailVo);

        // 加载衣服模型图片
        view.loadModelImg(shopDetailResponse.getShopImgOnModel());

        // 创建评论基本信息
        if (shopDetailResponse.getComments() != null)
        {
            for (int i = 0; i < shopDetailResponse.getComments().size(); i++)
            {
                CommentItem commentItem = shopDetailResponse.getComments().get(i);

                shopDetailVo = new ShopDetailVo();

                shopDetailVo.setViewType(ShopDetailAdapter.VIEW_TYPE_COMMENT);
                shopDetailVo.setShopId(commentItem.getId());
                shopDetailVo.setUserId(commentItem.getUserId());
                shopDetailVo.setContent(commentItem.getContent());
                shopDetailVo.setCommentTime(commentItem.getCommentTime());
                shopDetailVo.setLastUpdateTime(commentItem.getLastUpdateTime());
                shopDetailVo.setCommentUserNickName(commentItem.getCommentUserNickName());

                shopDetailVos.add(shopDetailVo);
            }
        }
        return shopDetailVos;
    }

}
