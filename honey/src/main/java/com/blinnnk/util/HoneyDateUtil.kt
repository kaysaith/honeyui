package com.blinnnk.util

/**
 * @date 08/04/2018 4:13 AM
 * @author KaySaith
 */
object HoneyDateUtil {

    data class DataText(
            val month: String = "month",
            val week: String = "week",
            val day: String = "day",
            val hour: String = "hour",
            val minute: String = "minute",
            val second: String = "second",
            val ago: String = " ago",
            val later: String = " later",
            val showPluralSymbol: Boolean = true
    )

    fun getSinceTime(
            lastTime: Long, // mill second
            dataText: DataText = DataText(),
            isAgo: Boolean = true
    ): String {
        fun showPluralByLanguage(): String {
            return if (!dataText.showPluralSymbol) ""
            else "s "
        }

        fun calculateUnit(count: Int, unit: String): String {
            val timeType = if (isAgo) dataText.ago else dataText.later
            return count.toString() + " " + unit + if (count > 1) showPluralByLanguage() + timeType else timeType
        }
        // 距离当前已过的时间，单位秒
        val timePass = (System.currentTimeMillis() - lastTime) / 1000.0
        return when {
            timePass > 30 * 7 * 24 * 60 * 60 -> {
                val monthCount = Math.floor(timePass / 30 / 7 / 24 / 60 / 60).toInt()
                calculateUnit(monthCount, dataText.month)
            }

            timePass > 7 * 24 * 60 * 60 -> {
                val weekCount = Math.floor(timePass / 7 / 24 / 60 / 60).toInt()
                calculateUnit(weekCount, dataText.week)
            }

            timePass > 24 * 60 * 60 -> {
                val dayCount = Math.floor(timePass / 24 / 60 / 60).toInt()
                calculateUnit(dayCount, dataText.day)
            }

            timePass > 60 * 60 -> {
                val hourCount = Math.floor(timePass / 60 / 60).toInt()
                calculateUnit(hourCount, dataText.hour)
            }

            timePass > 60 -> {
                val minuteCount = Math.floor(timePass / 60).toInt()
                calculateUnit(minuteCount, dataText.minute)
            }

            else -> {
                val secondValue = if (timePass.toInt() < 0) 0 else timePass.toInt()
                calculateUnit(secondValue, dataText.second)
            }
        }
    }
}