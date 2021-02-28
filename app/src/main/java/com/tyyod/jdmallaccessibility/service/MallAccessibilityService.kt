package com.tyyod.jdmallaccessibility.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.*
import android.view.accessibility.AccessibilityNodeInfo
import com.orhanobut.logger.Logger
import com.tyyod.jdmallaccessibility.data.AccessibilityServiceManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.core.SingleOnSubscribe
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MallAccessibilityService: AccessibilityService() {

    var isInProductDetailActivity = false

    override fun onUnbind(intent: Intent?): Boolean {
        Logger.d("---> MallAccessibilityService#onUnbind()")
        AccessibilityServiceManager.instance.isAccessibilityServiceStart.compareAndSet(true, false)
        return super.onUnbind(intent)
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        AccessibilityServiceManager.instance.isAccessibilityServiceStart.compareAndSet(false, true)
        Logger.d("---> MallAccessibilityService#onServiceConnected()")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) {
            return
        }

        if (event.eventType == TYPE_WINDOW_STATE_CHANGED) {
            isInProductDetailActivity = event.className == "com.jd.lib.productdetail.ProductDetailActivity"
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

        if (isInProductDetailActivity) {
            if (event.eventType == TYPE_WINDOW_CONTENT_CHANGED) {
                try {
                    traverseAllAccessibilityNode(event.source)
                } catch (e: Exception) {
                    e.printStackTrace()
                    restartProductDetail()
                }
            }
        }
        Logger.d("onAccessibilityEvent: $eventTypeStr  EventId: ${event.eventType}， class Name: ${event.className}")
    }


    private fun restartProductDetail() {
        Single.create(SingleOnSubscribe<String> {
            Logger.d("--> start sendKeyDownUpSync ---> ")
//            val inst =  Instrumentation()
//            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK)
            val runtime = Runtime.getRuntime()
            runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK)
            Logger.d("sendKeyDownUpSync ---> ")
            it?.onSuccess("")
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<String> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onSuccess(t: String?) {
                    if (AccessibilityServiceManager.instance.productOpenAppUrl != null) {
                        val it = Intent(Intent.ACTION_VIEW)
                        it.data = AccessibilityServiceManager.instance.productOpenAppUrl
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        applicationContext.startActivity(it)
                        Logger.d("start productOpenAppUrl ---> ")
                    }
                }

                override fun onError(e: Throwable?) {
                    e?.printStackTrace()
                    Logger.d("start productOpenAppUrl  onError---> ")
                }
            })
    }

    override fun onInterrupt() {
        Logger.d("---> MallAccessibilityService#onInterrupt()")
    }

    private fun traverseAllAccessibilityNode(nodeInfo: AccessibilityNodeInfo?) {
        if (nodeInfo == null) {
            return
        }
        if (nodeInfo.className == "android.widget.TextView") {
           Logger.e("TextView --> Text: ${nodeInfo.text}")
            if (nodeInfo.text == "等待预约") {
                throw Exception("等待预约中，无法点击")
            }
            return
        }
        val childrenCount = nodeInfo.childCount
        for (i in 0 until childrenCount) {
            val childNode = nodeInfo.getChild(i)
            traverseAllAccessibilityNode(childNode)
        }
    }
}