package com.tl.customclothing.utils.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.tl.customclothing.utils.Constant;

/**
 * Created by tianlin on 2017/4/6.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class DataProvider
{
    // 保存用户登录ID
    private static final String USERLOGINID = "USERID";
    // 保存用户昵称
    private static final String USERNAME = "USERNAME";
    // 保存密码，MD5加密
    private static final String PASSWORD = "PASSWORD";
    // 保存当前用户积分
    private static final String JIFEN = "JIFEN";
    // 保存当前登录的用户图标
    private static final String USER_ICON_URL = "USER_ICON_URL";
    // 用户的性别
    private static final String USER_GENDER = "USER_GENDER";

    // 用户Sp数据文件名
    private static final String USER_DATA_FILE_NAME = "USER_DATA_FILE_NAME";

    public static void saveUserGender(Context context, final String userGender)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_GENDER, userGender);
        editor.commit();
    }

    public static String getUserGender(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA_FILE_NAME, Context.MODE_PRIVATE);
        String userGender = sharedPreferences.getString(USER_GENDER, Constant.NULL);
        return userGender;
    }

    public static void saveUserIconUrl(Context context, final String userIconUrl)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ICON_URL, userIconUrl);
        editor.commit();
    }

    public static String getUserIconUrl(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA_FILE_NAME, Context.MODE_PRIVATE);
        String userIconUrl = sharedPreferences.getString(USER_ICON_URL, Constant.NULL);
        return userIconUrl;
    }

    public static void saveUserLoginId(Context context, final String userId)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERLOGINID, userId);
        editor.commit();
    }

    public static String getUserLoginId(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA_FILE_NAME, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString(USERLOGINID, Constant.NULL);
        return userId;
    }

    public static void saveUserName(Context context, final String username)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, username);
        editor.commit();
    }

    public static String getUsername(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA_FILE_NAME, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(USERNAME, Constant.NULL);
        return username;
    }

    public static void savePwd(Context context, final String pwd)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD, pwd);
        editor.commit();
    }

    public static String getPwd(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA_FILE_NAME, Context.MODE_PRIVATE);
        String pwd = sharedPreferences.getString(PASSWORD, Constant.NULL);
        return pwd;
    }

    public static void saveJifen(Context context, final int jifen)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(JIFEN, jifen);
        editor.commit();
    }

    public static int getJifen(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA_FILE_NAME, Context.MODE_PRIVATE);
        int jifen = sharedPreferences.getInt(JIFEN, 0);
        return jifen;
    }

    /**
     * 初始化用户信息
     *
     * @param context
     */
    public static void initUserData(Context context)
    {
        DataProvider.saveUserLoginId(context, Constant.NULL);
        DataProvider.savePwd(context, Constant.NULL);
        DataProvider.saveUserName(context, Constant.NULL);
        DataProvider.saveUserIconUrl(context, Constant.NULL);
        DataProvider.saveUserGender(context, Constant.MALE);
        DataProvider.saveJifen(context, 0);

    }
}
