package com.tl.customclothing.features.main.my;

import com.tl.customclothing.R;
import com.tl.customclothing.features.base.BasePresenter;
import com.tl.customclothing.features.myaddr.MyAddrActivity;
import com.tl.customclothing.features.ordersearch.OrderSearchActivity;
import com.tl.customclothing.utils.Constant;
import com.tl.customclothing.utils.data.DataProvider;
import com.tl.customclothing.utils.emotion.TextEmotion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlin on 2017/4/10.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class MyPresenter extends BasePresenter<MyFragment>
{
    @Override
    public void initData()
    {
        loadMy();
    }

    public void loadMy()
    {
        if (view == null)
            return;

        List<MyVo> list = new ArrayList<>();
        String userId = DataProvider.getUserLoginId(view.getActivity());

        if (Constant.NULL.equals(userId))
        {
            MyVo myVoNoLogin = new MyVo();
            myVoNoLogin.noLoginDesc = view.getString(R.string.no_login_text) + TextEmotion.huaiyi;
            myVoNoLogin.viewType = MyAdapter.VIEW_TYPE_NO_LOGIN;
            list.add(myVoNoLogin);
            view.setMyAdapter(list);
        } else
        {
            MyVo myVoOrder = new MyVo();
            myVoOrder.viewType = MyAdapter.VIEW_TYPE_MY_LL_HEAD;
            myVoOrder.clazz = OrderSearchActivity.class;
            myVoOrder.leftTitle = view.getString(R.string.my_order);
            myVoOrder.rightTitle = view.getString(R.string.see_more_order);
            MyVo myVoHeadIcon = new MyVo();
            myVoHeadIcon.viewType = MyAdapter.VIEW_TYPE_MY_LL_HEAD;
            myVoHeadIcon.clazz = OrderSearchActivity.class;
            myVoHeadIcon.leftTitle = view.getString(R.string.my_head_icon);
            myVoHeadIcon.rightTitle = view.getString(R.string.upload_icon);
            MyVo myVoReceiverAddr = new MyVo();
            myVoReceiverAddr.viewType = MyAdapter.VIEW_TYPE_MY_LL_HEAD;
            myVoReceiverAddr.clazz = MyAddrActivity.class;
            myVoReceiverAddr.leftTitle = view.getString(R.string.my_receiver_addr);
            myVoReceiverAddr.rightTitle = view.getString(R.string.set_my_addr);

            MyVo myVoExit = new MyVo();
            myVoExit.viewType = MyAdapter.VIEW_TYPE_MY_EXIT;
            myVoExit.leftTitle = view.getString(R.string.exit);

            list.add(myVoOrder);
            list.add(myVoHeadIcon);
            list.add(myVoReceiverAddr);
            list.add(myVoExit);

            view.setMyAdapter(list);
        }

    }

}
