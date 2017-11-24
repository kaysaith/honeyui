package com.blinnnk.extension

import kotlin.collections.ArrayList

fun <T> T?.isNull(): Boolean = this == null
fun String?.orEmpty(): String = this ?: ""
fun Int?.orZero(): Int = this ?: 0
fun Float?.orZero(): Float = this ?: 0f
fun Boolean?.orTrue(): Boolean = this ?: true
fun Boolean?.orFalse(): Boolean = this ?: false
fun <T> ArrayList<T>?.orEmptyArray(): ArrayList<T> = this ?: arrayListOf()
fun <T> T?.ifNullReturn() { if (this == null) return }

fun<T> T?.orElse(orElse: T): T {
  return if (this.isNull()) orElse else this!!
}

fun <T> T?.isNotNull(block: T.() -> Unit = { }): Boolean {
  return if (!isNull()) {
    block(this!!)
    true
  } else false
}

fun <T: ArrayList<*>> T?.isNullOrEmpty(block: T?.() -> Unit = { }): Boolean {
  return if (isNull() || this!!.isEmpty()) {
    block(this)
    true
  } else false
}

inline fun <T> T.isNull(block: () -> Unit): Boolean {
  return if (this.isNull()) {
    block()
    true
  } else false

}