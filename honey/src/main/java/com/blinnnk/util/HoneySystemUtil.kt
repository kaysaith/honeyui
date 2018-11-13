package com.blinnnk.util

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.blinnnk.extension.safeToast
import org.json.JSONArray
import org.json.JSONObject

object SoftKeyboard {

    fun hide(activity: Activity) {
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE)?.apply {
            this as InputMethodManager
            hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
        }
    }

    fun show(activity: Activity, currentEditText: EditText) {
        activity.getSystemService(Context.INPUT_METHOD_SERVICE)?.apply {
            this as InputMethodManager
            showSoftInput(currentEditText, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}

fun Context.convertLocalJsonFileToJSONObjectArray(filePath: Int): ArrayList<JSONObject> {
    val dataSet = ArrayList<JSONObject>()
    JSONArray(resources.openRawResource(filePath)
            .bufferedReader()
            .use { it.readText() }).apply {
        (0 until length()).mapTo(dataSet) { getJSONObject(it) }
    }
    return dataSet
}

fun Context.clickToCopy(code: String, toastMessage: String = "Copy Success") {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", code)
    clipboard.primaryClip = clip
    safeToast(toastMessage)
}

inline fun <reified T : Activity> Context.reload() {
    val startActivity = Intent(this, T::class.java)
    val pendingIntentId = 123456
    val pendingIntent = PendingIntent.getActivity(this, pendingIntentId, startActivity,
            PendingIntent.FLAG_CANCEL_CURRENT
    )
    val service = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    service.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent)
    System.exit(0)
}

// 获取手机厂商信息
fun getDeviceBrand(): String {
    return android.os.Build.BRAND
}

// 获取手机型号
fun getSystemModel(): String {
    return android.os.Build.MODEL
}