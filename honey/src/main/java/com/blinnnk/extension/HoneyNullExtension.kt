package com.blinnnk.extension

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@UseExperimental(ExperimentalContracts::class)
inline fun<T> T?.isNull(): Boolean {
	contract {
		returns(false) implies (this@isNull != null)
	}
	return this == null
}

fun String?.orEmpty(): String = this ?: ""
fun Int?.orZero(): Int = this ?: 0
fun Float?.orZero(): Float = this ?: 0f
fun Double?.orZero(): Double = this ?: 0.0
fun Boolean?.orTrue(): Boolean = this ?: true
fun Boolean?.orFalse(): Boolean = this ?: false
fun <T> ArrayList<T>?.orEmptyArray(): ArrayList<T> = this ?: arrayListOf()
fun <T> T?.orElse(orElse: T): T {
	return if (this.isNull()) orElse else this
}