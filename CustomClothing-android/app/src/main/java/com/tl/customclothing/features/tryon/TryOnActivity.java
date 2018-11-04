package com.tl.customclothing.features.tryon;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BaseActivity;
import com.tl.customclothing.features.login.LoginActivity;
import com.tl.customclothing.http.request.SubmitOrderRequest;
import com.tl.customclothing.model.database.CartShopRealm;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.NullUtils;
import com.tl.customclothing.utils.bitmap.DisplayImgOptionUtils;
import com.tl.customclothing.utils.data.DataProvider;
import com.tl.customclothing.utils.emotion.TextEmotion;
import com.tl.customclothing.utils.ui.TlDialogFragment;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tianlin on 2017/4/20.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class TryOnActivity extends BaseActivity implements ITryOnView, TryOnCartAdapter.OnItemShopClickListener
{
    public static final String INTENT_KEY_SHOPID = "INTENT_KEY_SHOPID";
    public static final String INTENT_KEY_USERID_SALE = "INTENT_KEY_USERID_SALE";
    public static final String INTENT_KEY_IMG_ON_MODEL = "INTENT_KEY_IMG_ON_MODEL";
    public static final String INTENT_KEY_SHOP_TYPE = "INTENT_KEY_SHOP_TYPE";
    public static final String INTENT_KEY_SHOP_GENDER = "INTENT_KEY_SHOP_GENDER";
    public static final String INTENT_KEY_SHOP_PRICE = "INTENT_KEY_SHOP_PRICE";

    @BindView(R.id.tv_try_on_bust)
    TextView tvTryOnBust;
    @BindView(R.id.sb_try_on_bust)
    SeekBar sbTryOnBust;
    @BindView(R.id.tv_try_on_waist)
    TextView tvTryOnWaist;
    @BindView(R.id.sb_try_on_waist)
    SeekBar sbTryOnWaist;
    @BindView(R.id.tv_try_on_hip)
    TextView tvTryOnHip;
    @BindView(R.id.sb_try_on_hip)
    SeekBar sbTryOnHip;
    @BindView(R.id.tv_try_on_hand)
    TextView tvTryOnHand;
    @BindView(R.id.sb_try_on_hand)
    SeekBar sbTryOnHand;
    @BindView(R.id.tv_try_on_leg)
    TextView tvTryOnLeg;
    @BindView(R.id.sb_try_on_leg)
    SeekBar sbTryOnLeg;

    @BindView(R.id.ll_tyr_on_body_adjust)
    LinearLayout llTyrOnBodyAdjust;

    @BindView(R.id.man_under)
    ImageView manUnder;
    @BindView(R.id.man_up)
    ImageView manUp;
    @BindView(R.id.man_head)
    ImageView manHead;

    // 是显示用户头像还是模特头像
    boolean isModelIcon = true;

    // 模特图片的长宽
    int manW;
    int manH;
    // 头部收缩率
    float headScaleX = 0;
    float headScaleY = 0;
    // 胸部收缩率
    float bustScaleX = 0;
    float bustScaleY = 0;
    // 腰部收缩率
    float waistScaleX = 0;
    float waistScaleY = 0;
    // 手部收缩率
    float handScaleX = 0;
    float handScaleY = 0;
    // 臀部收缩率
    float hipScaleX = 0;
    float hipScaleY = 0;
    // 腿部收缩率
    float legScaleX = 0;
    float legScaleY = 0;

    // 负责头部的放缩，让图片看起来不违和
    Matrix headMatrix;
    // 负责臀部，腿长的放缩，臀部在X方向上，腿长在Y方向上
    Matrix underMatrix;
    // 负责胸围，腰围和手长
    Matrix upMatrix;

    @BindView(R.id.ll_shop_detail_submit)
    LinearLayout llShopDetailSubmit;
    @BindView(R.id.tv_shop_detail_change_head)
    TextView tvShopDetailChangeHead;
    @BindView(R.id.ll_shop_detail_change_head)
    LinearLayout llShopDetailChangeHead;
    @BindView(R.id.iv_try_on_open_cart)
    ImageView ivTryOnOpenCart;
    @BindView(R.id.rv_try_on_cart)
    PullLoadMoreRecyclerView rvTryOnCart;
    @BindView(R.id.ll_try_on_cart)
    LinearLayout llTryOnCart;

    // 保存当前上衣的信息
    private int shopIdUp;
    private String userIdSaleUp = Constant.NULL;
    private String imgOnModelUp;
    private float priceUp;

    // 保存下装的信息
    private int shopIdUnder;
    private String userIdSaleUnder = Constant.NULL;
    private String imgOnModelUnder;
    private float priceUnder;

    TryOnPresenter tryOnPresenter;

    boolean isCartOpen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_on);
        ButterKnife.bind(this);
        initView();
        tryOnPresenter = new TryOnPresenter();
        tryOnPresenter.attachView(this);

        tryOnPresenter.initData();

    }

    @Override
    public void showCart()
    {

        ObjectAnimator animator = ObjectAnimator.ofFloat(llTryOnCart, "translationX", getResources().getDimension(R.dimen.shop_main_img_size_w), 0);
        animator.setDuration(Constant.ANIMATION_DURATION);
        animator.start();
    }

    @Override
    public void hideCart()
    {

        ObjectAnimator animator = ObjectAnimator.ofFloat(llTryOnCart, "translationX", 0, getResources().getDimension(R.dimen.shop_main_img_size_w));
        animator.setDuration(Constant.ANIMATION_DURATION);
        animator.start();
    }

    @Override
    public void onItemClick(CartShopRealm cartShopRealm)
    {

        if (cartShopRealm == null)
            return;

        if (Constant.UP.equals(cartShopRealm.getShopType()))
        {

            shopIdUp = cartShopRealm.getShopId();
            userIdSaleUp = cartShopRealm.getUserIdSale();
            imgOnModelUp = cartShopRealm.getImgOnModel();
            priceUp = cartShopRealm.getShopPrice();
            ImageLoader.getInstance().displayImage(Constant.BASE_URL + imgOnModelUp, manUp, DisplayImgOptionUtils.loadModel());

        } else
        {
            shopIdUnder = cartShopRealm.getShopId();
            userIdSaleUnder = cartShopRealm.getUserIdSale();
            imgOnModelUnder = cartShopRealm.getImgOnModel();
            priceUnder = cartShopRealm.getShopPrice();
            ImageLoader.getInstance().displayImage(Constant.BASE_URL + imgOnModelUnder, manUnder, DisplayImgOptionUtils.loadModel());
        }

    }

    @Override
    public void setCartAdapter(List<CartShopRealm> cartShopRealms)
    {
        TryOnCartAdapter tryOnCartAdapter = new TryOnCartAdapter(this, cartShopRealms);
        tryOnCartAdapter.setOnItemShopClickListener(this);
        rvTryOnCart.setAdapter(tryOnCartAdapter);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        tryOnPresenter.detachView();
    }

    @Override
    public void initView()
    {

        Intent intent = getIntent();
        String shopType = intent.getStringExtra(INTENT_KEY_SHOP_TYPE);

        if (Constant.UP.equals(shopType))
        {
            shopIdUp = intent.getIntExtra(INTENT_KEY_SHOPID, 0);
            userIdSaleUp = intent.getStringExtra(INTENT_KEY_USERID_SALE);
            imgOnModelUp = intent.getStringExtra(INTENT_KEY_IMG_ON_MODEL);
            priceUp = intent.getFloatExtra(INTENT_KEY_SHOP_PRICE, 0);
        } else
        {
            shopIdUnder = intent.getIntExtra(INTENT_KEY_SHOPID, 0);
            userIdSaleUnder = intent.getStringExtra(INTENT_KEY_USERID_SALE);
            imgOnModelUnder = intent.getStringExtra(INTENT_KEY_IMG_ON_MODEL);
            priceUnder = intent.getFloatExtra(INTENT_KEY_SHOP_PRICE, 0);
        }

        String userGender = DataProvider.getUserGender(this);

        if (Constant.MALE.equals(userGender))
        {
            // 头部
            ImageLoader.getInstance().loadImage(Constant.BASE_URL + Constant.MODEL_HEAD_MALE, DisplayImgOptionUtils.loadModel(), new ImageLoadingListener()
            {
                @Override
                public void onLoadingStarted(String s, View view)
                {
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason)
                {
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap)
                {
                    manW = bitmap.getWidth();
                    manH = bitmap.getHeight();
                    manHead.setImageBitmap(bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view)
                {

                }
            });

            // 穿上身
            if (Constant.UP.equals(shopType))
            {
                // 上身模特
                ImageLoader.getInstance().loadImage(Constant.BASE_URL + Constant.MODEL_UP_MALE, DisplayImgOptionUtils.loadModel(), new ImageLoadingListener()
                {
                    @Override
                    public void onLoadingStarted(String s, View view)
                    {
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason)
                    {
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap)
                    {
                        manW = bitmap.getWidth();
                        manH = bitmap.getHeight();
                        manUp.setImageBitmap(bitmap);

                        // 上身服装
                        ImageLoader.getInstance().displayImage(Constant.BASE_URL + imgOnModelUp, manUp, DisplayImgOptionUtils.loadModel());
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view)
                    {
                    }
                });

                // 下身模特
                ImageLoader.getInstance().displayImage(Constant.BASE_URL + Constant.MODEL_UNDER_MALE, manUnder, DisplayImgOptionUtils.loadModel());
            }
            // 穿下装
            else if (Constant.UNDER.equals(shopType))
            {
                // 上身模特
                ImageLoader.getInstance().displayImage(Constant.BASE_URL + Constant.MODEL_UP_MALE, manUp, DisplayImgOptionUtils.loadModel());

                // 下身模特
                ImageLoader.getInstance().loadImage(Constant.BASE_URL + Constant.MODEL_UNDER_MALE, DisplayImgOptionUtils.loadModel(), new ImageLoadingListener()
                {
                    @Override
                    public void onLoadingStarted(String s, View view)
                    {
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason)
                    {
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap)
                    {
                        manW = bitmap.getWidth();
                        manH = bitmap.getHeight();
                        manUnder.setImageBitmap(bitmap);

                        // 下身服装
                        ImageLoader.getInstance().displayImage(Constant.BASE_URL + imgOnModelUnder, manUnder, DisplayImgOptionUtils.loadModel());

                    }

                    @Override
                    public void onLoadingCancelled(String s, View view)
                    {

                    }
                });
            }
        } else
        {
            // 头部
            ImageLoader.getInstance().loadImage(Constant.BASE_URL + Constant.MODEL_HEAD_FEMALE, DisplayImgOptionUtils.loadModel(), new ImageLoadingListener()
            {
                @Override
                public void onLoadingStarted(String s, View view)
                {
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason)
                {
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap)
                {
                    manW = bitmap.getWidth();
                    manH = bitmap.getHeight();
                    manHead.setImageBitmap(bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view)
                {

                }
            });

            // 穿上身
            if (Constant.UP.equals(shopType))
            {
                // 上身模特
                ImageLoader.getInstance().loadImage(Constant.BASE_URL + Constant.MODEL_UP_FEMALE, DisplayImgOptionUtils.loadModel(), new ImageLoadingListener()
                {
                    @Override
                    public void onLoadingStarted(String s, View view)
                    {
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason)
                    {
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap)
                    {
                        manW = bitmap.getWidth();
                        manH = bitmap.getHeight();
                        manUp.setImageBitmap(bitmap);

                        // 上身服装
                        ImageLoader.getInstance().displayImage(Constant.BASE_URL + imgOnModelUp, manUp, DisplayImgOptionUtils.loadModel());
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view)
                    {
                    }
                });

                // 下身模特
                ImageLoader.getInstance().displayImage(Constant.BASE_URL + Constant.MODEL_UNDER_FEMALE, manUnder, DisplayImgOptionUtils.loadModel());
            }
            // 穿下装
            else if (Constant.UNDER.equals(shopType))
            {
                // 上身模特
                ImageLoader.getInstance().displayImage(Constant.BASE_URL + Constant.MODEL_UP_FEMALE, manUp, DisplayImgOptionUtils.loadModel());

                // 下身模特
                ImageLoader.getInstance().loadImage(Constant.BASE_URL + Constant.MODEL_UNDER_FEMALE, DisplayImgOptionUtils.loadModel(), new ImageLoadingListener()
                {
                    @Override
                    public void onLoadingStarted(String s, View view)
                    {
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason)
                    {
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap)
                    {
                        manW = bitmap.getWidth();
                        manH = bitmap.getHeight();
                        manUnder.setImageBitmap(bitmap);

                        // 下身服装
                        ImageLoader.getInstance().displayImage(Constant.BASE_URL + imgOnModelUnder, manUnder, DisplayImgOptionUtils.loadModel());

                    }

                    @Override
                    public void onLoadingCancelled(String s, View view)
                    {

                    }
                });
            }
        }

        // 初始化Matrix
        headMatrix = manHead.getImageMatrix();
        underMatrix = manUnder.getImageMatrix();
        upMatrix = manUp.getImageMatrix();

        // 初始化sb
        initSeekBar();

        // 初始化rv
        rvTryOnCart.setLinearLayout();
        rvTryOnCart.setPullRefreshEnable(false);
        rvTryOnCart.setPushRefreshEnable(false);

        // 先隐藏购物车里的东西
        hideCart();

    }

    @Override
    public void adjustModel()
    {
        int bustOffset = sbTryOnBust.getProgress() - Constant.MODEL_NORMAL_BUST_RANGE / 2;
        int waistOffset = sbTryOnWaist.getProgress() - Constant.MODEL_NORMAL_WAIST_RANGE / 2;
        int hipOffset = sbTryOnHip.getProgress() - Constant.MODEL_NORMAL_HIP_RANGE / 2;
        int handOffset = sbTryOnHand.getProgress() - Constant.MODEL_NORMAL_HAND_RANGE / 2;
        int legOffset = sbTryOnLeg.getProgress() - Constant.MODEL_NORMAL_LEG_RANGE / 2;

        // 头部的变化率,让头部看起来不与图片脱离
        headScaleX = bustOffset * 0.003f + waistOffset * 0.0015f + hipOffset * 0.0015f;
        // 胸围的变化率
        bustScaleX = bustOffset * 0.005f + waistOffset * 0.004f + hipOffset * 0.002f;
        // 腰围的变化率
        waistScaleX = bustOffset * 0.004f + waistOffset * 0.005f + hipOffset * 0.003f;
        // 臀部的变化率
        hipScaleX = bustOffset * 0.002f + waistOffset * 0.003f + hipOffset * 0.005f;

        // 腿长的变化率
        legScaleY = legOffset * 0.003f + handOffset * 0.001f;
        // 手长的变化率
        handScaleY = handOffset * 0.001f;
        // 头部的变化率
        headScaleY = handOffset * 0.001f;

        // 变动头部是为了让图片看起来比较和谐
        headMatrix.setScale(1.0f + headScaleX, 1.0f + headScaleY, manW / 2, manH / 9);
        // 要不占有比上腰部为4:3
        upMatrix.setScale(1.0f + 0.8f * bustScaleX + 0.6f * waistScaleX, 1.0f + handScaleY, manW / 2, manH / 2);
        // 负责臀部和腿长
        underMatrix.setScale(1.0f + hipScaleX, 1.0f + legScaleY, manW / 2, manH / 2);

        manHead.setImageMatrix(headMatrix);
        manUp.setImageMatrix(upMatrix);
        manUnder.setImageMatrix(underMatrix);
    }

    @Override
    public void initSeekBar()
    {
        sbTryOnBust.setMax(Constant.MODEL_NORMAL_BUST_RANGE);
        sbTryOnWaist.setMax(Constant.MODEL_NORMAL_WAIST_RANGE);
        sbTryOnHip.setMax(Constant.MODEL_NORMAL_HIP_RANGE);
        sbTryOnHand.setMax(Constant.MODEL_NORMAL_HAND_RANGE);
        sbTryOnLeg.setMax(Constant.MODEL_NORMAL_LEG_RANGE);

        sbTryOnBust.setProgress(Constant.MODEL_NORMAL_BUST_RANGE / 2);
        sbTryOnWaist.setProgress(Constant.MODEL_NORMAL_WAIST_RANGE / 2);
        sbTryOnHip.setProgress(Constant.MODEL_NORMAL_HIP_RANGE / 2);
        sbTryOnHand.setProgress(Constant.MODEL_NORMAL_HAND_RANGE / 2);
        sbTryOnLeg.setProgress(Constant.MODEL_NORMAL_LEG_RANGE / 2);

        tvTryOnBust.setText("" + (Constant.MODEL_NORMAL_BUST + Constant.MODEL_NORMAL_BUST_RANGE / 2));
        tvTryOnWaist.setText("" + (Constant.MODEL_NORMAL_WAIST + Constant.MODEL_NORMAL_WAIST_RANGE / 2));
        tvTryOnHip.setText("" + (Constant.MODEL_NORMAL_HIP + Constant.MODEL_NORMAL_HIP_RANGE / 2));
        tvTryOnHand.setText("" + (Constant.MODEL_NORMAL_HAND + Constant.MODEL_NORMAL_HAND_RANGE / 2));
        tvTryOnLeg.setText("" + (Constant.MODEL_NORMAL_LEG + Constant.MODEL_NORMAL_LEG_RANGE / 2));

        sbTryOnBust.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int preProgress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                tvTryOnBust.setText("" + (Constant.MODEL_NORMAL_BUST + seekBar.getProgress()));
                if (this.preProgress == seekBar.getProgress())
                    return;

                adjustModel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                this.preProgress = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });
        sbTryOnWaist.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int preProgress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                tvTryOnWaist.setText("" + (50 + progress));

                if (this.preProgress == seekBar.getProgress())
                    return;

                adjustModel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                this.preProgress = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });
        sbTryOnHip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int preProgress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                tvTryOnHip.setText("" + (70 + progress));
                if (this.preProgress == seekBar.getProgress())
                    return;

                adjustModel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                this.preProgress = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });
        sbTryOnHand.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int preProgress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                tvTryOnHand.setText("" + (70 + progress));

                if (this.preProgress == seekBar.getProgress())
                    return;

                adjustModel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                this.preProgress = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });
        sbTryOnLeg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int preProgress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                tvTryOnLeg.setText("" + (90 + progress));

                if (this.preProgress == seekBar.getProgress())
                    return;

                adjustModel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                this.preProgress = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });
    }

    @Override
    public void initModel()
    {
        sbTryOnBust.setProgress(Constant.MODEL_NORMAL_BUST_RANGE / 2);
        sbTryOnWaist.setProgress(Constant.MODEL_NORMAL_WAIST_RANGE / 2);
        sbTryOnHip.setProgress(Constant.MODEL_NORMAL_HIP_RANGE / 2);
        sbTryOnHand.setProgress(Constant.MODEL_NORMAL_HAND_RANGE / 2);
        sbTryOnLeg.setProgress(Constant.MODEL_NORMAL_LEG_RANGE / 2);

        adjustModel();
    }

    @OnClick({R.id.ll_shop_detail_change_head, R.id.ll_shop_detail_submit, R.id.iv_try_on_open_cart})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.ll_shop_detail_change_head:

                final String userId = DataProvider.getUserLoginId(this);
                final String userIcon = DataProvider.getUserIconUrl(this);

                if (Constant.NULL.equals(userId))
                {
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            response(TryOnActivity.this, getString(R.string.please_login) + TextEmotion.huaiyi);
                            Intent intent = new Intent(TryOnActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }, 500);
                    return;
                } else if (Constant.NULL.equals(userIcon))
                {
                    response(TryOnActivity.this, getString(R.string.you_have_no_icon) + TextEmotion.huaiyi);
                    return;
                }

                TlDialogFragment
                        .newInstance()
                        .setNoText(getString(R.string.no))
                        .setOkText(getString(R.string.ok))
                        .setTitle(getString(R.string.tips))
                        .setMsg(getString(R.string.make_sure_to_change_icon) + TextEmotion.huanhu)
                        .setOkListener(new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if (isModelIcon)
                                {
                                    tvShopDetailChangeHead.setText(R.string.use_model_icon);
                                    isModelIcon = false;

                                    // 当前是模特头像，需要转换成用户头像
                                    ImageLoader.getInstance().loadImage(Constant.BASE_URL + userIcon, DisplayImgOptionUtils.loadModel(), new ImageLoadingListener()
                                    {
                                        @Override
                                        public void onLoadingStarted(String s, View view)
                                        {
                                        }

                                        @Override
                                        public void onLoadingFailed(String s, View view, FailReason failReason)
                                        {
                                        }

                                        @Override
                                        public void onLoadingComplete(String s, View view, Bitmap bitmap)
                                        {
                                            manW = bitmap.getWidth();
                                            manH = bitmap.getHeight();
                                            manHead.setImageBitmap(bitmap);
                                        }

                                        @Override
                                        public void onLoadingCancelled(String s, View view)
                                        {

                                        }
                                    });
                                } else
                                {
                                    tvShopDetailChangeHead.setText(R.string.use_user_icon);
                                    isModelIcon = true;

                                    String userGender = DataProvider.getUserGender(TryOnActivity.this);

                                    if (Constant.MALE.equals(userGender))
                                    {
                                        // 头部
                                        ImageLoader.getInstance().loadImage(Constant.BASE_URL + Constant.MODEL_HEAD_MALE, DisplayImgOptionUtils.loadModel(), new ImageLoadingListener()
                                        {
                                            @Override
                                            public void onLoadingStarted(String s, View view)
                                            {
                                            }

                                            @Override
                                            public void onLoadingFailed(String s, View view, FailReason failReason)
                                            {
                                            }

                                            @Override
                                            public void onLoadingComplete(String s, View view, Bitmap bitmap)
                                            {
                                                manW = bitmap.getWidth();
                                                manH = bitmap.getHeight();
                                                manHead.setImageBitmap(bitmap);
                                            }

                                            @Override
                                            public void onLoadingCancelled(String s, View view)
                                            {

                                            }
                                        });
                                    } else
                                    {
                                        // 头部
                                        ImageLoader.getInstance().loadImage(Constant.BASE_URL + Constant.MODEL_HEAD_FEMALE, DisplayImgOptionUtils.loadModel(), new ImageLoadingListener()
                                        {
                                            @Override
                                            public void onLoadingStarted(String s, View view)
                                            {
                                            }

                                            @Override
                                            public void onLoadingFailed(String s, View view, FailReason failReason)
                                            {
                                            }

                                            @Override
                                            public void onLoadingComplete(String s, View view, Bitmap bitmap)
                                            {
                                                manW = bitmap.getWidth();
                                                manH = bitmap.getHeight();
                                                manHead.setImageBitmap(bitmap);
                                            }

                                            @Override
                                            public void onLoadingCancelled(String s, View view)
                                            {

                                            }
                                        });
                                    }
                                }

                                initModel();

                            }
                        })
                        .show(getSupportFragmentManager(), Constant.ALERT_DIALOG);

                break;
            case R.id.ll_shop_detail_submit:

                TlDialogFragment
                        .newInstance()
                        .setNoText(getString(R.string.no))
                        .setOkText(getString(R.string.ok))
                        .setTitle(getString(R.string.tips))
                        .setMsg(getString(R.string.make_sure_to_submit) + TextEmotion.huanhu)
                        .setOkListener(new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // 买家id
                                String userId = DataProvider.getUserLoginId(TryOnActivity.this);

                                SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_TIME_WITHOUT_DIVIDER);
                                if (Constant.NULL.equals(userId))
                                {
                                    new Handler().postDelayed(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            response(TryOnActivity.this, getString(R.string.please_login) + TextEmotion.huaiyi);
                                            Intent intent = new Intent(TryOnActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    }, 500);
                                } else
                                {

                                    // 如果上衣有提交上衣
                                    if (!userIdSaleUp.equals(Constant.NULL))
                                    {

                                        SubmitOrderRequest submitOrderRequest = new SubmitOrderRequest();

                                        // 订单头 + 买家id后4位
                                        StringBuilder sb = new StringBuilder("MFTXTL")
                                                .append(userId.substring(userId.length() - 4));
                                        // 买家id
                                        submitOrderRequest.setUserIdBuy(userId);
                                        // 订单状态
                                        submitOrderRequest.setOrderStatus(Constant.ORDER_STATUS_TO_DEAL);

                                        // 订单基本参数
                                        submitOrderRequest.setBust(Float.parseFloat(tvTryOnBust.getText().toString()));
                                        submitOrderRequest.setWaist(Float.parseFloat(tvTryOnWaist.getText().toString()));
                                        submitOrderRequest.setHip(Float.parseFloat(tvTryOnHip.getText().toString()));
                                        submitOrderRequest.setHandLength(Float.parseFloat(tvTryOnHand.getText().toString()));
                                        submitOrderRequest.setLegLength(Float.parseFloat(tvTryOnLeg.getText().toString()));

                                        // 时间
                                        sdf = new SimpleDateFormat(Constant.DATE_TIME);
                                        submitOrderRequest.setUploadTime(sdf.format(new Date()));
                                        submitOrderRequest.setLastUpdateTime(sdf.format(new Date()));

                                        if (!NullUtils.isEmpty(userIdSaleUp))
                                            sb.append(userIdSaleUp.substring(userIdSaleUp.length() - 4));
                                        sb.append(sdf.format(new Date()));
                                        submitOrderRequest.setOrderNo(sb.toString());

                                        submitOrderRequest.setShopId(shopIdUp);
                                        submitOrderRequest.setUserIdSale(userIdSaleUp);
                                        submitOrderRequest.setMoney(priceUp);
                                        tryOnPresenter.submitOrder(submitOrderRequest);
                                    }

                                    // 如果下装有，提交下装
                                    if (!userIdSaleUnder.equals(Constant.NULL))
                                    {

                                        SubmitOrderRequest submitOrderRequest = new SubmitOrderRequest();

                                        // 订单头 + 买家id后4位
                                        StringBuilder sb = new StringBuilder("MFTXTL")
                                                .append(userId.substring(userId.length() - 4));
                                        // 买家id
                                        submitOrderRequest.setUserIdBuy(userId);
                                        // 订单状态
                                        submitOrderRequest.setOrderStatus(Constant.ORDER_STATUS_TO_DEAL);

                                        // 订单基本参数
                                        submitOrderRequest.setBust(Float.parseFloat(tvTryOnBust.getText().toString()));
                                        submitOrderRequest.setWaist(Float.parseFloat(tvTryOnWaist.getText().toString()));
                                        submitOrderRequest.setHip(Float.parseFloat(tvTryOnHip.getText().toString()));
                                        submitOrderRequest.setHandLength(Float.parseFloat(tvTryOnHand.getText().toString()));
                                        submitOrderRequest.setLegLength(Float.parseFloat(tvTryOnLeg.getText().toString()));

                                        // 时间
                                        sdf = new SimpleDateFormat(Constant.DATE_TIME);
                                        submitOrderRequest.setUploadTime(sdf.format(new Date()));
                                        submitOrderRequest.setLastUpdateTime(sdf.format(new Date()));

                                        if (!NullUtils.isEmpty(userIdSaleUnder))
                                            sb.append(userIdSaleUnder.substring(userIdSaleUnder.length() - 4));
                                        sb.append(sdf.format(new Date()));
                                        submitOrderRequest.setOrderNo(sb.toString());

                                        submitOrderRequest.setShopId(shopIdUnder);
                                        submitOrderRequest.setUserIdSale(userIdSaleUnder);
                                        submitOrderRequest.setMoney(priceUnder);
                                        tryOnPresenter.submitOrder(submitOrderRequest);
                                    }

                                }
                            }
                        })
                        .show(getSupportFragmentManager(), Constant.ALERT_DIALOG);
                break;
            case R.id.iv_try_on_open_cart:
                if (isCartOpen)
                {
                    hideCart();
                    isCartOpen = false;
                } else
                {
                    showCart();
                    isCartOpen = true;
                }
                Log.d("my", "iv_try_on_open_cart = " + isCartOpen);

                break;
        }
    }
}
