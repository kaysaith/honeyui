package com.blinnnk.util

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.blinnnk.extension.safeToast
import org.json.JSONArray
import org.json.JSONObject

object SoftKeyboard {

  fun hide(activity: Activity) {
    activity.getSystemService(Activity.INPUT_METHOD_SERVICE)?.apply {
      this as InputMethodManager
      hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0) }
  }
  fun show(activity: Activity, currentEditText: EditText) {
    activity.getSystemService(Context.INPUT_METHOD_SERVICE)?.apply {
      this as InputMethodManager
      showSoftInput(currentEditText, InputMethodManager.SHOW_IMPLICIT) }
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
