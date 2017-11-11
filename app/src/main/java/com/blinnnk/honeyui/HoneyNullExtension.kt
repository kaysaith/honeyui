package com.blinnnk.honeyui

/**
 * @date 11/11/2017 8:58 PM
 * @author KaySaith
 */

fun <T> T?.isNull(): Boolean = this == null
fun String?.orEmpty(): String = this ?: ""
fun Int?.orZero(): Int = this ?: 0
fun Float?.orZero(): Float = this ?: 0f
fun Boolean?.orTrue(): Boolean = this ?: true
fun Boolean?.orFalse(): Boolean = this ?: false
fun <T> ArrayList<T>?.orEmptyArray(): ArrayList<T> = this ?: arrayListOf()
fun <T> T?.orReturn() { if (this == null) return }