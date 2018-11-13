package com.blinnnk.util

/**
 * @date 2018/6/13 1:12 AM
 * @author KaySaith
 */
enum class TinyNumber(val value: Int) {
    True(1),
    False(0)
}

object TinyNumberUtils {
    fun isTrue(value: Int): Boolean {
        return value == TinyNumber.True.value
    }

    fun isTrue(value: String): Boolean {
        return value.toIntOrNull() == TinyNumber.True.value
    }

    fun allTrue(vararg values: Boolean): Boolean {
        return values.none { !it }
    }

    fun allFalse(vararg values: Boolean): Boolean {
        return values.none { it }
    }

    fun hasTrue(vararg values: Boolean): Boolean {
        return values.any { it }
    }
}