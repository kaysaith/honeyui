package com.blinnnk.extension

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import com.blinnnk.uikit.uiPX

fun CharSequence.setUnderline(): SpannableString {
  return SpannableString(this).apply {
    setSpan(UnderlineSpan(), 0, this.length, 0)
  }
}

fun CharSequence.setColor(color: Int): SpannableString {
  return SpannableString(this).apply {
    setSpan(color, 0, this.length, 0)
  }
}

fun CharSequence.setItalic(): SpannableString {
  return SpannableString(this.insertCharAtLast(" ")).apply {
    setSpan(StyleSpan(Typeface.ITALIC), 0, this.length, 0)
  }
}

fun CharSequence.setBold(): SpannableString {
  return SpannableString(this).apply {
    setSpan(StyleSpan(Typeface.BOLD), 0, this.length, 0)
  }
}

fun CharSequence.insertCharAtLast(symbols: String): CharSequence =
  this.substring(0, this.count()) + symbols

object CustomTargetTextStyle {

  private fun calculateStringIndex(text: String, wholeString: String): List<Int> {
    val finalIndex = wholeString.indexOf(text) + text.length
    val startIndex = wholeString.indexOf(text)
    return listOf(startIndex, finalIndex)
  }

  private fun customStyle(text: String, position: List<Int>, color: Int, fontSize: Int): SpannableString {
    return SpannableString(text).apply {
      setSpan(ForegroundColorSpan(color), position[0], position[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      setSpan(StyleSpan(Typeface.BOLD_ITALIC), position[0], position[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      setSpan(UnderlineSpan(), position[0], position[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      setSpan(AbsoluteSizeSpan(fontSize), position[0], position[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
  }

  // 推荐使用的封装方式
  operator fun invoke(targetString: String, wholeString: String, color: Int, fontSize: Int = 14.uiPX()): SpannableString {
    return customStyle(wholeString, calculateStringIndex(targetString, wholeString) , color, fontSize)
  }
}