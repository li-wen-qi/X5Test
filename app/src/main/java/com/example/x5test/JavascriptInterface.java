package com.example.x5test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * 作者：liwenqi on 2021/8/23 23:36
 * 邮箱：liwenqi@zelkova.cn
 * 描述：
 */
@SuppressLint("JavascriptInterface")
public class JavascriptInterface {
    /**
     * 构造方法
     */
    private Context context;
    public JavascriptInterface(Context context) {
        this.context = context;
    }

    /**
     * JS 调用 Android
     */

    @android.webkit.JavascriptInterface
    public void showInfoFromJs(String name) {
        Toast.makeText(context, "JS页面输入内容：" + name, Toast.LENGTH_LONG).show();
    }

}