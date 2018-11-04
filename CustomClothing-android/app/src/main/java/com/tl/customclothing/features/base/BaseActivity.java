package com.tl.customclothing.features.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tl.customclothing.R;

/**
 * Created by tianlin on 2017/3/31.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView
{

    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityList.addActivity(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityList.removeActivity(this);
    }

    @Override
    public void showProgressDialog(String msg)
    {
        if(alertDialog == null)
        {
            View view = View.inflate(this, R.layout.progress_dialog, null);
            alertDialog = new AlertDialog.Builder(this, 0)
                    .setCancelable(false)
                    .setView(view)
                    .setMessage(msg)
                    .create();
        }
        else
        {
            alertDialog.setMessage(msg);
        }

        alertDialog.show();

        android.view.WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.width = (int) getResources().getDimension(R.dimen.progress_dialog_w);
        layoutParams.height = (int) getResources().getDimension(R.dimen.progress_dialog_h);

        alertDialog.getWindow().setAttributes(layoutParams);
    }

    @Override
    public void showProgressDialog(int msg)
    {
        String str = getString(msg);
        showProgressDialog(str);
    }

    @Override
    public void hideProgressDialog()
    {
        if(alertDialog != null)
            alertDialog.dismiss();
    }

    @Override
    public void response(Context context, String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void response(Context context, int msg)
    {
        Toast.makeText(context, context.getString(msg), Toast.LENGTH_SHORT).show();
    }
}
