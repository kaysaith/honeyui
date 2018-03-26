package com.blinnnk.component

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.ViewGroup
import android.widget.Switch
import com.blinnnk.uikit.HoneyColor
import com.blinnnk.uikit.matchParentViewGroup
import com.blinnnk.uikit.uiPX

/**
 * @date 25/03/2018 6:00 PM
 * @author KaySaith
 */

class HoneyBaseSwitch(context: Context) : Switch(context) {

  override fun setChecked(checked: Boolean) {
    super.setChecked(checked)
    changeColor(checked)
  }

  private var thumbThemeColor = HoneyColor.Red
  private var trackThemeColor = HoneyColor.Red

  private fun changeColor(isChecked: Boolean) {

    val thumbColor: Int
    val trackColor: Int

    if (isChecked) {
      thumbColor = thumbThemeColor
      trackColor = trackThemeColor
    } else {
      thumbColor = Color.DKGRAY
      trackColor = Color.LTGRAY
    }

    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        thumbDrawable.setColorFilter(thumbColor, PorterDuff.Mode.MULTIPLY)
        trackDrawable.setColorFilter(trackColor, PorterDuff.Mode.MULTIPLY)
      }
    } catch (e: NullPointerException) {
      e.printStackTrace()
    }
  }

  init {

    layoutParams = ViewGroup.LayoutParams(matchParentViewGroup, matchParentViewGroup)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      switchMinWidth = 30.uiPX()
      thumbDrawable.setColorFilter(Color.DKGRAY, PorterDuff.Mode.MULTIPLY)
      trackDrawable.setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      trackTintMode = PorterDuff.Mode.DARKEN
    }
  }

  fun setThemColor(thumbColor: Int, trackColor: Int) {
    thumbThemeColor = thumbColor
    trackThemeColor = trackColor
  }

}