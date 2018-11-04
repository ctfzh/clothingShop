package com.tl.customclothing.features.main.cart;

import com.tl.customclothing.features.base.IBaseView;

import java.util.List;

/**
 * Created by tianlin on 2017/4/10.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public interface ICartView extends IBaseView
{
    void setAdapter(List<CartVo> cartVoList);

    void setLinear();

    void setFlow();
}
