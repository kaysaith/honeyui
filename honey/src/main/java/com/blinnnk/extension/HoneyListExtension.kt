package com.blinnnk.extension

import android.util.Log
import org.json.JSONObject
import java.util.*

fun <T> List<T>.getRandom(): T =
        this[Random().nextInt(this.count())]

fun <T> List<T>.randomIndex(): Int =
        Random().nextInt(this.count())

/***
 * 业务中经常遇到在异步环节中在循环的最后的时候出发事件。处理中经常是比对角标，综上这里封装一个
 * 简洁的函数。
 */

fun <T> Iterable<T>.forEachOrEnd(action: (item: T, isEnd: Boolean) -> Unit) {
    var index = 0
    for (item in this) {
        index++
        action(item, index == this.count())
    }
}

fun <T> List<T>.toArrayList(): ArrayList<T> {
    return this.mapTo(arrayListOf()) { it }
}

fun JSONObject.safeGet(key: String): String {
    return try {
        get(key).toString()
    } catch (error: Exception) {
        Log.e("ERROR", "function: safeGet $key $error")
        ""
    }
}

fun <T> Collection<T>.mergeAndDistinct(other: Collection<T>): Collection<T> {
    return this.plus(other).distinct()
}


