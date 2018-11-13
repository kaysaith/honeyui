package com.blinnnk.extension

import org.json.JSONArray
import org.json.JSONObject
import java.math.BigInteger


/**
 * @author KaySaith
 * @date  2018/09/17
 */

@Throws
fun JSONObject.getTargetChild(vararg keys: String): String {
    try {
        var willGetChildObject = this
        keys.forEachIndexed { index, content ->
            if (index == keys.lastIndex) return@forEachIndexed
            willGetChildObject = JSONObject(willGetChildObject.safeGet(content))
        }
        return willGetChildObject.safeGet(keys.last())
    } catch (error: Exception) {
        throw Exception("goldstone getTargetChild has error")
    }
}

@Throws
fun JSONObject.getTargetObject(vararg keys: String): JSONObject {
    try {
        var willGetChildObject = this
        keys.forEach {
            willGetChildObject = JSONObject(willGetChildObject.safeGet(it))
        }
        return willGetChildObject
    } catch (error: Exception) {
        throw Exception("goldstone getTargetObject has error")
    }
}

fun JSONArray.toList(): List<String> {
    var result = listOf<String>()
    (0 until length()).forEach {
        result += get(it).toString()
    }
    return result
}

fun JSONArray.toJSONObjectList(): List<JSONObject> {
    var result = listOf<JSONObject>()
    (0 until length()).forEach {
        result += JSONObject(get(it).toString())
    }
    return result
}

