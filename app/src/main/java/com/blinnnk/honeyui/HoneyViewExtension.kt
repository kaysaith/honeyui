package com.blinnnk.honeyui

import android.app.Activity
import android.graphics.Rect
import android.view.View

/**
 * @date 11/11/2017 8:20 PM
 * @author KaySaith
 */


/**
 * Calculate the height of the keyboard by dynamically calculating the
 * actual height of the display area
 */

fun View.getDisplayHeight(): Int {
  val rect = Rect()
  (context as Activity).window.decorView.getWindowVisibleDisplayFrame(rect)
  return rect.bottom - rect.top
}

var View.leftPadding: Int
  inline get() = paddingLeft
  set(value) = setPadding(value, paddingTop, paddingRight, paddingBottom)

var View.topPadding: Int
  inline get() = paddingTop
  set(value) = setPadding(paddingLeft, value, paddingRight, paddingBottom)

var View.rightPadding: Int
  inline get() = paddingRight
  set(value) = setPadding(paddingLeft, paddingTop, value, paddingBottom)

var View.bottomPadding: Int
  inline get() = paddingBottom
  set(value) = setPadding(paddingLeft, paddingTop, paddingRight, value)
