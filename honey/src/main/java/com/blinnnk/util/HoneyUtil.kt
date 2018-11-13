@file:Suppress("DEPRECATION")

package com.blinnnk.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.blinnnk.extension.measureTextWidth
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

// Same Like Swift `DidSet` and `WillSet`
fun <T> observing(
  initialValue: T,
  willSet: () -> Unit = { },
  didSet: () -> Unit = { }
) = object : ObservableProperty<T>(initialValue) {
  override fun beforeChange(
    property: KProperty<*>,
    oldValue: T,
    newValue: T
  ): Boolean = true.apply { willSet() }

  override fun afterChange(
    property: KProperty<*>,
    oldValue: T,
    newValue: T
  ) = didSet()
}

abstract class FixTextLength {
  abstract var text: String
  abstract val maxWidth: Float
  abstract val textSize: Float
  private var hasFixed = false
  private fun fixTextLength() {
    if (text.measureTextWidth(textSize) > maxWidth) {
      hasFixed = true
      text = text.substring(0, text.lastIndex - 1)
      fixTextLength()
    }
  }

  fun getFixString(scaleInMiddle: Boolean = false): String {
    fixTextLength()
    return if (scaleInMiddle) {
      try {
        text.substring(0, text.length / 2) + " ... " + text.substring(text.length / 2 + 1, text.length)
      } catch (error: Exception) {
        text + if (hasFixed) "..." else ""
      }
    } else {
      text + if (hasFixed) "..." else ""
    }
  }
}

object SystemUtils {

  @Throws(PackageManager.NameNotFoundException::class)
  private fun getPackageInfo(context: Context): PackageInfo {
    return context.packageManager.getPackageInfo(context.packageName, 0)
  }

  fun getVersionName(context: Context): String {
    try {
      return getPackageInfo(context).versionName
    } catch (e: PackageManager.NameNotFoundException) {
      e.printStackTrace()
    }
    return ""
  }

  fun getVersionCode(context: Context): Int {
    try {
      return getPackageInfo(context).versionCode
    } catch (e: PackageManager.NameNotFoundException) {
      e.printStackTrace()
    }
    return 0
  }
}