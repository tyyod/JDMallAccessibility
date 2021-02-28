package com.tyyod.jdmallaccessibility.data

import android.net.Uri
import java.util.concurrent.atomic.AtomicBoolean

class AccessibilityServiceManager {
    companion object {
        val instance: AccessibilityServiceManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AccessibilityServiceManager()
        }
    }
    val isAccessibilityServiceStart = AtomicBoolean(false)
    val isStartMotionJDMall = AtomicBoolean(false)
    var productOpenAppUrl: Uri? = null
}