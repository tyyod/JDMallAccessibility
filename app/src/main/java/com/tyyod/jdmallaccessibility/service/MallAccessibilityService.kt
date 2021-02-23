package com.tyyod.jdmallaccessibility.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.*
import com.orhanobut.logger.Logger

class MallAccessibilityService: AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        Logger.d("---> MallAccessibilityService#onServiceConnected()")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) {
            return
        }

        val eventTypeStr = when (event.eventType) {
            TYPE_VIEW_CLICKED -> "TYPE_VIEW_CLICKED"
            TYPE_VIEW_LONG_CLICKED -> "TYPE_VIEW_LONG_CLICKED"
            TYPE_VIEW_SELECTED -> "TYPE_VIEW_SELECTED"
            TYPE_VIEW_FOCUSED -> "TYPE_VIEW_FOCUSED"
            TYPE_VIEW_TEXT_CHANGED -> "TYPE_VIEW_TEXT_CHANGED"
            TYPE_VIEW_TEXT_SELECTION_CHANGED -> "TYPE_VIEW_TEXT_SELECTION_CHANGED"
            TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY -> "TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY"
            TYPE_VIEW_SCROLLED -> "TYPE_VIEW_SCROLLED"
            TYPE_WINDOW_STATE_CHANGED -> "TYPE_WINDOW_STATE_CHANGED"
            TYPE_WINDOW_CONTENT_CHANGED -> "TYPE_WINDOW_CONTENT_CHANGED"
            TYPE_WINDOWS_CHANGED -> "TYPE_WINDOWS_CHANGED"
            TYPE_NOTIFICATION_STATE_CHANGED -> "TYPE_NOTIFICATION_STATE_CHANGED"
            TYPE_VIEW_HOVER_ENTER -> "TYPE_VIEW_HOVER_ENTER"
            TYPE_VIEW_HOVER_EXIT -> "TYPE_VIEW_HOVER_EXIT"
            TYPE_TOUCH_INTERACTION_START -> "TYPE_TOUCH_INTERACTION_START"
            TYPE_TOUCH_INTERACTION_END -> "TYPE_TOUCH_INTERACTION_END"
            TYPE_TOUCH_EXPLORATION_GESTURE_START -> "TYPE_TOUCH_EXPLORATION_GESTURE_START"
            TYPE_TOUCH_EXPLORATION_GESTURE_END -> "TYPE_TOUCH_EXPLORATION_GESTURE_END"
            TYPE_GESTURE_DETECTION_START -> "TYPE_GESTURE_DETECTION_START"
            TYPE_GESTURE_DETECTION_END -> "TYPE_GESTURE_DETECTION_END"
            TYPE_ANNOUNCEMENT -> "TYPE_ANNOUNCEMENT"
            else -> "Unknown Event Type"
        }
        Log.e("Tod", "class Name: ${event.className}")
        Logger.d("onAccessibilityEvent: $eventTypeStr  EventId: ${event.eventType}")
    }

    override fun onInterrupt() {
        Logger.d("---> MallAccessibilityService#onInterrupt()")
    }
}