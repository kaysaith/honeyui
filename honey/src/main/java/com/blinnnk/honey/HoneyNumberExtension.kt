package com.blinnnk.honey

import android.os.Handler

/**
 * @date 12/11/2017 5:19 AM
 * @author KaySaith
 */

fun <T : Number> T.isEven(): Boolean = this.toInt() % 2 == 0

infix fun Long.timeUpThen(doThis: () -> Unit) {
  Handler().postDelayed({
    doThis()
  },this)
}