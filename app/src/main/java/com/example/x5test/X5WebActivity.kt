package com.example.x5test

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
//调用js：x5WebView.loadUrl("javascript:MyOnclick()");
//function sendInfoToJava(){
//    //调用android程序中的方法，并传递参数
//    var name = document.getElementById("name_input").value;
//    window.AndroidWebView.showInfoFromJs(name);
//}

//https://blog.csdn.net/weixin_37730482/article/details/73505969
class X5WebActivity : AppCompatActivity() {
    private var progressBar: ProgressBar? = null
    private var x5WebView: X5WebView? = null
    private val url = "https://blog.csdn.net/weixin_37730482"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initHardwareAccelerate()
        initView()
    }

    /**
     * 启用硬件加速
     */
    private fun initHardwareAccelerate() {
        try {
            if (Build.VERSION.SDK.toInt() >= 11) {
                window
                    .setFlags(
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                    )
            }
        } catch (e: Exception) {
        }
    }

    /**
     * 初始化各种View
     */
    private fun initView() {

        progressBar = findViewById(R.id.activity_x5webview_progressbar)
        x5WebView = findViewById(R.id.activity_x5webview_x5webview)
        x5WebView?.loadUrl(url)
        x5WebView?.setWebViewClient(myWebViewClient())
        x5WebView?.setWebChromeClient(myWebChromeClient())
        // 添加js交互接口类，并起别名 AndroidWebView
        x5WebView?.addJavascriptInterface(JavascriptInterface(this), "AndroidWebView");
    }

    /**
     * WebViewClient监听
     */
    private inner class myWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(webView: WebView, s: String): Boolean {
            webView.loadUrl(s)
            return true
        }
    }

    /**
     * WebChromeClient监听
     */
    private inner class myWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(webView: WebView, i: Int) {
            super.onProgressChanged(webView, i)
            if (i == 100) {
                progressBar!!.visibility = View.GONE //加载完网页进度条消失
            } else {
                progressBar!!.visibility = View.VISIBLE //开始加载网页时显示进度条
                progressBar!!.progress = i //设置进度值
            }
        }
    }

    /**
     * onKeyDown方法
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (x5WebView != null && x5WebView!!.canGoBack()) {
                x5WebView?.goBack()
                true
            } else {
                super.onKeyDown(keyCode, event)
            }
        } else super.onKeyDown(keyCode, event)
    }

    /**
     * onDestroy方法
     */
    override fun onDestroy() {
        super.onDestroy()
        if (null != x5WebView) {
            x5WebView?.destroy() //释放资源
        }
    }
}
