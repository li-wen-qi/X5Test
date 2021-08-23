package com.example.x5test

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.os.Process
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient


/**
 * 作者：liwenqi on 2021/8/23 22:07
 * 邮箱：liwenqi@zelkova.cn
 * 描述：
 */
class X5WebView : WebView {
    var title: TextView? = null
    private val client: WebViewClient = object : WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    constructor(arg0: Context?, arg1: AttributeSet?) : super(arg0, arg1) {
        this.setWebViewClient(client)
        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings()
        this.getView().setClickable(true)
    }

    private fun initWebViewSettings() {
        val webSetting: WebSettings = this.getSettings()
        webSetting.setJavaScriptEnabled(true)
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true)
        webSetting.setAllowFileAccess(true)
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS)
        webSetting.setSupportZoom(true)
        webSetting.setBuiltInZoomControls(true)
        webSetting.setUseWideViewPort(true)
        webSetting.setSupportMultipleWindows(true)
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true)
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true)
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE)
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND)
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE)

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }

    override protected fun drawChild(canvas: Canvas, child: View?, drawingTime: Long): Boolean {
        val ret: Boolean = super.drawChild(canvas, child, drawingTime)
        canvas.save()
        val paint = Paint()
        paint.setColor(0x7fff0000)
        paint.setTextSize(24f)
        paint.setAntiAlias(true)
        if (getX5WebViewExtension() != null) {
            canvas.drawText(
                this.getContext().getPackageName().toString() + "-pid:"
                        + Process.myPid(), 10f, 50f, paint
            )
            canvas.drawText(
                "X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10f,
                100f, paint
            )
        } else {
            canvas.drawText(
                (this.getContext().getPackageName().toString() + "-pid:"
                        + Process.myPid()), 10f, 50f, paint
            )
            canvas.drawText("Sys Core", 10f, 100f, paint)
        }
        canvas.drawText(Build.MANUFACTURER, 10f, 150f, paint)
        canvas.drawText(Build.MODEL, 10f, 200f, paint)
        canvas.restore()
        return ret
    }

    constructor(arg0: Context?) : super(arg0) {
        setBackgroundColor(85621)
    }
}

