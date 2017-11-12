package com.blinnnk.honey

import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.widget.EditText
import android.widget.TextView

fun EditText.getTextRect(): Rect {
  val bounds = Rect()
  paint.getTextBounds(hint as String, 0, hint.length, bounds)
  return bounds
}

fun EditText.setCursorColor(color: Int) {
  try {
    val focusCursorDrawableRes = TextView::class.java.getDeclaredField("mCursorDrawableRes")
    focusCursorDrawableRes.isAccessible = true
    val mCursorDrawableRes = focusCursorDrawableRes.getInt(this)
    val focusEditor = TextView::class.java.getDeclaredField("mEditor")
    focusEditor.isAccessible = true
    val editor = focusEditor.get(this)
    val clazz = editor.javaClass
    val focusCursorDrawable = clazz.getDeclaredField("mCursorDrawable")
    focusCursorDrawable.isAccessible = true
    val drawables = arrayOfNulls<Drawable>(2)
    val res = this.context.resources
    drawables[0] = res.getDrawable(mCursorDrawableRes)
    drawables[1] = res.getDrawable(mCursorDrawableRes)
    drawables[0]?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    drawables[1]?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    focusCursorDrawable.set(editor, drawables)
  } catch (ignored: Throwable) {
  }
}
