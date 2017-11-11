package com.blinnnk.honey

import android.app.Activity
import android.graphics.Rect
import android.view.View

fun View.getDisplayHeight(): Int {
  val rect = Rect()
  (context as Activity).window.decorView.getWindowVisibleDisplayFrame(rect)
  return rect.bottom - rect.top
}

