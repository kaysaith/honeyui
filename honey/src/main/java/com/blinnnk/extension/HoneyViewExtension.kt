package com.blinnnk.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.blinnnk.honey.timeUpThen

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

/**
 * This function is used to simulate the click event with the program, the application
 * scenario is a local unit test, or other need to use the scene.
 */
@SuppressLint("Recycle")
fun View.imitateClick() {
  1000L timeUpThen {
    var downTime = SystemClock.uptimeMillis()

    val downEvent = MotionEvent.obtain(downTime, downTime,
      MotionEvent.ACTION_DOWN, 10f, 10f, 0)
    downTime += 1000

    val upEvent = MotionEvent.obtain(downTime, downTime,
      MotionEvent.ACTION_UP, 10f, 10f, 0)

    onTouchEvent(downEvent)
    onTouchEvent(upEvent)
    downEvent.recycle()
    upEvent.recycle()
  }
}