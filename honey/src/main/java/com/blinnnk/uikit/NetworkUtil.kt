package com.blinnnk.uikit

import android.content.Context
import android.text.format.DateUtils
import android.view.View
import java.util.*

/**
 * @date 2018/5/19 5:56 PM
 * @author KaySaith
 */

object TimeUtils {

	const val oneHourInMills = 3600000L
	const val ondDayInMills = 86400000L

	fun timeIntervalFromUTC(): Long {
		val offsetFromUtc = TimeZone.getDefault().getOffset(Date().time)
		return Integer.toString(offsetFromUtc).toLongOrNull()
			?: 0
	}

	fun getNatureMondayTimeInMill(): Long {
		val calendar = Calendar.getInstance()
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
		return calendar.timeInMillis
	}

	fun getNatureSundayTimeInMill(): Long {
		val calendar = Calendar.getInstance()
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
		return calendar.timeInMillis
	}

	fun getNatureMonthFirstTimeInMill(): Long {
		val calendar = Calendar.getInstance()
		calendar.set(Calendar.DAY_OF_MONTH, 1)
		return calendar.timeInMillis
	}
}

// Time Utils
fun Context.numberDate(timeStamp: Long): String {
	return DateUtils.formatDateTime(
		this,
		timeStamp,
		DateUtils.FORMAT_NUMERIC_DATE
	)
}

fun View.numberDate(timeStap: Long): String {
	return DateUtils.formatDateTime(
		context,
		timeStap,
		DateUtils.FORMAT_NUMERIC_DATE
	)
}