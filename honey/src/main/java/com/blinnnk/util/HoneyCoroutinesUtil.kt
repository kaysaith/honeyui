package com.blinnnk.util

import kotlinx.coroutines.*

fun <T> coroutinesTask(doThings: () -> T, then: (T) -> Unit) {
  GlobalScope.launch {
    val task = async(Dispatchers.Default) { doThings() }
    withContext(Dispatchers.Main) { then(task.await()) }
  }
}