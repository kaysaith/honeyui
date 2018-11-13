package com.blinnnk.extension

import android.graphics.Paint
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import com.blinnnk.uikit.uiPX
import java.math.BigInteger
import java.util.regex.Pattern

fun CharSequence.setUnderline(): SpannableString {
    return SpannableString(this).apply {
        setSpan(UnderlineSpan(), 0, this.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

fun CharSequence.setColor(color: Int): SpannableString {
    return SpannableString(this).apply {
        setSpan(ForegroundColorSpan(color), 0, this.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

fun CharSequence.setItalic(): SpannableString {
    return SpannableString(this.insertCharAtLast(" ")).apply {
        setSpan(StyleSpan(Typeface.ITALIC), 0, this.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

fun CharSequence.setBold(): SpannableString {
    return SpannableString(this).apply {
        setSpan(StyleSpan(Typeface.BOLD), 0, this.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

fun CharSequence.insertCharAtLast(symbols: String): CharSequence =
        this.substring(0, this.count()) + symbols

object CustomTargetTextStyle {

    private fun calculateStringIndex(
            text: String,
            wholeString: String
    ): List<Int> {
        val finalIndex = wholeString.indexOf(text) + text.length
        val startIndex = wholeString.indexOf(text)
        return listOf(startIndex, finalIndex)
    }

    private fun customStyle(
            text: String,
            position: List<Int>,
            color: Int,
            fontSize: Int,
            isItalic: Boolean,
            hasUnderline: Boolean
    ): SpannableString {
        return SpannableString(text).apply {
            setSpan(
                    ForegroundColorSpan(color), position[0], position[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                    StyleSpan(if (isItalic) Typeface.BOLD_ITALIC else Typeface.BOLD), position[0], position[1],
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            hasUnderline.isTrue {
                setSpan(UnderlineSpan(), position[0], position[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            setSpan(
                    AbsoluteSizeSpan(fontSize), position[0], position[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    // 推荐使用的封装方式
    operator fun invoke(
            targetString: String,
            wholeString: String,
            color: Int,
            fontSize: Int = 14.uiPX(),
            isItalic: Boolean = true,
            hasUnderline: Boolean = true
    ): SpannableString {
        return customStyle(
                wholeString, calculateStringIndex(targetString, wholeString), color, fontSize, isItalic,
                hasUnderline
        )
    }
}

fun CharSequence.measureTextWidth(fontSize: Float): Float {
    val textPaint = Paint().apply {
        textSize = fontSize
    }
    return textPaint.measureText(this.toString())
}

private fun String.checkChineseCount(
        callback: (Int) -> Unit = {}
): Boolean {
    var result = false
    var chineseCount = 0
    forEachIndexed { index, char ->
        if (char.isChinese()) {
            result = true
            chineseCount += 1
        }
        if (index == lastIndex) {
            callback(chineseCount)
        }
    }
    return result
}

private fun Char.isChinese(): Boolean {
    val ub = Character.UnicodeBlock.of(this)
    return ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub === Character.UnicodeBlock.GENERAL_PUNCTUATION || ub === Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub === Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
}

fun String.toUpperCaseFirstLetter(): String {
    isNotEmpty() isTrue {
        if (length == 1) return substring(
                0, 1
        ).toUpperCase()
        return substring(
                0, 1
        ).toUpperCase() + substring(
                1, length
        )
    } otherwise {
        return ""
    }
}

fun String.scaleTo(count: Int): String {
    return if (length < count) this
    else substring(0, count) + "..."
}

fun String.replaceWithPattern(
        replace: String = " "
): String {
    return Pattern.compile("\\s+").matcher(this).replaceAll(replace)
}

fun String.removeStartAndEndValue(value: String = "\n"): String {
    if (isNullOrEmpty()) {
        return ""
    }
    var finalValue = this
    if (finalValue.last().toString() == value) {
        finalValue = finalValue.substring(
                0, finalValue.length - 1
        )
    }
    if (finalValue.first().toString() == value) {
        finalValue = finalValue.substring(
                1, finalValue.length
        )
    }
    // 去除前后的空格或回车
    return finalValue
}

fun String.isEvenCount(): Boolean = this.length % 2 == 0
fun String.toIntOrZero(): Int = toIntOrNull().orZero()
fun String.toLongOrZero(): Long = toLongOrNull() ?: 0L
fun String.toDoubleOrZero(): Double = toDoubleOrNull().orZero()

fun String.toMillisecond(): Long {
    val timestamp = toBigDecimal().toString().toLong().orElse(0L)
    return when {
        count() == 10 -> timestamp * 1000
        count() < 13 -> timestamp * Math.pow(10.0, (13 - count()).toDouble()).toLong()
        count() > 13 -> timestamp / Math.pow(10.0, (count() - 13).toDouble()).toLong()
        else -> timestamp
    }
}

fun String.getDecimalCount(): Int? {
    return if (contains('.')) {
        val integerPlaces = indexOf('.')
        length - integerPlaces - 1
    } else {
        null
    }
}

infix fun String.suffix(content: String) = this + " " + content

// 自己解 `SONObject` 的时候用来可能用 `String` 判断 `Null`
fun String.isNullValue(): Boolean {
    return contains("null")
}

fun Double.getDecimalCount(): Int? {
    return toString().getDecimalCount()
}

fun String.toBigIntegerOrZero() = toBigIntegerOrNull() ?: BigInteger.ZERO

fun String.isIntOnly(): Boolean = all { it.toString().matches(Regex(".*[0-9].*")) }

fun String.convertToDouble(decimal: Int): Double? {
    val illegalSymbol = Regex(".*[!@#\$%¥^&*()_=+?].*")
    val convertedNumber =
            if (toLowerCase().matches(Regex(".*[a-z].*"))  || matches(illegalSymbol)) null
            else if (filter { it.toString() == "." }.count() > 1) null
            else if (!contains(".") && length > 0 && substring(0, 1) == "0") substring(0, 1) + "." + substring(1)
            else this
    return if (convertedNumber.isNull()) null
    else if (convertedNumber!!.contains(".") && convertedNumber.substringAfter(".").length > decimal) convertedNumber.substring(0, convertedNumber.indexOf(".") + decimal).toDouble()
    else convertedNumber.toDouble()
}