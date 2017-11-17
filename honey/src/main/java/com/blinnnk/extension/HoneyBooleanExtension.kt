package com.blinnnk.extension

/**
 * @date 17/11/2017 11:17 AM
 * @author KaySaith
 */

sealed class BooleanExt<out T> constructor(val boolean: Boolean)
class HoldData<out T>(val data: T) : BooleanExt<T>(false)
object Otherwise : BooleanExt<Nothing>(true)

infix inline fun<T> Boolean.isFalse(block: () -> T): BooleanExt<T> {
  return if (!this) {
    HoldData(block())
  } else Otherwise
}

infix inline fun<T> Boolean.isTrue(block: () -> T): BooleanExt<T> {
  return if (this) {
    HoldData(block())
  } else Otherwise
}

infix inline fun<T> BooleanExt<T>.otherwise(block: () -> T): T {
  return when(this) {
    is Otherwise -> block()
    is HoldData<T> -> data
  }
}