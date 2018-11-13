package com.blinnnk.util

import android.content.Context
import com.blinnnk.extension.orEmpty

/**
 * @date 10/04/2018 4:28 PM
 * @author KaySaith
 */
/** Context Utils */
private const val sharedPreferencesName = "share_data"

fun <T> Context.saveDataToSharedPreferences(key: String, data: T) {
    val sharedPreferencesEdit =
            getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).edit()
    when (data) {
        is String -> {
            sharedPreferencesEdit.putString(key, data)
            sharedPreferencesEdit.apply()
        }

        is Int -> {
            sharedPreferencesEdit.putInt(key, data)
            sharedPreferencesEdit.apply()
        }

        is Boolean -> {
            sharedPreferencesEdit.putBoolean(key, data)
            sharedPreferencesEdit.apply()
        }

        is Float -> {
            sharedPreferencesEdit.putFloat(key, data)
            sharedPreferencesEdit.apply()
        }

        is Double -> {
            sharedPreferencesEdit.putFloat(key, data.toFloat())
            sharedPreferencesEdit.apply()
        }

        is Long -> {
            sharedPreferencesEdit.putLong(key, data)
            sharedPreferencesEdit.apply()
        }
    }
}

fun Context.getIntFromSharedPreferences(key: String): Int =
        getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getInt(key, -1)

fun Context.getFloatFromSharedPreferences(key: String): Float =
        getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getFloat(key, 100f)

fun Context.getDoubleFromSharedPreferences(key: String): Double =
        getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getFloat(key, 100f).toDouble()

fun Context.getStringFromSharedPreferences(key: String): String =
        getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getString(key, "Default").orEmpty()

fun Context.getBooleanFromSharedPreferences(key: String): Boolean =
        getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getBoolean(key, false)