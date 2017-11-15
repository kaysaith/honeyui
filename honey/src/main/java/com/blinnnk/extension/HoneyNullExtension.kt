package com.blinnnk.extension

import kotlin.collections.ArrayList

fun <T> T?.isNull(): Boolean = this == null
fun String?.orEmpty(): String = this ?: ""
fun Int?.orZero(): Int = this ?: 0
fun Float?.orZero(): Float = this ?: 0f
fun Boolean?.orTrue(): Boolean = this ?: true
fun Boolean?.orFalse(): Boolean = this ?: false
fun <T> ArrayList<T>?.orEmptyArray(): ArrayList<T> = this ?: arrayListOf()
fun <T> T?.orReturn() { if (this == null) return }

inline fun <T> T?.isNotNull(block: T.() -> Unit) {
  if (this != null) {
    block(this)
  }
}

fun <T: ArrayList<*>> T?.isNullOrEmpty(block: T?.() -> Unit = { }): Boolean {
  return if (isNull() || this!!.isEmpty()) {
    block(this)
    true
  } else false
}