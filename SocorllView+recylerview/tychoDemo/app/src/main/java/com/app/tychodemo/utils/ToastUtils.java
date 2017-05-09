package com.app.tychodemo.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by xingjikang on 2017/3/24.
 */

public class ToastUtils {
    public static void show(Context context, CharSequence msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static void show(Context context, @StringRes int stringId) {
        android.widget.Toast.makeText(context, stringId, android.widget.Toast.LENGTH_LONG).show();
    }
}
