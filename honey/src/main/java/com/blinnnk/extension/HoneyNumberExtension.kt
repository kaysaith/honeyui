package com.blinnnk.honey

import android.os.Handler

fun <T : Number> T.isEven(): Boolean = this.toInt() % 2 == 0

infix fun Long.timeUpThen(doThis: () -> Unit) {
  Handler().postDelayed({
    doThis()
  },this)
}