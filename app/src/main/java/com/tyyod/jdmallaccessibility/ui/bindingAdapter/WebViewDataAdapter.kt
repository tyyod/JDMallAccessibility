package com.tyyod.jdmallaccessibility.ui.bindingAdapter

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.BindingAdapter
import com.tyyod.jdmallaccessibility.ui.model.WebViewSettings

class WebViewBindingAdapter {
    companion object {
        @BindingAdapter("loadUrl")
        @JvmStatic
        fun WebView.loadUrl(url: String) {
            this.loadUrl(url)
        }

        @BindingAdapter("setWebViewSettings")
        @JvmStatic
        fun WebView.setWebViewSettings(settings: WebViewSettings) {
            this.settings.javaScriptEnabled = settings.javaScriptEnabled
            this.settings.domStorageEnabled = settings.domStorageEnabled
        }

        @BindingAdapter("setWebViewClient")
        @JvmStatic
        fun WebView.setWebViewClient(client: WebViewClient) {
            this.webViewClient = client
        }

        @BindingAdapter("setWebChromeClient")
        @JvmStatic
        fun WebView.setWebChromeClient(client: WebChromeClient) {
            this.webChromeClient = client
        }
    }
}