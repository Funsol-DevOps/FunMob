package com.scorpio.funmobsdk.customViews

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.VideoView

class FunMediaView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var imageView: ImageView = ImageView(context, attrs)

    var webView: WebView = WebView(context)

    init {
        with(webView) {
            settings.pluginState = WebSettings.PluginState.ON
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                settings.mediaPlaybackRequiresUserGesture = true
            }
            settings.setAppCacheEnabled(true)
            settings.saveFormData = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true
            settings.defaultTextEncodingName = "utf-8"
            settings.userAgentString = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (HTML, like Gecko) Chrome/71.0.3578.99 Safari/537.36"

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    return false
                }
            }
        }

        this.addView(webView)

        imageView.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.invalidate()
        this.addView(imageView)
    }
}