package com.example.yiyao.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by MBENBEN on 2016-5-18.
 */
public class ToastUtil {

    private static Toast mToast;

    // 工具类私有化
    private ToastUtil() {
    }

    // 单例模式 显示Toast
    public static void show(Context context, String text) {
        if (null == mToast) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();

    }

    // 关闭Toast
    public static void cancel() {
        if (null != mToast)
            mToast.cancel();
    }
}
