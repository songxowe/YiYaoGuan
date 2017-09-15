package com.example.yiyao.Utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.example.yiyao.pojo.User;

/**
 * Created by MBENBEN on 2016-5-6.
 */
public class TmallUtils {
    // 选项存储 *********************************************
    private static final String PREFS_NAME = "yiyao_settings";
    private static final int PREFS_MODE = Context.MODE_PRIVATE;

    /**
     * 在首选项中保存用户信息
     *
     * @param context
     * @param user
     */
    public static void saveUser(Context context, User user) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, PREFS_MODE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", user.getU_id());
        editor.putString("mobile", user.getU_mobile());
        editor.putString("password", user.getU_password());
        editor.putString("showimageUrl", user.getU_imageurl());
        editor.putString("backgroundimageUrl", user.getU_backgroundimageurl());
        editor.putString("nickname", user.getU_nickname());
        editor.putString("sex", user.getU_sex());
        editor.commit();
    }

    //存储收货地址
    public static void saveAddress(Context context, String name, String address, String mobile) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, PREFS_MODE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("receive_name", name);
        editor.putString("receive_address", address);
        editor.putString("receive_mobile", mobile);
        editor.commit();
    }
    //存储商品总共金额
    public static void savePrice(Context context, String price) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, PREFS_MODE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("price", price);
        editor.commit();
    }



    //存储收货地址id
    public static void saveAid(Context context, String aid) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, PREFS_MODE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("a_id", aid);
        editor.commit();
    }

    //获取地址id;
    public static String getPrice(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, PREFS_MODE);
        String s = preferences.getString("price", "");
        return s;
    }


    //获取名字
    public static String getReceiveName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, PREFS_MODE);
        String s = preferences.getString("receive_name", "");
        return s;
    }
    //获取地址
    public static String getReceiveAdress(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, PREFS_MODE);
        String s = preferences.getString("receive_address", "");
        return s;
    }
   //获取电话
    public static String getReceiveMobile(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, PREFS_MODE);
        String s = preferences.getString("receive_mobile", "");
        return s;
    }
   //获取地址id;
    public static String getAid(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, PREFS_MODE);
        String s = preferences.getString("a_id", "");
        return s;
    }

    /**
     * 获取首选项存储中的用户信息
     *
     * @param context
     * @return
     */
    public static User getUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, PREFS_MODE);
        Integer id = preferences.getInt("id", 0);
        String mobile = preferences.getString("mobile", "");
        String showimageUrl = preferences.getString("showimageUrl", "");
        String password = preferences.getString("password", "");
        String backgroundimageUrl = preferences.getString("backgroundimageUrl", "");
        String nickname = preferences.getString("nickname", "");
        String sex = preferences.getString("sex", "");

        User user = new User();
        user.setU_id(id);
        user.setU_mobile(mobile);
        user.setU_imageurl(showimageUrl);
        user.setU_password(password);
        user.setU_backgroundimageurl(backgroundimageUrl);
        user.setU_nickname(nickname);
        user.setU_sex(sex);
        return user;
    }

    // Toast *****************************************
    public static void showToast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, @StringRes int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }
}
