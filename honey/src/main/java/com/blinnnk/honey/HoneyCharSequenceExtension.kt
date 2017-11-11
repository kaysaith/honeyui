package com.blinnnk.honey

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan

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
