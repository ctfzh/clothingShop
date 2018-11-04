package com.tl.customclothing.features.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BaseActivity;
import com.tl.customclothing.features.login.LoginActivity;
import com.tl.customclothing.features.main.cart.CartFragment;
import com.tl.customclothing.features.main.home.HomeFragment;
import com.tl.customclothing.features.main.home.ShopQueryActivity;
import com.tl.customclothing.features.main.my.MyFragment;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.bitmap.DisplayImgOptionUtils;
import com.tl.customclothing.utils.data.DataProvider;
import com.tl.customclothing.utils.eventbus.BusEvents;
import com.tl.customclothing.utils.eventbus.EventBusUtils;
import com.tl.customclothing.utils.ui.DrawableRadioButton;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tl.customclothing.R.id.fl_my_user_data;

public class MainActivity extends BaseActivity implements
        RadioGroup.OnCheckedChangeListener,
        IMainView,
        Runnable,
        ImageLoadingListener
{

    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    @BindView(R.id.tv_my_user_jifen)
    TextView tvMyUserJifen;
    @BindView(fl_my_user_data)
    FrameLayout flMyUserData;
    @BindView(R.id.collapse_tool_bar)
    CollapsingToolbarLayout collapseToolBar;
    @BindView(R.id.carousel_view)
    CarouselView carouselView;
    @BindView(R.id.rb_main_home)
    DrawableRadioButton rbMainHome;
    @BindView(R.id.rb_main_cart)
    DrawableRadioButton rbMainCart;
    @BindView(R.id.rb_main_my)
    DrawableRadioButton rbMainMy;
    @BindView(R.id.iv_my_user_gender)
    ImageView ivMyUserGender;

    // 主控制器
    MainPresenter mainPresenter;

    // 首页的fragment
    HomeFragment homeFragment;
    // 购物车的fragment
    CartFragment cartFragment;
    // 我的fragment
    MyFragment myFragment;

    // 是否退出
    private boolean isExit = false;

    // 两秒钟退出
    Handler handler = new Handler();

    private int checkId = R.id.rb_main_home;

    public interface OnOptionMenuSelectListener
    {
        void onDeleteClick();
    }

    private OnOptionMenuSelectListener onOptionMenuSelectListener;

    public OnOptionMenuSelectListener getOnOptionMenuSelectListener()
    {
        return onOptionMenuSelectListener;
    }

    public void setOnOptionMenuSelectListener(OnOptionMenuSelectListener onOptionMenuSelectListener)
    {
        this.onOptionMenuSelectListener = onOptionMenuSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // 请求权限
        requestSdcardPermission();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBusUtils.register(this);

        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);

        initView();

    }

    @Override
    public void requestSdcardPermission()
    {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!(result == PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    /**
     * 该方法在Activity onResume之后才会调用, 并且在Activity onResume之后才会弹出对话框
     * 对话框是模态的，对话框消失后才开始执行onRequestPermissionsResult，之后activity
     * 进入onResume状态
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 循环式的请求存贮权限
        for (int i : grantResults)
        {
            if (i != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUser(BusEvents.LoginEvent event)
    {
        if (Constant.USER_UPDATE.equals(event.what))
        {
            initMyToolBar(checkId);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBusUtils.unRegister(this);
        mainPresenter.detachView();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
    {
        // 记录当前选中的Fragment
        checkId = checkedId;

        // 初始化toolbar
        initMyToolBar(checkedId);

        // 跳转到指定的fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        switchToFragment(fragmentTransaction, checkedId);
        fragmentTransaction.commit();
    }

    @Override
    public void hideAllFragment(FragmentTransaction fragmentTransaction)
    {
        if (homeFragment != null)
            fragmentTransaction.hide(homeFragment);

        if (cartFragment != null)
            fragmentTransaction.hide(cartFragment);

        if (myFragment != null)
            fragmentTransaction.hide(myFragment);

    }

    @Override
    public void switchToFragment(FragmentTransaction fragmentTransaction, int id)
    {
        switch (id)
        {
            case R.id.rb_main_home:
                if (homeFragment == null)
                {
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.main_content, homeFragment);
                } else
                {
                    fragmentTransaction.show(homeFragment);
                }
                break;

            case R.id.rb_main_cart:
                if (cartFragment == null)
                {
                    cartFragment = new CartFragment();
                    fragmentTransaction.add(R.id.main_content, cartFragment);
                } else
                {
                    fragmentTransaction.show(cartFragment);
                }
                break;

            case R.id.rb_main_my:
                if (myFragment == null)
                {
                    myFragment = new MyFragment();
                    fragmentTransaction.add(R.id.main_content, myFragment);
                } else
                {
                    fragmentTransaction.show(myFragment);
                }
                break;
        }
    }

    private void initMyToolBar(int id)
    {
        // 刷新标题栏控件
        supportInvalidateOptionsMenu();
        switch (id)
        {
            case R.id.rb_main_home:
                collapseToolBar.setTitleEnabled(false);
                flMyUserData.setVisibility(View.GONE);
                carouselView.setVisibility(View.VISIBLE);
                break;

            case R.id.rb_main_cart:
                collapseToolBar.setTitleEnabled(false);
                flMyUserData.setVisibility(View.GONE);
                carouselView.setVisibility(View.VISIBLE);
                break;

            case R.id.rb_main_my:
                collapseToolBar.setTitleEnabled(true);
                carouselView.setVisibility(View.GONE);
                flMyUserData.setVisibility(View.VISIBLE);

                String userId = DataProvider.getUserLoginId(this);
                Log.d("my", "initMyToolBar userId = " + userId);
                // 设置是否可点击
                if (Constant.NULL.equals(userId))
                {
                    flMyUserData.setEnabled(true);
                } else
                {
                    flMyUserData.setEnabled(false);
                }


                if (!Constant.NULL.equals(userId))
                {
                    int jifen = DataProvider.getJifen(this);
                    String userName = DataProvider.getUsername(this);
                    String userGender = DataProvider.getUserGender(this);

                    // 设置标题
                    collapseToolBar.setTitle(userName);
                    // 让积分可见
                    tvMyUserJifen.setVisibility(View.VISIBLE);
                    // 设置积分
                    tvMyUserJifen.setText(Constant.CURRENT_JIFEN + jifen);
                    // 让性别图标可见
                    ivMyUserGender.setVisibility(View.VISIBLE);

                    if (Constant.MALE.equals(userGender))
                    {
                        ivMyUserGender.setImageResource(R.drawable.ic_male);
                    } else if (Constant.FEMALE.equals(userGender))
                    {
                        ivMyUserGender.setImageResource(R.drawable.ic_female);
                    }
                } else
                {
                    // 隐藏性别图标
                    ivMyUserGender.setVisibility(View.GONE);
                    // 设置标题
                    collapseToolBar.setTitle(getString(R.string.click_login));
                    // 隐藏积分
                    tvMyUserJifen.setVisibility(View.GONE);
                }
                break;
        }

    }

    @Override
    public void initView()
    {
        // 默认第一页
        rgMain.setOnCheckedChangeListener(this);
        rbMainHome.setChecked(true);

        // 加载toolbar
        setSupportActionBar(mainToolbar);

        // 加载广告
        setImages(null);

        // 分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_divider, null));
        } else
        {
            dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_divider));
        }

        // 加载衣服模型的图片
        ImageLoader.getInstance().loadImage(Constant.BASE_URL + Constant.MODEL_HEAD_MALE, DisplayImgOptionUtils.loadModel(), this);
        ImageLoader.getInstance().loadImage(Constant.BASE_URL + Constant.MODEL_UP_MALE, DisplayImgOptionUtils.loadModel(), this);
        ImageLoader.getInstance().loadImage(Constant.BASE_URL + Constant.MODEL_UNDER_MALE, DisplayImgOptionUtils.loadModel(), this);
        ImageLoader.getInstance().loadImage(Constant.BASE_URL + Constant.MODEL_HEAD_FEMALE, DisplayImgOptionUtils.loadModel(), this);
        ImageLoader.getInstance().loadImage(Constant.BASE_URL + Constant.MODEL_UP_FEMALE, DisplayImgOptionUtils.loadModel(), this);
        ImageLoader.getInstance().loadImage(Constant.BASE_URL + Constant.MODEL_UNDER_FEMALE, DisplayImgOptionUtils.loadModel(), this);
    }

    @Override
    public void setImages(final List<String> ads)
    {
        final List<String> imgs = new ArrayList<>();
        imgs.add("adsImg/ad1.jpg");
        imgs.add("adsImg/ad2.jpg");
        imgs.add("adsImg/ad3.jpg");
        imgs.add("adsImg/ad4.jpg");
        imgs.add("adsImg/ad5.jpg");

        carouselView.setPageCount(imgs.size());
        carouselView.setImageListener(new ImageListener()
        {
            @Override
            public void setImageForPosition(int position, ImageView imageView)
            {
                ImageLoader.getInstance().displayImage(Constant.BASE_URL + imgs.get(position), imageView, DisplayImgOptionUtils.getAdsDio());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        Log.d("main", "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        Log.d("main", "onPrepareOptionsMenu");

        MenuItem menuItemSearch = menu.findItem(R.id.main_menu_search);
        MenuItem menuItemDelete = menu.findItem(R.id.main_menu_delete);

        switch (checkId)
        {
            case R.id.rb_main_home:
                menuItemSearch.setVisible(true);
                menuItemDelete.setVisible(false);
                break;

            case R.id.rb_main_cart:
                menuItemSearch.setVisible(false);
                menuItemDelete.setVisible(true);
                break;

            case R.id.rb_main_my:
                menuItemSearch.setVisible(false);
                menuItemDelete.setVisible(false);
                break;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.main_menu_search:

                Intent intent = new Intent(this, ShopQueryActivity.class);
                startActivity(intent);
                break;

            case R.id.main_menu_delete:

                if (onOptionMenuSelectListener != null)
                {
                    onOptionMenuSelectListener.onDeleteClick();
                }
                break;
        }
        return true;
    }

    @OnClick(fl_my_user_data)
    public void onViewClicked()
    {
        String userId = DataProvider.getUserLoginId(this);
        if (Constant.NULL.equals(userId))
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (isExit == true)
            {
                return super.onKeyUp(keyCode, event);
            } else
            {
                isExit = true;
                response(this, getString(R.string.pressed_again_to_exit));
                handler.postDelayed(this, Constant.DELAY_TO_EXIT);
                return true;
            }

        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void run()
    {
        isExit = false;
    }

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
        Log.d("my", "onLoadingComplete w = " + bitmap.getWidth() + ", h = " + bitmap.getHeight());
    }

    @Override
    public void onLoadingCancelled(String s, View view)
    {
    }
}
