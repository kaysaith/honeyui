package com.blinnnk.honey

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

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
