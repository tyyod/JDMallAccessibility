package com.tyyod.jdmallaccessibility.ui.dataAdapter

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.tyyod.jdmallaccessibility.ui.model.WebSettingModel

class WebViewDataAdapter {
    companion object {
        @JvmStatic
        fun WebView.loadUrl(url: String) {
            this.loadUrl(url)
        }

        @JvmStatic
        fun WebView.setWebViewSettings(settings: WebSettingModel) {
            this.settings.javaScriptEnabled = settings.javaScriptEnabled
        }

        @JvmStatic
        fun WebView.setWebViewClient(client: WebViewClient) {
            this.webViewClient = client
        }

        @JvmStatic
        fun WebView.setWebChromeClient(client: WebChromeClient) {
            this.webChromeClient = client
        }
    }
}