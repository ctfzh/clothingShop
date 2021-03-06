package com.tl.customclothing.utils.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.tl.customclothing.utils.NullUtils;

/**
 * Created by tianlin on 2017/3/15.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class TlDialogFragment extends DialogFragment
{

    /**
     * ok键的按钮事件
     */
    private DialogInterface.OnClickListener okListener;

    /**
     * 取消键的按钮事件
     */
    private DialogInterface.OnClickListener noListener;

    /**
     * 对话框的标题
     */
    private String title;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 确认键的文本
     */
    private String okText = "OK";

    /**
     * 取消键的文本
     */
    private String noText = "NO";

    /**
     * 资源文件的id，默认值为0表示使用的是activity的Theme
     */
    private int styleId = 0;


    private View view;

    public TlDialogFragment setView(View view)
    {
        this.view = view;
        return this;
    }

    public static TlDialogFragment newInstance()
    {
        TlDialogFragment fragment = new TlDialogFragment();
        return fragment;
    }

    public TlDialogFragment setStyleId(int styleId)
    {
        this.styleId = styleId;
        return this;
    }

    public TlDialogFragment setOkText(String okText)
    {
        this.okText = okText;
        return this;
    }

    public TlDialogFragment setNoText(String noText)
    {
        this.noText = noText;
        return this;
    }

    public TlDialogFragment setOkListener(DialogInterface.OnClickListener okListener)
    {
        this.okListener = okListener;
        return this;
    }

    public TlDialogFragment setNoListener(DialogInterface.OnClickListener noListener)
    {
        this.noListener = noListener;
        return this;
    }

    public TlDialogFragment setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public TlDialogFragment setMsg(String msg)
    {
        this.msg = msg;
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), styleId)
                .setTitle(title)
                .setPositiveButton(okText, okListener)
                .setNegativeButton(noText, noListener);

        if (!NullUtils.isEmpty(msg))
        {
            builder.setMessage(msg);
        } else if (view != null)
        {
            builder.setView(view);
        }
        return builder.create();
    }

}
