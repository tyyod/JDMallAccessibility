package com.tyyod.jdmallaccessibility.ui.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import com.tyyod.jdmallaccessibility.data.AccessibilityServiceManager
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
        if (AccessibilityServiceManager.instance.isAccessibilityServiceStart.get()) {
            if (webUrl.get() == URL) {
                webUrl.notifyChange()
            } else {
                webUrl.set(URL)
            }
        } else {
            launchAccessibilitySettings(view.context)
            Toast.makeText(view.context, "请先系统设置 -> 辅助功能 -> 开启京东商城辅助", Toast.LENGTH_LONG).show()
        }
    }

    private fun launchAccessibilitySettings(context: Context?) {
        if (context != null) {
            val intent = if (Build.MANUFACTURER.equals("samsung")) {
                Intent("com.samsung.accessibility.installed_service")
            } else {
                Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            }
            context.startActivity(intent)
        }
    }
}

class JDMallWebViewClient: WebViewClient() {

    private var hasLaunchJDApp = false

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        if (!hasLaunchJDApp) {
            view?.evaluateJavascript(
                "document.getElementById(\"m_common_tip_trynow_0\").click()",
                null
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        Logger.d("onShouldInterceptRequest: ${request?.url}")
        if (view != null && request?.url?.toString()?.startsWith("openapp.jdmobile://virtual?") == true) {
            AccessibilityServiceManager.instance.productOpenAppUrl = request.url
            val it = Intent()
            it.action = Intent.ACTION_VIEW
            it.data = request.url
            if (view.context !is Activity) {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            if (it.resolveActivity(view.context.packageManager) != null) {
                view.context.startActivity(it)
            }
            hasLaunchJDApp = true
            return true
        }
        return super.shouldOverrideUrlLoading(view, request)
    }
}