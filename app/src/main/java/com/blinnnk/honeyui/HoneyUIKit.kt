package com.blinnnk.honeyui

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * @date 11/11/2017 8:24 PM
 * @author KaySaith
 */

val matchParent: Int = android.view.ViewGroup.LayoutParams.MATCH_PARENT
val wrapContent: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT

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
  const val Default = 500L
}

fun getStatusBarHeight(): Int {
  val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
  return if (resourceId > 0) {
    Resources.getSystem().getDimensionPixelSize(resourceId)
  } else 0
}

/**
 * Get the height of the bottom virtual control bar
 */
fun Context.getControlarHeight(): Int {
  val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val clazz = Class.forName("android.view.Display")
  val method = clazz.getMethod("getRealMetrics", DisplayMetrics::class.java)
  val displayMetrics = DisplayMetrics()
  method.invoke(windowManager.defaultDisplay, displayMetrics)
  return displayMetrics.heightPixels - Resources.getSystem().displayMetrics.heightPixels
}

/**
 * Any number converted directly to `uiPX`, where` uiPX` refers to the designer's 1x PX
 * In order to achieve the unit-wide unity of the platform, this function has been a series
 * of deformation. After seeing in the black honey `uiPX` Is iOS, Android, Web universal uiPX`
 */

fun <T : Number> T.uiPX(): Int = pxFromDp(dpFromUIPX(this as Int).toInt())

/**
 * 1.018f is calculated as the ratio of multiples of virtually all Android devices `ppi`
 * converted to actual pixels.
 */
fun dpFromUIPX(px: Int): Float = px * 1.018f
fun pxFromDp(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()
