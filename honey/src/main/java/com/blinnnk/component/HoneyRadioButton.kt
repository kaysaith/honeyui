package com.blinnnk.component

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.view.ViewGroup
import android.widget.RadioButton
import com.blinnnk.uikit.uiPX

/**
 * @date 26/03/2018 4:29 PM
 * @author KaySaith
 */

class HoneyRadioButton(context: Context) : RadioButton(context) {

  init {
    layoutParams = ViewGroup.LayoutParams(30.uiPX(), 30.uiPX())
  }

  fun setColorStyle(checkedColor: Int, unCheckedColor: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      buttonTintList = ColorStateList(
        arrayOf(
          intArrayOf(-R.attr.state_checked), //disabled
          intArrayOf(R.attr.state_checked) //enabled
        ),
        intArrayOf(
          checkedColor, // disabled
          unCheckedColor //enabled
        )
      )
    }
  }

}