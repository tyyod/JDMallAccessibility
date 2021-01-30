package com.tyyod.jdmallaccessibility.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class MallAccessibilityService: AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
    }

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {

    }

    override fun onInterrupt() {

    }
}