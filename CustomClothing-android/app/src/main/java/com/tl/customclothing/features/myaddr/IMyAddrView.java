package com.tl.customclothing.features.myaddr;

import com.tl.customclothing.features.base.IBaseView;

import java.util.List;

/**
 * Created by tianlin on 2017/4/27.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public interface IMyAddrView extends IBaseView
{
    void setAdapter(List<MyAddrVo> list);
}
