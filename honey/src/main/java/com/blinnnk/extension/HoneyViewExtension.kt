package com.blinnnk.honey

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast

fun View.getDisplayHeight(): Int {
  val rect = Rect()
  (context as Activity).window.decorView.getWindowVisibleDisplayFrame(rect)
  return rect.bottom - rect.top
}

fun Context?.safeToast(message: CharSequence) {
  if (this != null) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  } else {
    return
  }
}

fun Fragment?.safeToast(message: CharSequence) {
  if (this != null) {
    context.safeToast(message)
  } else {
    return
  }
}