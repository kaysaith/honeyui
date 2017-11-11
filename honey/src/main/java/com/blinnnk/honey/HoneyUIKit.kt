package com.blinnnk.honey

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager

val matchParentViewGroup: Int = android.view.ViewGroup.LayoutParams.MATCH_PARENT

object ScreenSize {
  @JvmField
  val Width = Resources.getSystem().displayMetrics.widthPixels
  // This height will lose control bar height when there is a soft control bar
  @JvmField
  val Height = Resources.getSystem().displayMetrics.heightPixels - getStatusBarHeight()
  @JvmField
  val centerX = Width / 2f
  @JvmField
  val centerY = Height / 2f
}

object AnimationDuration {
  const val Default = 360L
}

fun getStatusBarHeight(): Int {
  val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
  return if (resourceId > 0) {
    Resources.getSystem().getDimensionPixelSize(resourceId)
  } else 0
}

fun Context.getControlBarHeight(): Int {
  val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val clazz = Class.forName("android.view.Display")
  val method = clazz.getMethod("getRealMetrics", DisplayMetrics::class.java)
  val displayMetrics = DisplayMetrics()
  method.invoke(windowManager.defaultDisplay, displayMetrics)
  return displayMetrics.heightPixels - Resources.getSystem().displayMetrics.heightPixels
}

fun <T : Number> T.uiPX(): Int = pxFromDp(
  dpFromUIPX(this as Int).toInt()
)

fun dpFromUIPX(px: Int): Float = px * 1.018f
fun pxFromDp(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()
