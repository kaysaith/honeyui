package com.blinnnk.uikit

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.WindowManager

val matchParentViewGroup: Int = android.view.ViewGroup.LayoutParams.MATCH_PARENT

object ScreenSize {
  @JvmField
  val Width = Resources.getSystem().displayMetrics.widthPixels
  // This height will lose control bar height when there is a soft control bar
  @JvmField
  val Height = Resources.getSystem().displayMetrics.heightPixels - DeviceElementSize.getStatusBarHeight()
  @JvmField
  val centerX = Width / 2f
  @JvmField
  val centerY = Height / 2f
  @JvmField val statusBarHeight = getStatusBarHeight()
}

object AnimationDuration {
  const val Default = 360L
}

// Color

object HoneyColor {
  @JvmField val HoneyWhite = Color.parseColor("#FFF9F8F4")
  @JvmField val DarkGray = Color.parseColor("#FF262626")
  @JvmField val Gray = Color.parseColor("#FF555555")
  @JvmField val LightGray = Color.parseColor("#FF9E9E9E")
  @JvmField val Yellow = Color.parseColor("#FFFFF000")
  @JvmField val Pale = Color.parseColor("#FFF6F6F6")
  @JvmField val WhitePale = Color.parseColor("#FFE2E2E2")
  @JvmField val Red = Color.parseColor("#FFFF1B5B")
  @JvmField val Blue = Color.parseColor("#FF00C0FF")
  @JvmField val Purple = Color.parseColor("#FF945FFF")
  @JvmField val DarkYellow = Color.parseColor("#FF787100")
  @JvmField val Green = Color.parseColor("#FF1DD779")
}

object DeviceElementSize {
  fun getStatusBarHeight(): Int {
    val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) {
      Resources.getSystem().getDimensionPixelSize(resourceId)
    } else 0
  }

  fun isNavigationBarAvailable(): Boolean {
    val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
    val hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME)
    return (!(hasBackKey && hasHomeKey))
  }

  fun getNavigationBarHeight(): Int {
    val resourceId = Resources.getSystem().getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) { return Resources.getSystem().getDimensionPixelSize(resourceId) } else 0
  }
}

fun Context.getActionBarHeight(): Int {
  val value = TypedValue()
  return if (theme.resolveAttribute(android.R.attr.actionBarSize, value, true)) {
    TypedValue.complexToDimensionPixelSize(value.data, Resources.getSystem().displayMetrics)
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

// 获取状态栏高度的方法
private fun getStatusBarHeight(): Int {
  var result = 0
  val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
  if (resourceId > 0) {
    result = Resources.getSystem().getDimensionPixelSize(resourceId)
  }
  return result
}
