package com.blinnnk.extension

import android.R
import android.content.res.ColorStateList
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.CompoundButton
import com.blinnnk.uikit.HoneyColor


/**
 * @author KaySaith
 * @date  2018/11/13
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun CompoundButton.isDefaultStyle(themeColor: Int) {
  buttonTintList = ColorStateList(
    arrayOf(
      intArrayOf(-R.attr.state_checked), //disabled
      intArrayOf(R.attr.state_checked) //enabled
    ),
    // disabled - enabled
    intArrayOf(HoneyColor.LightGray, themeColor)
  )
}
