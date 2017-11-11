package com.blinnnk.honeyui

import android.os.Handler
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

/**
 * @date 11/11/2017 8:47 PM
 * @author KaySaith
 */

/**
 * Easy to use delay execution function
 */
infix fun Long.timeUpThen(doThis: () -> Unit) {
  Handler().postDelayed({
    doThis()
  },this)
}

fun <T> coroutinesTask(doThings: () -> T, then: (T) -> Unit) {
  val task = async(CommonPool, CoroutineStart.LAZY) { doThings() }
  launch { then(task.await()) }
}