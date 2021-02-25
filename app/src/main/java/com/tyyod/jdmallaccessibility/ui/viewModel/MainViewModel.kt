package com.tyyod.jdmallaccessibility.ui.viewModel

import android.view.View
import android.webkit.*
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import com.tyyod.jdmallaccessibility.ui.model.WebViewSettings

class MainViewModel : ViewModel() {

    companion object {
        const val URL = "https://cd.jd.com/qrcode?skuId=100012043978&location=2&isWeChatStock=2"
    }

    val webUrl = ObservableField("")
    val webViewSetting = WebViewSettings()
    val webViewClient = JDMallWebViewClient()
    val webChromeClient = WebChromeClient()

    fun onStartActivity(view: View) {
        if (webUrl.get() == URL) {
            webUrl.notifyChange()
        } else {
            webUrl.set(URL)
        }
    }
}

class JDMallWebViewClient: WebViewClient() {

    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
        Logger.d("onShouldInterceptRequest: ${request?.url}")
        return super.shouldInterceptRequest(view, request)
    }
}