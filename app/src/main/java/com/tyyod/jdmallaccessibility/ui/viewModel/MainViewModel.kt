package com.tyyod.jdmallaccessibility.ui.viewModel

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tyyod.jdmallaccessibility.ui.model.WebSettingModel

class MainViewModel : ViewModel() {

    companion object {
        const val URL = "https://cd.jd.com/qrcode?skuId=100012043978&location=2&isWeChatStock=2"
    }

    val webUrl = ObservableField<String>("")
    val webSetting = WebSettingModel()
    val webViewClient = WebViewClient()
    val webChromeClient = WebChromeClient()

    fun onStartActivity(view: View) {
        if (webUrl.get() == URL) {
            webUrl.notifyChange()
        } else {
            webUrl.set(URL)
        }
    }
}